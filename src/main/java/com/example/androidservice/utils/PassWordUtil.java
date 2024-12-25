
package com.example.androidservice.utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author: laihuiming
 * @date: 2023/12/11
 * @version: 1.0
 * @description: 密码加解密工具类
 **/
public class PassWordUtil {

    /***
     * SM3加密密码,组合用户名和密码一起加密
     * @param username
     * @param password
     * @return
     */
    public static String sm3Encrypt(String username,String password){
        String s = SmUtil.sm3(username + password);
        return s;
    }

}
