package com.lgc.user_service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgc.user_service.entity.BookTable;
import com.lgc.user_service.entity.SeatManage;
import com.lgc.user_service.entity.vo.ChangePassWord;
import com.lgc.user_service.entity.vo.R;

import com.lgc.user_service.entity.UserManage;
import com.lgc.user_service.entity.vo.UserQuery;

import com.lgc.user_service.service.BookTableService;
import com.lgc.user_service.service.SeatManageService;
import com.lgc.user_service.service.UserManageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘高城
 * @since 2020-12-21
 */
@RestController
@RequestMapping("/user_service/user-manage")
public class UserManageController {
    //注入service鸭
    @Autowired
    private UserManageService userManageService;
    @Autowired
    private BookTableService bookTableService;
    @Autowired
    private SeatManageService seatManageService;



    //获取所有用户
    //访问地址"http://localhost:8001/user_service/user-manage/findAllUser"
    @GetMapping("findAllUser")
    @ApiOperation(value = "获得所有用户的信息")
    public R findAllUser(){
        return R.ok().data("userList",userManageService.list(null));
    }

    //用户逻辑删除功能
    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping("{id}") //id值需要通过路径传递
    public R removeUser(@ApiParam(name="id",value="用户ID",required = true) @PathVariable long id){
        return (userManageService.removeById(id)==true)?R.ok(): R.error();//返回删除结果 三目运算
    }
    //分页查询用户功能
    @ApiOperation(value = "分页查询用户功能")
    @GetMapping("pageUser/{current}/{limit}")
    public R pageListUser(@PathVariable long current,@PathVariable long limit ){
        //创建page对象
        Page<UserManage> pageUser=new Page<>(current,limit);
        userManageService.page(pageUser,null);
        long total=pageUser.getTotal();
        List<UserManage> lists=pageUser.getRecords();
        return R.ok().data("userList",lists).data("total",total);
    }
    //条件查询用户功能
    @ApiOperation(value = "条件查询用户带分页功能 该接口复合了三个功能:1、按身份查询 2、按Id模糊查询 3、按姓名模糊查询 4、按用户状态查询 5、查信用分")
    //
    @PostMapping("pageConditionUser/{current}/{limit}")
    public R pageConditionUser(@PathVariable long current,@PathVariable long limit,@RequestBody(required = false) UserQuery userQuery){
        //创建page对象
        Page<UserManage> pageUser=new Page<>(current,limit);
        //调用方法实现条件查询带分页功能
        QueryWrapper<UserManage> wrapper=new QueryWrapper<>();

        if(userQuery!=null){
            Long userId=userQuery.getUserId();
            String userName=userQuery.getUserName();
            Integer userStatus=userQuery.getUserStatus();
            Integer userRole=userQuery.getUserRole();
            Integer userCredit=userQuery.getUserCredit();
            if(!StringUtils.isEmpty(userId)){
                wrapper.like("user_id",userId);
                System.out.println("userId="+userId);
            }
            if (!StringUtils.isEmpty(userStatus)) {
                wrapper.eq("user_status",userStatus);
                System.out.println("userStatus="+userStatus);
            }
            if (!StringUtils.isEmpty(userRole)) {
                wrapper.eq("user_Role",userRole);
                System.out.println("userRole="+userRole);
            }
            if (!StringUtils.isEmpty(userName)) {
                wrapper.like("user_name",userName);//直接映射数据库名字 最好一模一样
                System.out.println("userName="+userName);
            }
            if (!StringUtils.isEmpty(userCredit)) {

                wrapper.eq("user_credit",userCredit);
                System.out.println("userCredit="+userCredit);
            }

        }

        userManageService.page(pageUser,wrapper);
        long total=pageUser.getTotal();
        List<UserManage> userList=pageUser.getRecords();
        return R.ok().data("total",total).data("userList",userList);
    }

