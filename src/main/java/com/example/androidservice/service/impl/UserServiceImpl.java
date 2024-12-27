
package com.example.androidservice.service.impl;

import com.alibaba.fastjson2.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.androidservice.constant.YesNoConstant;
import com.example.androidservice.entity.SystemUser;
import com.example.androidservice.mapper.UserMapper;
import com.example.androidservice.service.UserService;
import com.example.androidservice.utils.DateUtil;
import com.example.androidservice.utils.PassWordUtil;
import org.springframework.stereotype.Service;

/**
 @author: laihuiming
 @date: 2023/12/26
 @version: 1.0
 @description:
 **/
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, SystemUser> implements UserService {
    @Override
    public void register(SystemUser user) {
        String now = DateUtil.getCurrentTime();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setDelFlag(YesNoConstant.NO);
    }

    @Override
    public SystemUser checkLogin(String userName, String password) {
        LambdaQueryWrapper<SystemUser> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SystemUser::getUserName,userName);
        wrapper.eq(SystemUser::getPassWord, PassWordUtil.sm3Encrypt(userName,password));
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public void changePassword(SystemUser systemUser, String oldPassword, String newPassword) {
        String passWord = systemUser.getPassWord();
        if(!PassWordUtil.sm3Encrypt(systemUser.getUserName(),oldPassword).equals(passWord)){
            throw new RuntimeException("旧密码错误");
        }
        systemUser.setPassWord(PassWordUtil.sm3Encrypt(systemUser.getUserName(),newPassword));
        this.updateById(systemUser);
    }


}
