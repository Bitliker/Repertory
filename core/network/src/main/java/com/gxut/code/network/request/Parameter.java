package com.gxut.code.network.request;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bitliker on 2017/7/18.
 */

public class Parameter {
    public static final int GET = 0x11;
    public static final int POST = 0x12;
    public static final int POST_JSON = 0x13;
    public static final int DOWNLOAD = 0x14;
    public static final int UPLOAD = 0x15;


    public static final String MEDIA_TYPE_XWWW = "application/x-www-form-urlencoded;charset=utf-8";
    public static final String MEDIA_TYPE_JSON = "application/json;charset=utf-8";
    public static final String MEDIA_TYPE_PLAIN = "text/plain;charset=utf-8";
    public static final String MEDIA_TYPE_STREAM = "application/octet-stream;charset=utf-8";
    public static final String MEDIA_TYPE_MULTIPART = "multipart/form-data;charset=utf-8";


    private int mode = GET;//请求模式
    private String url;//请求的url
    private String mediaType = MEDIA_TYPE_JSON;
    private Map<String, Object> params;
    private Map<String, String> headers;
    private Tags tag;

    private Parameter() {
        tag = new Tags();
        params = new HashMap<>();
        headers = new HashMap<>();
    }

    public Map<String, String> getHeaders() {
        return headers == null ? new HashMap<String, String>() : headers;
    }

    public Map<String, Object> getParams() {
        return params == null ? new HashMap<String, Object>() : params;
    }

    public Tags getTag() {
        return tag == null ? new Tags() : tag;
    }

    public int getMode() {
        return mode == 0 ? GET : mode;
    }

    public String mergeUrl() {
        if (mode == GET) {
            return  param2Url(url, params);
        } else {
            return url;
        }
    }

    public String getUrl() {
        return url;
    }

    public String getMediaType() {
        return mediaType;
    }

    public static class Builder {

        private Parameter request;

        public Builder(Parameter request) {
            this.request = request;
        }

        public Builder() {
            this.request = new Parameter();
        }


        public Builder mode(@Duration int mode) {
            this.request.mode = mode;
            return this;
        }

        public Builder url(String url) {
            if (url != null) {
                this.request.url = url;
            }
            return this;
        }

        public Builder mediaType(String mediaType) {
            if (mediaType != null) {
                this.request.mediaType = mediaType;
            }
            return this;
        }

        public Builder headers(Map<String, String> header) {
            if (header != null) {
                this.request.headers = header;
            }
            return this;
        }

        public Builder addHeaders(String key, String value) {
            this.request.getHeaders().put(key, value);
            return this;
        }

        public Builder params(Map<String, Object> params) {
            if (params != null) {
                this.request.params = params;
            }
            return this;
        }

        public Builder addParams(String key, Object value) {
            this.request.getParams().put(key, value);
            return this;
        }

        public Builder tag(Object tag) {
            if (tag != null) {
                this.request.getTag().tag(tag);
            }
            return this;
        }

        public Builder record(int code) {
            this.request.getTag().record(code);
            return this;
        }

        public Builder addTag(int key, Object values) {
            if (values != null) {
                this.request.getTag().put(key, values);
            }
            return this;
        }

        public Parameter builder() {
            return request;
        }


        public Parameter bulid() {
            return request;
        }
    }


    @IntDef({GET, POST, POST_JSON, DOWNLOAD, UPLOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public static String param2Url(String url, Map<String, Object> param) {
        if (TextUtils.isEmpty(url))
            return "";
        StringBuilder urlBuilder = new StringBuilder(url);
        if (param == null || param.isEmpty()) {
            return urlBuilder.toString();
        }
        if (!url.contains("?"))
            urlBuilder.append("?");
        else urlBuilder.append("&");
        for (Map.Entry<String, Object> e : param.entrySet()) {
            if (e.getValue() == null || TextUtils.isEmpty(e.getKey()))
                continue;
            String value = null;
            try {
                value = URLEncoder.encode(e.getValue().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                value = e.getValue().toString();
            }
            urlBuilder.append(String.format("%s=%s", e.getKey(), value));
            urlBuilder.append("&");
        }
        if (urlBuilder.length() > 1)
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        return urlBuilder.toString();
    }

}