    //添加用户功能
    @ApiOperation(value="添加用户")
    @PostMapping("addUser")
    public R addUser(@RequestBody UserManage userManage){

        QueryWrapper<UserManage> wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",userManage.getUserName());
        if(userManageService.list(wrapper).size()==0){
            boolean flag=userManageService.save(userManage);
            if(flag){
                QueryWrapper<UserManage> tempWrapper=new QueryWrapper<>();
                Map<String,Object> map=new HashMap<>();
                map.put("user_name",userManage.getUserName());
                map.put("user_password",userManage.getUserPassword());
                map.put("user_role",userManage.getUserRole());
                List<UserManage> lists=userManageService.list(tempWrapper.allEq(map));

                return R.ok().data("user",lists.get(0));
            }else{
                return R.error();
            }

        }else{
            return R.error().message("该用户名已经存在！");
        }


    }

    //登录功能
    @ApiOperation(value="登录功能 利用用户名 和密码登录")
    @PostMapping("login")
    public R login(String userName,String userPwd){
        QueryWrapper<UserManage> wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        wrapper.eq("user_password",userPwd);
        List<UserManage> lists=userManageService.list(wrapper);
        if(lists.size()==1)
        {
            return R.ok().message("登陆成功！").data("user",lists.get(0));
        }
        return R.error().message("ID或密码错误");
    }

    //修改用户功能
    @ApiOperation(value="修改用户信息")
    @PostMapping("updateUser")
    public R updateUser(@RequestBody UserManage userManage){
        boolean flag=userManageService.updateById(userManage);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("取消预定座位 只需要确定用户id和座位id就可以取消")
    @GetMapping("cancelBook/{user_id}/{seat_id}")
    public R cancelBook(@PathVariable long user_id,@PathVariable String seat_id){

//--------------------------------------刷新数据库信息---------------------------------------------//
        List<BookTable> bookTables=bookTableService.list(null);
        Date date=new Date();
        for(BookTable bookTable:bookTables){//过了预定时间自动取消预订单和预定记录
            if(bookTable.getBookEnd().getTime()<date.getTime()){//如果保留预定的时间显示时间已过
                String bookTableId=bookTable.getId();
                String temp_seat_id=bookTable.getSeatId();

                //更改座位属性 座位状态 0、未预定 1、已预定
                boolean flag1=seatManageService.updateById(new SeatManage().setSeatStatus(0).setSeatId(temp_seat_id));
                boolean flag2=bookTableService.removeById(bookTableId);
            }
        }

//--------------------------------------刷新数据库信息---------------------------------------------//

        Map<String,Object> map=new HashMap<>();
        map.put("user_id",user_id);
        map.put("seat_id",seat_id);
        int floor=seatManageService.getById(seat_id).getSeatFloor();
        seatManageService.removeById(new SeatManage().setSeatId(seat_id));
        seatManageService.save(new SeatManage().setSeatId(seat_id).setSeatFloor(floor));


        boolean flag=bookTableService.removeByMap(map);
        if(flag){
            return R.ok().message("取消预定成功！");
        }else{
            return R.error().message("取消预定失败！");
        }
    }

    @ApiOperation("修改用户时检查用户名是否和之前重复了 是的话返回20001 否则返回20001")
    @PostMapping("checkUser_name")
    public R checkUser_name(@RequestBody String user_name){
        QueryWrapper<UserManage> wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",user_name);
        List<UserManage> lists=userManageService.list(wrapper);
        return lists.size()==0?R.ok().message("用户名可用！"):R.error().message("用户名不可用！");

    }

    @ApiOperation("修改密码")
    @PostMapping("updateUserPwd")
    public R updateUserPwd(@RequestBody ChangePassWord cg){
        QueryWrapper<UserManage> wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",cg.getUser_name());
        List<UserManage> lists=userManageService.list(wrapper);
        if(lists!=null && lists.size()==1){
            UserManage user=lists.get(0);
            user.setUserPassword(cg.getUser_pwd());
            userManageService.updateById(user);
            return R.ok().data("user",user);

        }else{
            return R.error();
        }

    }


}

