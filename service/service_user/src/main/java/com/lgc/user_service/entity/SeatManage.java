package com.lgc.user_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘高城
 * @since 2020-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SeatManage对象", description="")
public class SeatManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seat_id", type = IdType.ID_WORKER)
    @ApiModelProperty(value="座位ID")
    private String seatId;

    @ApiModelProperty(value="座位楼层")
    private Integer seatFloor;

    @ApiModelProperty(value="座位状态 0、未预定 1、已预定 2、正在使用")
    private Integer seatStatus;

    @ApiModelProperty(value="座位开始使用时间")
    private Date seatBegin;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public Integer getSeatFloor() {
        return seatFloor;
    }

    public void setSeatFloor(Integer seatFloor) {
        this.seatFloor = seatFloor;
    }

    public Integer getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(Integer seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Date getSeatBegin() {
        return seatBegin;
    }

    public void setSeatBegin(Date seatBegin) {
        this.seatBegin = seatBegin;
    }

    public Date getSeatEnd() {
        return seatEnd;
    }

    public void setSeatEnd(Date seatEnd) {
        this.seatEnd = seatEnd;
    }

    public Long getSeatUserId() {
        return seatUserId;
    }

    public void setSeatUserId(Long seatUserId) {
        this.seatUserId = seatUserId;
    }

    @ApiModelProperty(value="座位结束使用时间")
    private Date seatEnd;

    @ApiModelProperty(value="使用座位的用户ID")
    private Long seatUserId;


}
