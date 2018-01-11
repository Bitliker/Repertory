package com.gxut.code.network.request;


import android.text.TextUtils;

import com.gxut.code.network.response.OnHttpCallback;
import com.gxut.code.network.response.Result;
import com.gxut.code.network.ssl.DefaultSSLConfig;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Bitliker on 2017/7/3.
 */

public class OkHttpRequest extends HttpRequest {
    private OkHttpClient okHttpClient;

    @Override
    protected HttpRequest init() {
        if (sslConfig == null) sslConfig = new DefaultSSLConfig();
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(readTimeout, TimeUnit.SECONDS)// 设置超时时间
                .readTimeout(writeTimeout, TimeUnit.SECONDS)// 设置读取超时时间
                .writeTimeout(connectTimeout, TimeUnit.SECONDS)// 设置写入超时时间
                .retryOnConnectionFailure(maxRetryCount > 0)
                .sslSocketFactory(sslConfig.createSSLSocketFactory(),
                        sslConfig.createTrustAllCerts())
                .hostnameVerifier(sslConfig.createHostnameVerifier());
        if (chcheDirectory != null)
            builder.cache(new Cache(chcheDirectory, chcheMaxSize));
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor i : interceptors)
                builder.addInterceptor(i);
        }
        okHttpClient = builder.build();
        return this;
    }

    @Override
    public void request(final Parameter parameter, final OnHttpCallback callback) {
        Observable.create(new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Result> emitter) throws Exception {
                Result result = new Result();
                try {
                    Response response = null;
                    if (parameter == null) {
                        result.setCode(Result.PARAMETER_ERROR);
                        result.setMessage("parameter is null!!!");
                    } else {
                        switch (parameter.getMode()) {
                            case Parameter.POST:
                            case Parameter.POST_JSON:
                                response = post(parameter);
                                break;
                            case Parameter.GET:
                            default:
                                response = get(parameter);
                        }
                        if (response == null) {
                            result.setMessage("Request is error!!!!");
                        } else {
                            result.setCode(response.code());
                            result.setMessage(response.body().string());
                        }
                    }
                } catch (Exception e) {
                    result.setCode(Result.DATA_EXCEPTION);
                    result.setMessage(e.getMessage());
                }
                result.setTag(parameter.getTag());
                emitter.onNext(result);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(@NonNull Result result) throws Exception {
                        if (result != null && result.requestOK()) {
                            if (callback != null) {
                                callback.onSuccess(result.getTags().getRecord(),result.getMessage(),result.getTags());
                            }
                        } else {
                            if (callback != null) {
                                callback.onFailure(result.getTags().getRecord(),result.getMessage(),result.getTags());
                            }
                        }
                    }
                });
    }

    private Response post(final Parameter parameter) throws Exception {
        Request.Builder builder = new Request.Builder();
        builder.url(parameter.mergeUrl());
        RequestBody body = getBody(parameter);
        if (parameter.getMode() == Parameter.POST_JSON) {
            builder.post(RequestBody.create(MediaType.parse(parameter.getMediaType()),bodyToString(body)));
        } else {
            builder.post(body);
        }
        return okHttpClient.newCall(builder.build()).execute();
    }


    private Response get(final Parameter parameter) throws Exception {
        Request.Builder builder = new Request.Builder().url(parameter.mergeUrl());
        Headers2Builder(builder, parameter);
        return okHttpClient.newCall(builder.build()).execute();
    }


    private RequestBody getBody(Parameter parameter) {
        if (parameter.getMode() == Parameter.POST) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            if (!parameter.getParams().isEmpty()) {
                for (Map.Entry<String, Object> e : parameter.getParams().entrySet()) {
                    if (e.getValue() == null || TextUtils.isEmpty(e.getKey()))
                        continue;
                    bodyBuilder.add(e.getKey(), e.getValue().toString());
                }
            }
            return bodyBuilder.build();
        } else {
            return new FormBody.Builder().build();
        }
    }

    private void Headers2Builder(Request.Builder builder, Parameter parameter) {
        if (!parameter.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> e : parameter.getHeaders().entrySet()) {
                if (e.getValue() == null || TextUtils.isEmpty(e.getKey()))
                    continue;
                builder.addHeader(e.getKey(), e.getValue().toString());
            }
        }
        builder.addHeader("Content-Type", parameter.getMediaType());
    }
    public static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
