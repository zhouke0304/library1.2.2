package com.lgc.demo;

import com.lgc.user_service.entity.BookTable;
import com.lgc.user_service.entity.SeatManage;
import com.lgc.user_service.service.BookTableService;
import com.lgc.user_service.service.SeatManageService;
import com.lgc.user_service.service.UserManageService;
import com.lgc.user_service.service.impl.BookTableServiceImpl;
import com.lgc.user_service.service.impl.SeatManageServiceImpl;
import com.lgc.user_service.service.impl.UserManageServiceImpl;
import com.lgc.user_service.utils.Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
@Controller
public class Demo01 {
    @Autowired
    public SeatManageService seatManageService;
    @Autowired
    public UserManageService userManageService;
    @Autowired
    public BookTableService bookTableService;
//
//    public SeatManageService seatManageService=new SeatManageServiceImpl();
//
//    public UserManageService userManageService=new UserManageServiceImpl();
//
//    public BookTableService bookTableService= new BookTableServiceImpl();
    @Test
    public void test01(){
        System.out.println(new Date());
    }
    @Test
    public void refreshSeat(){
        {
            Util util=new Util();
            List<BookTable> bookTables=util.getAllBookItems();
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
        }
    }
}
