package com.lgc.user_service.utils;

import com.lgc.user_service.entity.BookTable;
import com.lgc.user_service.entity.SeatManage;
import com.lgc.user_service.entity.UserManage;
import com.lgc.user_service.service.BookTableService;
import com.lgc.user_service.service.SeatManageService;
import com.lgc.user_service.service.UserManageService;
import com.lgc.user_service.service.impl.BookTableServiceImpl;
import com.lgc.user_service.service.impl.SeatManageServiceImpl;
import com.lgc.user_service.service.impl.UserManageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public class Util {
    @Autowired
    public SeatManageService seatManageService;
    @Autowired
    public BookTableService bookTableService;
    @Autowired
    public UserManageService userManageService;

    public List<SeatManage> getAllSeat(){
        return seatManageService.list(null);
    }
    public List<BookTable> getAllBookItems(){
        System.out.println(bookTableService);
        return bookTableService.list(null);
    }
    public List<UserManage> getAllUsers(){
        return userManageService.list(null);
    }
    public void refreshSeat(){
        {
            Util util=new Util();
            List<BookTable> bookTables=util.getAllBookItems();
            Date date=new Date();
            for(BookTable bookTable:bookTables){//过了预定时间自动取消预订单和预定记录
                if(bookTable.getBookEnd().getTime()<date.getTime()){//如果保留预定的时间显示时间已过
                    String bookTableId=bookTable.getId();
                    String seat_id=bookTable.getSeatId();
                    long user_id=bookTable.getUserId();

                    //更改座位属性 座位状态 0、未预定 1、已预定
                    boolean flag1=seatManageService.updateById(new SeatManage().setSeatStatus(0).setSeatId(seat_id));
                    boolean flag2=bookTableService.removeById(bookTableId);
                }
            }
        }
    }
}
