package com.example.androidservice.base;

/**
 * @Author laihuiming
 * @description:
 * @Date 2022/5/9 9:02
 * @Version 1.0
 */
public class Result<T> {
    private String code;
    private T data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static Result success(){
        Result result = new Result<>();
        result.code="200";
        result.msg="成功";
        return result;
    }

    public static Result success(String data){
        Result result = new Result<>();
        result.code="200";
        result.msg="成功";
        result.data = data;
        return result;
    }

    public static Result fail(String msg){
        Result result = new Result<>();
        result.code="500";
        result.msg=msg;
        return result;
    }
}
