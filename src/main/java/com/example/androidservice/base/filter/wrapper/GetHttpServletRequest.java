
package com.example.androidservice.base.filter.wrapper;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.tomcat.util.http.Parameters;

import java.lang.reflect.Field;

/**
 @author: laihuiming
 @date: 2023/12/25
 @version: 1.0
 @description:
 **/
public class GetHttpServletRequest extends RequestFacade {
    public GetHttpServletRequest(Request request) {
        super(request);
    }

    public void setParameter(String name,String value) throws NoSuchFieldException, IllegalAccessException {
        Class<Request> requestClass = (Class<Request>) this.request.getClass();
        Field field = requestClass.getDeclaredField("coyoteRequest");
        field.setAccessible(true);
        org.apache.coyote.Request coyoteRequest = (org.apache.coyote.Request) field.get(this.request);
        Class<org.apache.coyote.Request> coyoteRequestClasses = (Class<org.apache.coyote.Request>) coyoteRequest.getClass();
        Field parametersField = coyoteRequestClasses.getDeclaredField("parameters");
        parametersField.setAccessible(true);
        org.apache.tomcat.util.http.Parameters parameters = (Parameters) parametersField.get(coyoteRequest);
        parameters.addParameter(name,value);
    }
}
