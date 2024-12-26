/**
 * 版权声明 厦门法度信息科技有限公司，版权所有 违者必究
 */
package com.example.androidservice.utils;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 @author: laihuiming
 @date: 2023/3/6
 @version: 1.0
 @description: redis常用操作工具类
 **/
@Service
public class RedisUtils {
    @Resource
    RedisTemplate redisTemplate;

    /**
      * 方法描述: 添加(不设置时间)
      * @param key
      * @param value
      * @return: boolean
      * @author: laihuiming
      * @date: 2023/3/6 14:53
     */
    public boolean set(String key, Object value){
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
      * 方法描述: 添加(过期时间单位为毫秒)
      * @param key
      * @param value
      * @param expireTime
      * @return: boolean
      * @author: laihuiming
      * @date: 2023/3/6 14:58
     */
    public boolean setExpire(String key,Object value,Long expireTime){
        boolean result = false;
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            redisTemplate.expire(valueOperations, expireTime, TimeUnit.MILLISECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
      * 方法描述: 添加(设定指定时间过期)
      * @param key
      * @param value
      * @param date
      * @return: boolean
      * @author: laihuiming
      * @date: 2023/3/6 16:39
     */
    public boolean setExpireAt(String key, Object value, Date date){
        boolean result = false;
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            redisTemplate.expireAt(key,date);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
      * 方法描述: 获取缓存
      * @param key
      * @return: Object
      * @author: laihuiming
      * @date: 2023/3/6 15:15
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
      * 方法描述: 判断是否存在对应value
      * @param key
      * @return: boolean
      * @author: laihuiming
      * @date: 2023/3/6 16:27
     */
    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    /**
      * 方法描述: 哈希添加
      * @param key
      * @param hashKey
      * @param value
      * @return: boolean
      * @author: laihuiming
      * @date: 2023/3/6 15:34
     */
    public boolean hashSet(String key,String hashKey,Object value){
        boolean result = false;
        try {
            HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.put(key,hashKey,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
      * 方法描述: 哈希获取
      * @param key
      * @param hashKey
      * @return: Object
      * @author: laihuiming
      * @date: 2023/3/6 15:54
     */
    public Object hashGet(String key,String hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
      * 方法描述: 列表添加(添加在末尾)
      * @param key
      * @param value
      * @return: boolean
      * @author: laihuiming
      * @date: 2023/3/6 16:23
     */
    public boolean listSet(String key,Object value){
        boolean result = false;
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            listOperations.rightPush(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /** 获取列表(start开始位置, 0是开始位置，end 结束位置, -1返回所有)
      * 方法描述:
      * @param key
      * @param start
      * @param end
      * @return: List<Object>
      * @author: laihuiming
      * @date: 2023/3/6 16:52
     */
    public List<Object> listGet(String key,long start,long end){
        return redisTemplate.opsForList().range(key,start,end);
    }


}
