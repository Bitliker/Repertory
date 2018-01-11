package com.gxut.code.network;

import com.gxut.code.network.request.HttpRequest;
import com.gxut.code.network.request.Parameter;
import com.gxut.code.network.request.RequestImp;
import com.gxut.code.network.response.OnHttpCallback;

/**
 * Created by Bitliker on 2017/7/17.
 */

public class HttpClient implements RequestImp {

    private static HttpClient api;
    private static HttpRequest httpRequest;

    public static void init(HttpRequest httpRequest) {
        api();
        HttpClient.httpRequest = httpRequest;
    }

    public static HttpClient api() {
        HttpClient inst = api;
        if (inst == null) {
            synchronized (HttpClient.class) {
                inst = api;
                if (inst == null) {
                    inst = new HttpClient();
                    api = inst;
                }
            }
        }
        return inst;
    }


    @Override
    public void request(Parameter parameter, OnHttpCallback callback) {
        if (httpRequest == null) {
            new IllegalAccessException("You should call init() initialize first");
        }
        httpRequest.request(parameter, callback);
    }



}
