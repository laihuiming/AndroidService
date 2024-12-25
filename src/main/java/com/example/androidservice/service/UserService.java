
package com.example.androidservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.androidservice.entity.SystemUser;

/**
 @author: laihuiming
 @date: 2023/12/26
 @version: 1.0
 @description:
 **/
public interface UserService extends IService<SystemUser> {
    /***
     * 注册用户
     * @param user
     */
    void register(SystemUser user);

    SystemUser checkLogin(String userName, String password);
}
