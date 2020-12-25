package com.lgc.user_service.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class UserQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="用户ID",example = "123456")
    private Long userId;

    @ApiModelProperty(value="用户名",example = "刘")
    private String userName;

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

    public Integer getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(Integer userCredit) {
        this.userCredit = userCredit;
    }

    @ApiModelProperty(value="用户状态 0、未预定 1、已预定 2、正在使用预定位置",example = "1")
    private Integer userStatus;

    @ApiModelProperty(value="用户身份 0、普通用户 1、管理员",example = "1")
    private Integer userRole;


    @ApiModelProperty(value="用户信用分 初始100" )
    private Integer userCredit;
}
