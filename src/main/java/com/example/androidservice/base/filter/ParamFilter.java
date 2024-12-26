
package com.example.androidservice.base.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.androidservice.base.filter.wrapper.GetHttpServletRequest;
import com.example.androidservice.base.filter.wrapper.RequestWrapper;
import com.example.androidservice.base.filter.wrapper.ResponseWrapper;
import com.example.androidservice.service.CurrencyService;
import com.example.androidservice.utils.DESUtil;
import com.example.androidservice.utils.SM4Util;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;

/**
 @author: laihuiming
 @date: 2023/12/18
 @version: 1.0
 @description: 入参解密和出参加密
 **/
@Component
public class ParamFilter implements Filter {

    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=utf-8";
    private static final String UTF8_VALUE = "UTF-8";

    private static final String Access_Control_Allow_Origin_VALUE ="Access-Control-Allow-Origin";
    private static final String Access_Control_Allow_Methods_VALUE = "Access-Control-Allow-Methods";

    @Value("${excludePaths}")
    String excludePaths;
    @Autowired
    private Environment env;
    @Autowired
    CurrencyService currencyService;

    /***
     * 不过滤的接口
     */
    private static final List<String> NoFilterApiList = Arrays.asList("","");


    private boolean isExcludePath(String path) {
        // 检查是否需要排除的路径
        String[] split = excludePaths.split(",");
        boolean flag = Arrays.asList(split).contains(path);
        if (!flag){
            flag = NoFilterApiList.stream().anyMatch(p -> p.equals(path));
        }
        return flag;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 获取请求路径
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();
        boolean excludePath = isExcludePath(path);
        String encrypt = env.getProperty("enable.encrypt");
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse) response);
        //判断是否需要解密
        if (!excludePath && "1".equals(encrypt) && !path.endsWith(".css") && !path.endsWith(".js")){
            //对request中的参数进行解密
            String token = request.getHeader("token");
            Map<String, String> requestParams = getRequestParams(request);
            String sid = requestParams.get("sid");
            String encryptType = requestParams.get("encryptType");
            String params = requestParams.get("params");
            if (StringUtils.isBlank(sid) || StringUtils.isBlank(encryptType)){
                throw new RuntimeException("接口{"+path+"}缺失基本参数");
            }
            if (StringUtils.isBlank(params)){
                //根据sid获取randomKey
                String randomKey = currencyService.getRandomKeyBySid(sid);
                try {
                    params = decryptParam(encryptType,randomKey,params);
                } catch (Exception e) {
                    throw new RuntimeException("参数解密异常");
                }
                //将params重新封装到request中
                if (RequestMethod.POST.toString().equals(request.getMethod())){
                    //form-json,目前默认post都是json
                    request = new RequestWrapper(request, params);
                }
                if (RequestMethod.GET.toString().equals(request.getMethod())){
                    Class<RequestFacade> classes = (Class<RequestFacade>) servletRequest.getClass();
                    request = pageRequest(classes,request, params);
                }

            }
            filterChain.doFilter(request,response);

            //         返回参数加密
            // 创建一个自定义响应包装器来捕获响应数据
            CharResponseWrapper wrappedResponse = new CharResponseWrapper(response);

            filterChain.doFilter(request, wrappedResponse);

            // 将响应数据作为字符串获取
            String responseData = wrappedResponse.toString();

            // 加密响应数据
            String encryptedResponse = encryptData(responseData);

            // 在实际响应中设置加密的响应数据
            response.getWriter().write(encryptedResponse);
        }else {
            filterChain.doFilter(request,response);
        }



    }

    private String encryptData(String responseData) {
        System.out.println(responseData);
        return null;
    }

    private  void writeResponse(HttpServletResponse response, String json) {
        PrintWriter printWriter=null;
        try {
            response.reset();//很重要
            response.setHeader(Access_Control_Allow_Origin_VALUE, "*");
            response.setHeader(Access_Control_Allow_Methods_VALUE, "*");
            response.setContentType(APPLICATION_JSON_UTF8);
            response.setStatus(200);
            printWriter = response.getWriter();
            printWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null){
                printWriter.close();
            }
        }

    }

    /***
     * 包装get请求解密后的参数
     *
     * @param classes
     * @param request
     * @param params
     * @return
     */
    private HttpServletRequest pageRequest(Class<RequestFacade> classes, HttpServletRequest request, String params) {
        Field requestField = null;
        try {
            requestField = classes.getDeclaredField("request");
            requestField.setAccessible(true);
            Request req = (Request) requestField.get(request);
            GetHttpServletRequest getHttpServletRequest = new GetHttpServletRequest(req);
            //封装参数
            JSONObject jObj = JSON.parseObject(params);
            if (jObj != null) {
                Iterator<String> keys = jObj.keySet().iterator();
                String key = null;
                String value = null;
                while (keys.hasNext()) {
                    key = keys.next();
                    if(jObj.get(key) == null){
                        value = null;
                    }else {
                        value = jObj.get(key) + "";
                    }
                    //重新put相同的key，替换对应的values
                    getHttpServletRequest.setParameter(key, value);
                }
            }
            return  getHttpServletRequest;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /***
     * 解密参数
     * @param encryptType
     * @param randomKey
     * @param params
     * @return
     */
    private String decryptParam(String encryptType, String randomKey, String params) throws Exception {
        //国密为SM4，非国密为DES
        if ("1".equals(encryptType)){
            params = SM4Util.decryptSm4(randomKey, params);
        }else {
            params = DESUtil.decrypt(params,randomKey);
        }
        return params;
    }

    private static Map<String,String> getRequestParams(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String,String> params = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            params.put(key, request.getParameter(key));
        }
        return params;
    }


}
