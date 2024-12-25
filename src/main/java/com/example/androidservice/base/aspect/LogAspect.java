
package com.example.androidservice.base.aspect;


import cn.hutool.core.util.ObjectUtil;
import com.example.androidservice.entity.SystemLog;
import com.example.androidservice.entity.SystemUser;
import com.example.androidservice.service.LogService;
import com.example.androidservice.service.PlatFormService;
import com.example.androidservice.utils.HttpRequestLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: laihuiming
 * @date: 2024/3/8
 * @version: 1.0
 */

@Component
@Aspect
public class LogAspect {

    @Autowired
    private HttpRequestLocalUtil httpRequestLocal;

    @Autowired
    private PlatFormService platFormService;

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.example.androidservice.base.aspect.Log)")
    public void log() {

    }

    @Around("log()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        joinPoint.proceed();
        long time = System.currentTimeMillis() - start;
        saveLog(joinPoint);
        System.out.println("方法执行所耗时间:{"+time+"(ms)}");
        return joinPoint.proceed();
    }

    private void saveLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        String type = log.type();
        String value = log.value();
        //写保存日志的逻辑
        SystemUser currentUser = platFormService.getCurrentUser();
        SystemLog systemLog = new SystemLog();
        if (!ObjectUtil.isNull(currentUser)){
            systemLog.setUserId(currentUser.getId());
        }
        systemLog.setOperationType(type);

        logService.save(systemLog);
    }

}
