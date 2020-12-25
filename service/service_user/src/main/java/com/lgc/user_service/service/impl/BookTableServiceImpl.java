package com.lgc.user_service.service.impl;

import com.lgc.user_service.entity.BookTable;
import com.lgc.user_service.entity.SeatManage;
import com.lgc.user_service.mapper.BookTableMapper;
import com.lgc.user_service.service.BookTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgc.user_service.service.SeatManageService;
import com.lgc.user_service.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘高城
 * @since 2020-12-22
 */
@Service
public class BookTableServiceImpl extends ServiceImpl<BookTableMapper, BookTable> implements BookTableService {
    @Autowired
    public SeatManageService seatManageService;
    @Autowired
    public BookTableService bookTableService;
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
