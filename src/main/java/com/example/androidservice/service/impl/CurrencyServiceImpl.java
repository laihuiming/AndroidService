
package com.example.androidservice.service.impl;

import cn.hutool.core.lang.generator.UUIDGenerator;
import com.example.androidservice.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: laihuiming
 * @date: 2023/12/15
 * @version: 1.0
 * @description: 通用API接口实现
 **/
@Service
public class CurrencyServiceImpl implements CurrencyService {
    //未接入redis,先将sid存缓存中
    Map<String, String> sidMap = new HashMap<>();

    @Override
    public String getSidByRandomKey(String randomKey) {
        UUIDGenerator uuidGenerator = new UUIDGenerator();
        String uuid = uuidGenerator.next();
        sidMap.put(uuid,randomKey);
        return uuid;
    }

    @Override
    public String getRandomKeyBySid(String sid) {
        String randomKey = sidMap.get(sid);
        return randomKey;
    }

    @Override
    public void saveVerificationCode(String sid, String code) {
        //保存到redis,sid为key,code为value
    }


}
