package com.primax.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口:统一返回类型
 *
 * @author tf
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 调用成功
     */
    public static final Integer RESULT_SUCCESS = 1;
    public static final String RESULT_SUCCESS_MSG = "操作成功";

    /**
     * 无权限访问
     */
    public static final Integer RESULT_NO_AUTHORITY = 8;
    public static final String RESULT_NO_AUTHORITY_MSG = "无权限访问";
    /**
     * 调用失败
     */
    public static final Integer RESULT_FAILURE = 9;
    public static final String RESULT_FAILURE_MSG = "系统繁忙，请稍后再试";

    //总数
    private int total;
    //1:成功; 9:失败
    private int code;
    //成功或者失败返回的消息
    private String msg;
    //成功后返回的数据
    private Object data;

    public Result() {

    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Result(int code, String msg, Object data, int total) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }
}
