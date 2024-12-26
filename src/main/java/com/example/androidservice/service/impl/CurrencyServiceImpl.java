
package com.example.androidservice.service.impl;

import cn.hutool.core.lang.generator.UUIDGenerator;
import com.example.androidservice.constant.RedisCacheKeyConstant;
import com.example.androidservice.service.CurrencyService;
import com.example.androidservice.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String getSidByRandomKey(String randomKey) {
        UUIDGenerator uuidGenerator = new UUIDGenerator();
        String uuid = uuidGenerator.next();
        redisUtils.setExpire(RedisCacheKeyConstant.RANDOM_KEY+uuid,randomKey,60*60*2L);
        return uuid;
    }

    @Override
    public String getRandomKeyBySid(String sid) {
        String randomKey = (String) redisUtils.get(RedisCacheKeyConstant.RANDOM_KEY+sid);
        return randomKey;
    }

    @Override
    public void saveVerificationCode(String sid, String code) {
        //保存到redis,sid为key,code为value,过期时间为5分钟
        redisUtils.setExpire(RedisCacheKeyConstant.VERIFICATION_CODE + sid,code,300L);
    }

    @Override
    public boolean checkVerificationCode(String sid, String code) {
        if (redisUtils.exists(RedisCacheKeyConstant.VERIFICATION_CODE + sid)) {
            String redisCode = (String) redisUtils.get(RedisCacheKeyConstant.VERIFICATION_CODE + sid);
            return redisCode.equals(code);
        }
        return false;
    }


}
