package com.lgc.user_service.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgc.user_service.entity.UserManage;
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


import javax.management.Query;
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
        String seat_id=seatBookMessage.getSeat_id();
        SeatManage seat=seatManageService.getById(seat_id);
        if(seat.getSeatStatus()==0){
            seat.setSeatStatus(1);
            seat.setSeatBegin(seatBookMessage.getSeat_begin());
            seat.setSeatEnd(seatBookMessage.getSeat_end());
            seatManageService.updateById(seat);
            BookTable bookTable=new BookTable();
            bookTable.setUserId(seatBookMessage.getUser_id());
            bookTable.setSeatId(seat.getSeatId());

            bookTable.setBookBegin(seatBookMessage.getSeat_begin());
            bookTable.setBookEnd(seatBookMessage.getSeat_end());


            bookTableService.save(bookTable);
            return R.ok().data("seat",seat);
        }else{
            return R.error();
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

