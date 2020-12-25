package com.lgc.user_service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgc.user_service.entity.vo.R;
import com.lgc.user_service.entity.vo.ResultCode;
import com.lgc.user_service.entity.BookTable;
import com.lgc.user_service.entity.SeatManage;
import com.lgc.user_service.entity.vo.SeatBookMessage;
import com.lgc.user_service.service.BookTableService;
import com.lgc.user_service.service.SeatManageService;
import com.lgc.user_service.service.UserManageService;
import com.lgc.user_service.service.impl.SeatManageServiceImpl;
import com.lgc.user_service.utils.Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘高城
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/user_service/book-table")
public class BookTableController {
    @Autowired
    SeatManageService seatManageService;
    @Autowired
    private UserManageService userManageService;
    @Autowired
    private BookTableService bookTableService;

    //预定座位功能
    @PostMapping("bookSeat")
    public R bookSeat(@RequestBody SeatBookMessage seatBookMessage){


//--------------------------------------刷新数据库信息---------------------------------------------//
        List<BookTable> bookTables=bookTableService.list(null);
        Date date=new Date();
        for(BookTable bookTable:bookTables){//过了预定时间自动取消预订单和预定记录
            if(bookTable.getBookEnd().getTime()<date.getTime()){//如果保留预定的时间显示时间已过
                String bookTableId=bookTable.getId();
                String seat_id=bookTable.getSeatId();

                //更改座位属性 座位状态 0、未预定 1、已预定
                boolean flag1=seatManageService.updateById(new SeatManage().setSeatStatus(0).setSeatId(seat_id));
                boolean flag2=bookTableService.removeById(bookTableId);
            }
        }

//--------------------------------------刷新数据库信息---------------------------------------------//

        System.out.println(seatBookMessage);

        QueryWrapper<BookTable> wrapper=new QueryWrapper<>();
        Page<BookTable> page=new Page<>(1,1024);
        wrapper.eq("seat_id",seatBookMessage.getSeat_id());
        List<BookTable> bookAboutThisSeat=bookTableService.list(wrapper); //获得了关于想要预定座位的所有订单 下面开始检查有没有时间冲突
        SeatManageService seatManageService=new SeatManageServiceImpl();
        BookTable bookTable=new BookTable();
        bookTable.setSeatId(seatBookMessage.getSeat_id());
        bookTable.setBookBegin(seatBookMessage.getSeat_begin());
        bookTable.setBookEnd(seatBookMessage.getSeat_end());
        bookTable.setUserId(seatBookMessage.getUser_id());
        System.out.println("bookAboutThisSeat==null?"+(bookAboutThisSeat==null));
        System.out.println("bookAboutThisSeat="+(bookAboutThisSeat));
        if(bookAboutThisSeat.size()==0){//如果关于这个座位的订单空空如也 那么就可以直接给这个座位预定了

            if(bookTableService.save(bookTable)){
                return R.ok().message("预定座位"+bookTable.getSeatId()+"成功！");

            }else{
                return R.error().message("预定座位"+bookTable.getSeatId()+"失败！");
            }

        }else{
            //如果这个座位有相关的预定 则要进行检查
            Date itemBegin=new Date(),itemEnd=new Date();
            Date needBegin=seatBookMessage.getSeat_begin();
            Date needEnd=seatBookMessage.getSeat_end();
      if(bookAboutThisSeat.size()!=0){

          for(BookTable tempBookTable:bookAboutThisSeat){
              itemBegin=tempBookTable.getBookBegin();
              itemEnd=tempBookTable.getBookEnd();
              if(!((needBegin.getTime()<needEnd.getTime()&&needEnd.getTime()<=itemBegin.getTime())||(needBegin.getTime()>=itemEnd.getTime()&&needEnd.getTime()>needBegin.getTime()))){
                  return R.error().message("预定座位"+bookTable.getSeatId()+"失败！");
              }
          }
      }
            System.out.println("bookTableService"+bookTableService);
      //bookTableService.save(bookTable);
            System.out.println("bookTableService"+bookTableService);
      SeatManage seat=seatManageService.getById(bookTable.getSeatId());
      seat.setSeatStatus(1);
      seatManageService.updateById(seat);
      return R.ok().message("预定座位"+bookTable.getSeatId()+"成功！");
        }
    }
    @ApiOperation("根据用户id获取用户当前预定的座位 不提供分页功能")
    @GetMapping("getMySeat/{id}")
    public R getMySeat(@PathVariable long id){
//--------------------------------------刷新数据库信息---------------------------------------------//
        List<BookTable> bookTables=bookTableService.list(null);
        Date date=new Date();
        for(BookTable bookTable:bookTables){//过了预定时间自动取消预订单和预定记录
            if(bookTable.getBookEnd().getTime()<date.getTime()){//如果保留预定的时间显示时间已过
                String bookTableId=bookTable.getId();
                String seat_id=bookTable.getSeatId();

                //更改座位属性 座位状态 0、未预定 1、已预定
                boolean flag1=seatManageService.updateById(new SeatManage().setSeatStatus(0).setSeatId(seat_id));
                boolean flag2=bookTableService.removeById(bookTableId);
            }
        }

//--------------------------------------刷新数据库信息---------------------------------------------//
        QueryWrapper<BookTable> wrapperr=new QueryWrapper<>();
        wrapperr.eq("user_id",id);
        List<BookTable> lists=bookTableService.list(wrapperr);
        int total=lists.size();
        return R.ok().data("total",total).data("lists",lists);

    }


}

