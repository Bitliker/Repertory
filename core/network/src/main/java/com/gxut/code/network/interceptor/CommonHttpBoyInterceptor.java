package com.gxut.code.network.interceptor;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 公共请求头
 * Created by Bitliker on 2017/9/28.
 */

public abstract class CommonHttpBoyInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Request.Builder requestBuilder = request.newBuilder();
		Map<String, String> header = getHeader();
		Map<String, Object> params = getParams();

		if (header != null && !header.isEmpty()) {
			Set<String> headerNames = null;
			Headers httpHeaders = request.headers();
			if (httpHeaders != null && httpHeaders.size() > 0) {
				headerNames = httpHeaders.names();
			}
			for (Map.Entry<String, String> e : header.entrySet()) {
				if (e.getKey() != null && e.getKey() != null && (headerNames == null || !headerNames.contains(e.getKey()))) {
					requestBuilder.addHeader(e.getKey(), e.getValue());
				}
			}
		}
		if (params != null && !params.isEmpty()) {
			String method = request.method();
			String url = request.url().toString();
			if (method.equals("GET")) {
				url =param2Url(url, params);
				requestBuilder.url(url);
			} else if ("POST".equals(method)) {
				String postBodyString = bodyToString(request.body());
				FormBody.Builder formBodyBuilder = new FormBody.Builder();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					if (!postBodyString.contains(entry.getKey())) {
						formBodyBuilder.add(entry.getKey(), (String) entry.getValue());
					}
				}
				RequestBody formBody = formBodyBuilder.build();
				postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
				requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), postBodyString));
			}
		}
		request = requestBuilder.build();
		return chain.proceed(request);
	}

	public static String param2Url(String url, Map<String, Object> param) {
		if (url == null || url.length() <= 0)
			return "";
		StringBuilder urlBuilder = new StringBuilder(url);
		if (param == null || param.isEmpty()) {
			return urlBuilder.toString();
		}
		if (!url.contains("?"))
			urlBuilder.append("?");
		else urlBuilder.append("&");
		for (Map.Entry<String, Object> e : param.entrySet()) {
			if (e.getValue() == null || e.getKey() == null || e.getKey().length() <= 0)
				continue;
			String value = null;
			try {
				value = URLEncoder.encode(e.getValue().toString(), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				value = e.getValue().toString();
			}
			if (!urlBuilder.toString().contains(e.getKey() + "=")) {
				urlBuilder.append(String.format("%s=%s", e.getKey(), value));
				urlBuilder.append("&");
			}
		}
		if (urlBuilder.length() > 1)
			urlBuilder.deleteCharAt(urlBuilder.length() - 1);
		return urlBuilder.toString();
	}
	private String bodyToString(final RequestBody request) {
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

	protected abstract Map<String, String> getHeader();

	protected abstract Map<String, Object> getParams();

}
