
package com.example.androidservice.utils;

import com.example.androidservice.entity.SystemUser;
import com.example.androidservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 @author: laihuiming
 @date: 2024/2/1
 @version: 1.0
 @description:
 **/
@Component
public class HttpRequestLocalUtil {

    private static final ThreadLocal<HttpServletRequest> requests =
            new ThreadLocal<HttpServletRequest>() {
                @Override protected HttpServletRequest initialValue() {
                    return null;
                }
            };

    public String getUserIdByRequestToken() {
        String token = requests.get().getHeader(JwtTokenUtil.HEAD_KEY);
        String userId = JwtTokenUtil.getUserIdByToken(token);
        return userId;
    }

    /**
     *
     * @return
     */
    public String getRequestIP(){
        return getIpAddr(requests.get());
    }

    /**
     * 获取当前网络ip
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
