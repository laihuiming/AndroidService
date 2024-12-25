
package com.example.androidservice.base.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

/**
 @author: laihuiming
 @date: 2024/2/29
 @version: 1.0
 @description:
 **/
public class CharResponseWrapper extends HttpServletResponseWrapper {
    private final CharArrayWriter charArray = new CharArrayWriter();

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(charArray);
    }

    @Override
    public String toString() {
        return charArray.toString();
    }
}
