package com.gxut.code.network.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 公共请求头
 * Created by Bitliker on 2017/6/28.
 */
public abstract class LogInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Response response = chain.proceed(request);
		MediaType mediaType = response.body().contentType();
		String message = response.body().string();
		Response resultResponse = response.newBuilder().body(ResponseBody.create(mediaType, message)).build();
		if (showLogAble())
			showLog(request, message);
		result(message);
		return resultResponse;
	}

	private void showLog(Request request, String message) {
		String url = request.url().toString();
		log(request.method() + ":" + getMessage(url));
		if ("POST".equals(request.method())) {
			String postBodyString = null;
			if (request.body() instanceof FormBody) {
				FormBody requestBody = (FormBody) request.body();
				Map<String, Object> body = new HashMap<>();
				for (int i = 0; i < requestBody.size(); i++) {
					body.put(requestBody.encodedName(i), requestBody.encodedValue(i));
				}
				postBodyString = mapToJson(body);
			} else {
				postBodyString = bodyToString(request.body());
			}
			log("body=" + getMessage(postBodyString));
		}
		Headers headers = request.headers();
		if (headers != null && headers.size() > 0) {
			Map<String, Object> header = new HashMap<>();
			for (String name : headers.names()) {
				header.put(name, headers.get(name));
			}
			log("header=" + mapToJson(header));
		}
		log(message);
	}

	private String mapToJson(Map<String, Object> map) {
		if (map == null || map.isEmpty()) return "";
		StringBuilder builder = new StringBuilder("{\n");
		for (Map.Entry<String, Object> e : map.entrySet()) {
			builder.append("\"" + e.getKey() + "\":");
			if (e.getValue() instanceof String || e.getValue() instanceof CharSequence) {
				builder.append("\"" + e.getValue() + "\",\n");
			} else {
				builder.append(e.getValue() + ",\n");
			}
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.deleteCharAt(builder.length() - 1);
		builder.append("\n}");
		return builder.toString();
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


	private String getMessage(String message) {
		try {
			return URLDecoder.decode(message, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log("showMessage=" + e.getMessage());
			e.printStackTrace();
			if (e != null)
				return e.getMessage();
			else return "";
		}
	}

	protected abstract boolean showLogAble();

	protected abstract void log(String logMsg);

	protected abstract void result(String message);


}
