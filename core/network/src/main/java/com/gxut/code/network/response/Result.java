package com.gxut.code.network.response;

import com.gxut.code.network.request.Tags;


/**
 * Created by Bitliker on 2017/7/19.
 */

public class Result {
    public static final int NETWORK_OUT = 543 >> 1;//网络连接失败
    public static final int DATA_EXCEPTION = 542 >> 1;//数据处理异常
    public static final int PARAMETER_ERROR = 541 >> 1;//参数错误
    public static final int REQUEST_ERROR = 500;//请求失败
    public static final int REQUEST_OK = 200;//请求成功

    protected int code;
    protected String message;
    protected Tags tag;

    public Result(Result result) {
        this.code = result.code;
        this.message = result.message;
        this.tag = result.tag;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public Tags getTags() {
        return tag == null ? new Tags() : tag;
    }

    public Object getTag() {
        return getTags().getTag();
    }

    public Object getTag(int key) {
        return getTags().get(key);
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public int getRecord() {
        return getTags().getRecord();
    }

    public Result(int code) {
        this.code = code;
    }

    public Result() {
        this.code = REQUEST_ERROR;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean requestOK() {
        return code==200;
    }
}

