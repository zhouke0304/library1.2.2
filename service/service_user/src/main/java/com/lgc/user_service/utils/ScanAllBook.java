package com.lgc.user_service.utils;

import com.lgc.user_service.entity.BookTable;
import com.lgc.user_service.entity.SeatManage;
import com.lgc.user_service.service.BookTableService;
import com.lgc.user_service.service.SeatManageService;
import jdk.nashorn.internal.codegen.types.BooleanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
@Component
//该类会每6秒扫描一次预定表 防止预定表上的时间失效
public class ScanAllBook {
    @Autowired
    private static BookTableService bookTableService;
    @Autowired
    private static SeatManageService seatManageServiceService;
    private static Util util=new Util();
    public static void scan(){
        List<BookTable> bookTables=util.getAllBookItems();
        Date date=new Date();
        for(BookTable bookTable:bookTables){//过了预定时间自动取消预订单和预定记录
            if(bookTable.getBookEnd().getTime()<date.getTime()){//如果保留预定的时间显示时间已过
                String bookTableId=bookTable.getId();
                String seat_id=bookTable.getSeatId();
                long user_id=bookTable.getUserId();

                //更改座位属性 座位状态 0、未预定 1、已预定
                boolean flag1=seatManageServiceService.updateById(new SeatManage().setSeatStatus(0).setSeatId(seat_id));
                boolean flag2=bookTableService.removeById(bookTableId);

            }

        }

    }
}
