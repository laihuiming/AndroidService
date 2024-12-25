
package com.example.androidservice.base.interceptor;

import com.example.androidservice.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 @author: laihuiming
 @date: 2023/8/2
 @version: 1.0
 @description: 根据路径判断是否验证token
 **/
@Component
@Order(1)
public class MyInterceptor implements HandlerInterceptor {

    @Value("${excludePaths}")
    String excludePaths;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求路径
        String path = request.getRequestURI();
        if (isExcludePath(path)){
            return true;
        }
        //判断是不是swagger是就放行
//        HandlerMethod handlerMethod=(HandlerMethod)handler;
//        if("springfox.documentation.swagger.web.ApiResourceController".equals(handlerMethod.getBean().getClass().getName())){
//            return  true;
//        }

        //todo 验证token
        boolean flag = true;
        String token  = request.getHeader(JwtTokenUtil.HEAD_KEY);
        try {
            JwtTokenUtil.verifierToken(token);
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    private boolean isExcludePath(String path) {
        // 检查是否需要排除的路径
        String[] split = excludePaths.split(",");
        Optional<String> first = Arrays.stream(split).filter(p -> p.equals(path)).findFirst();
        return first.isPresent();
    }
}
