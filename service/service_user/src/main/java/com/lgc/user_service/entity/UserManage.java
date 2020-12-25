package com.lgc.user_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
@ApiModel(value="UserManage对象", description="")
public class UserManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.ID_WORKER)
    @ApiModelProperty(value="用户ID")
    private Long userId;

    @ApiModelProperty(value="用户名")
    private String userName;

    @ApiModelProperty(value="用户密码")
    private String userPassword;

    @ApiModelProperty(value="用户状态 1、未预定 2、已预定 3、正在使用预定位置")
    private Integer userStatus;

    @ApiModelProperty(value="用户身份 0、普通用户 1、管理员")
    private Integer userRole;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getUserBookTime() {
        return userBookTime;
    }

    public void setUserBookTime(Date userBookTime) {
        this.userBookTime = userBookTime;
    }

    public Double getUserBookHour() {
        return userBookHour;
    }

    public void setUserBookHour(Double userBookHour) {
        this.userBookHour = userBookHour;
    }

    public Integer getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(Integer userCredit) {
        this.userCredit = userCredit;
    }

    @ApiModelProperty(value="用户逻辑删除 0、未删除 1、已删除")
    @TableLogic //只有逻辑删除采用的注解！！！
    private Boolean isDeleted;

    @ApiModelProperty(value="用户预定时间")
    private Date userBookTime;

    @ApiModelProperty(value="用户预定时长 最多8小时")
    private Double userBookHour;

    @ApiModelProperty(value="用户信用分 初始100")
    private Integer userCredit;


}
