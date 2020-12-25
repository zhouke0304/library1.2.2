package com.lgc.user_service.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
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
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BookTable对象", description="")
public class BookTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type= IdType.AUTO)
    @ApiModelProperty(value = "预定表的ID")
    private String id;
    @ApiModelProperty(value = "预定表的座位号")
    private String seatId;

    @ApiModelProperty(value = "预定表的用户ID")
    private Long userId;

    @ApiModelProperty(value = "预定的开始时间")
    private Date bookBegin;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getBookBegin() {
        return bookBegin;
    }

    public void setBookBegin(Date bookBegin) {
        this.bookBegin = bookBegin;
    }

    public Date getBookEnd() {
        return bookEnd;
    }

    public void setBookEnd(Date bookEnd) {
        this.bookEnd = bookEnd;
    }

    @ApiModelProperty(value = "预定的结束时间")
    private Date bookEnd;


}
