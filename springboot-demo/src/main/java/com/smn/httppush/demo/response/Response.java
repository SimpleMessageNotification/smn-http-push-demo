package com.smn.httppush.demo.response;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author zhangyx
 * @version 1.0.0
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -4774672312419683890L;

    /**
     * 错误码
     */
    private int errCode = 0;

    /**
     * 错误信息
     */
    private String errMessage = "success";

    /**
     * 无参构造函数
     */
    public Response() {
    }

    /**
     * 构造成功的Response实例
     *
     * @param data 返回数据
     * @param <T>  泛型类型
     * @return 成功的响应数据
     */
    public static <T> Response<T> newSuccess(T data) {
        Response<T> result = new Response<>(0, "success");
        result.data = data;
        return result;
    }

    /**
     * 构造方法
     *
     * @param errCode    错误码
     * @param errMessage 错误信息
     */
    public Response(int errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    /**
     * 返回数据
     */
    private T data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}