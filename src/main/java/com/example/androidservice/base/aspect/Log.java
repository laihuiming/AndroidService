package com.example.androidservice.base.aspect;

import java.lang.annotation.*;

/**
 * @author: laihuiming
 * @date: 2024/3/8
 * @version: 1.0
 * @description: 日志切面
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    public String value() default "";
    public String type() default "";
}
