
package com.example.androidservice.controller;

import com.alibaba.fastjson2.JSON;
import com.example.androidservice.base.Result;
import com.example.androidservice.entity.SystemUser;
import com.example.androidservice.service.UserService;
import com.example.androidservice.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 @author: laihuiming
 @date: 2023/12/11
 @version: 1.0
 @description: 登录相关接口
 **/
@Api(value = "登录相关接口",tags = {"登录相关接口"})
@RestController
@RequestMapping("/loginController")
public class LoginController {

    @Autowired
    UserService userService;

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(String userName,String password){
        SystemUser systemUser;
        //验证账号密码有效性
        systemUser = userService.checkLogin(userName,password);
        if (systemUser == null){
            return Result.fail("账号或密码错误,请检查");
        }
        String token = JwtTokenUtil.getToken(systemUser.getId().toString(), 120);
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.success(JSON.toJSONString(map));
    }

    //登出
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public Result logout(){
        return Result.success();
    }

}
