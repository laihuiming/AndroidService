
package com.example.androidservice.service;

/**
 @author: laihuiming
 @date: 2023/12/15
 @version: 1.0
 @description: 通用api接口
 **/
public interface CurrencyService {
    String getSidByRandomKey(String randomKey);

    String getRandomKeyBySid(String sid);

    void saveVerificationCode(String sid, String code);
}
