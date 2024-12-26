/**
 * 版权声明 厦门法度信息科技有限公司，版权所有 违者必究
 */
package com.example.androidservice.constant;

/**
 @author: laihuiming
 @date: 2024/12/26
 @version: 1.0
 @description: redis缓存key
 **/
public class RedisCacheKeyConstant {
    public static final String REDIS_CACHE_KEY_PREFIX = "android_service:";

    /** 验证码 */
    public static final String VERIFICATION_CODE = REDIS_CACHE_KEY_PREFIX + "verification_code:";
    /** sid做key,random_key做value */
    public static final String RANDOM_KEY = REDIS_CACHE_KEY_PREFIX + "random_key:";
}
