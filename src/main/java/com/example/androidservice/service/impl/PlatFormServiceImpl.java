
package com.example.androidservice.service.impl;

import com.example.androidservice.entity.SystemUser;
import com.example.androidservice.service.PlatFormService;
import com.example.androidservice.service.UserService;
import com.example.androidservice.utils.HttpRequestLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 @author: laihuiming
 @date: 2024/2/1
 @version: 1.0
 @description: 平台功能接口实现类
 **/
@Service
public class PlatFormServiceImpl implements PlatFormService {

    @Autowired
    HttpRequestLocalUtil httpRequestLocal;
    @Autowired
    UserService userService;

    /**
     * 获取当前登录账户信息
     */
    @Override
    public SystemUser getCurrentUser() {
        SystemUser systemUser = null;
        try{
            String userId = httpRequestLocal.getUserIdByRequestToken();
            systemUser = userService.getById(userId);
        }catch (Exception e){
            throw new RuntimeException("无效token");
        }
        return systemUser;
    }
}
