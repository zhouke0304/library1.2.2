package com.lgc.user_service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgc.user_service.entity.BookTable;
import com.lgc.user_service.entity.vo.R;
import com.lgc.user_service.entity.vo.ResultCode;
import com.lgc.user_service.entity.SeatManage;
import com.lgc.user_service.entity.UserManage;
import com.lgc.user_service.entity.vo.SeatBookMessage;
import com.lgc.user_service.entity.vo.UserQuery;
import com.lgc.user_service.service.BookTableService;
import com.lgc.user_service.service.SeatManageService;
import com.lgc.user_service.service.UserManageService;
import com.lgc.user_service.utils.Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘高城
 * @since 2020-12-21
 */
@RestController
@RequestMapping("/user_service/seat-manage")
public class SeatManageController {
    final int MAXFLOORSIZE=1024;//定义每一层最大的座位数不超过的值
    @Autowired
    SeatManageService seatManageService;
    @Autowired
    private UserManageService userManageService;
    @Autowired
    private BookTableService bookTableService;
    @GetMapping("findAllSeat")
    @ApiOperation(value = "获得所有座位的信息")
    public R findAllSeat(){

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

        return R.ok().data("userList",seatManageService.list(null));
    }

    @GetMapping("findFloorSeat/{floor}")
    @ApiOperation(value = "获得某一层座位的信息")
    public R findFloorSeat(@PathVariable long floor){


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

        Page<SeatManage> pageSeat=new Page<>(floor,MAXFLOORSIZE);
        QueryWrapper<SeatManage> wrapper=new QueryWrapper<>();
        wrapper.eq("seat_floor",floor);
        seatManageService.page(pageSeat,wrapper);
        return R.ok().data("seatList",pageSeat.getRecords()).data("total",pageSeat.getTotal());
    }


}

