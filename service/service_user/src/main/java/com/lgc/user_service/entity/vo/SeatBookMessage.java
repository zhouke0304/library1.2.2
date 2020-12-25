package com.lgc.user_service.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class SeatBookMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="用户精确19位ID",example = "124567890123456789")
    private Long user_id;

    @ApiModelProperty(value="座位号id",example = "0001")
    private String seat_id;

    @ApiModelProperty(value="预定开始时间", example = "2019-01-01 10:10:10")
    private Date seat_begin;

    @ApiModelProperty(value="预定结束时间", example = "2019-01-01 12:10:10")
    private Date seat_end;

}
