
package com.example.androidservice.controller;

import com.alibaba.fastjson2.JSON;
import com.example.androidservice.base.Result;
import com.example.androidservice.entity.SystemUser;
import com.example.androidservice.service.PlatFormService;
import com.example.androidservice.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 @author: laihuiming
 @date: 2023/12/11
 @version: 1.0
 @description: 用户相关接口
 **/
@Api(value = "用户相关接口",tags = {"用户相关接口"})
@RestController
@RequestMapping("/userController")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PlatFormService platFormService;

    //新增/注册
    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(SystemUser user){
        //必填字段校验
        if (StringUtils.isBlank(user.getUserName()) ||
                StringUtils.isBlank(user.getPassWord()) ||
                StringUtils.isBlank(user.getNickName()) ||
                StringUtils.isBlank(user.getPhoneNumber())){
            return Result.fail("参数缺失");
        }
        userService.register(user);
        return Result.success();
    }

    //修改密码
    @ResponseBody
    @RequestMapping("/changePassword")
    public Result changePassword(String oldPassword,String newPassword){
        userService.changePassword(platFormService.getCurrentUser(),oldPassword,newPassword);
        return Result.success();
    }

    //修改用户信息
    @ResponseBody
    @RequestMapping("/update")
    public Result update(SystemUser user){
        userService.updateById(user);
        return Result.success();
    }

    //获取当前用户信息
    @ResponseBody
    @RequestMapping("/getCurrentUserInfo")
    public Result getCurrentUserInfo(){
        SystemUser currentUser = platFormService.getCurrentUser();
        return Result.success(JSON.toJSONString(currentUser));
    }

}
