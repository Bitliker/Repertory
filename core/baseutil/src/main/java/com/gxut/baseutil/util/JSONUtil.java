package com.gxut.baseutil.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 1.解析json为基本数字类型
 * 2.实体类的转化工具方法
 * Created by Bitlike on 2018/1/4.
 */

public class JSONUtil {
    private static final String TAG = "JSONUtil";

    public static Map<String, Object> parseMap(String message) {
        if (!TextUtils.isEmpty(message)) try {
            Map<String, Object> map = new HashMap<>();
            JSONObject o = new JSONObject(message);
            Iterator<String> ints = o.keys();
            while (ints.hasNext()) {
                String key = ints.next();
                map.put(key, getString(o, key));
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject parseObject(String message) {
        try {
            return new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static JSONArray parseJSONArray(String message) {
        try {
            return new JSONArray(message);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public static JSONObject getJSONObject(JSONObject object, String... keys) {
        if (keys != null && keys.length > 0) try {
            for (String key : keys) {
                if (object.has(key) && !object.isNull(key) && object.get(key) instanceof JSONObject) {
                    return object.getJSONObject(key);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                Log.i(TAG, e.getMessage());
            }
        }
        return new JSONObject();
    }

    public static JSONArray getJSONArray(JSONObject object, String... keys) {
        if (keys != null && keys.length > 0) try {
            for (String key : keys) {
                if (object.has(key) && !object.isNull(key) && object.get(key) instanceof JSONArray) {
                    return object.getJSONArray(key);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                Log.i(TAG, e.getMessage());
            }
        }
        return new JSONArray();
    }

    public static boolean getBoolean(JSONObject object, String... keys) {
        if (keys != null && keys.length > 0) try {
            for (String key : keys) {
                if (object.has(key) && !object.isNull(key) && object.get(key) instanceof Boolean) {
                    return object.getBoolean(key);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                Log.i(TAG, e.getMessage());
            }
        }
        return false;

    }

    public static String getString(JSONObject object, String... keys) {
        if (keys != null && keys.length > 0) try {
            for (String key : keys) {
                if (object.has(key) && !object.isNull(key)) {
                    return object.getString(key);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                Log.i(TAG, e.getMessage());
            }
        }
        return "";
    }

    public static int getInt(JSONObject object, String... keys) {
        return getInt(object, 0, keys);
    }

    public static int getInt(JSONObject object, int defValues, String... keys) {
        if (keys != null && keys.length > 0) try {
            for (String key : keys) {
                if (object.has(key) && !object.isNull(key) && object.get(key) instanceof Integer) {
                    return object.getInt(key);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                Log.i(TAG, e.getMessage());
            }
        }
        return defValues;
    }

    public static double getDouble(JSONObject object, String... keys) {
        return getDouble(object, 0, keys);
    }

    public static double getDouble(JSONObject object, double defValues, String... keys) {
        if (keys != null && keys.length > 0) try {
            for (String key : keys) {
                if (object.has(key) && !object.isNull(key) && object.get(key) instanceof Double) {
                    return object.getDouble(key);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                Log.i(TAG, e.getMessage());
            }
        }
        return defValues;
    }


    public static long getLong(JSONObject object, String... keys) {
        return getLong(object, 0, keys);
    }

    public static long getLong(JSONObject object, long defValues, String... keys) {
        if (keys != null && keys.length > 0) try {
            for (String key : keys) {
                if (object.has(key) && !object.isNull(key) && object.get(key) instanceof Long) {
                    return object.getLong(key);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                Log.i(TAG, e.getMessage());
            }
        }
        return defValues;
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


    public static <T> String mapToJson(Map<String, T> map) {
        if (map == null || map.isEmpty()) return "";
        StringBuilder builder = new StringBuilder("{\n");
        for (Map.Entry<String, T> e : map.entrySet()) {
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

    public static String mapsToJson(List<Map<String, Object>> maps) {
        if (maps == null || maps.isEmpty()) return "";
        StringBuilder builder = new StringBuilder("[\n");
        for (Map<String, Object> m : maps) {
            builder.append("{\n");
            for (Map.Entry<String, Object> e : m.entrySet()) {
                builder.append("\"" + e.getKey() + "\":");
                if (e.getValue() instanceof String || e.getValue() instanceof CharSequence) {
                    builder.append("\"" + e.getValue() + "\",\n");
                } else {
                    builder.append(e.getValue() + ",\n");
                }
            }
            if (builder.length() > 1)
                builder.deleteCharAt(builder.length() - 1);
            if (builder.length() > 1)
                builder.deleteCharAt(builder.length() - 1);
            builder.append("\n},\n");
        }
        if (builder.length() > 1)
            builder.deleteCharAt(builder.length() - 1);
        if (builder.length() > 1)
            builder.deleteCharAt(builder.length() - 1);
        builder.append("\n]");
        return builder.toString();
    }


    public static String toJSONString(Object o) {
        return mapToJson(toJSONMap(o));
    }



    public static Map<String, Object> toJSONMap(Object model) {
        Map<String, Object> map = new HashMap<>();
        if (model != null) try {
            Field[] field = model.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {     //遍历所有属性
                String name = field[j].getName();    //获取属性的名字
                String nameB = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
                try {
                    Method m = model.getClass().getMethod("get" + nameB);
                    Object value = m.invoke(model);
                    map.put(name, value);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {

        }
        return map;
    }

    public static <T> T paseObject(Map<String, Object> map, Class<T> tClass) {
        T t = null;
        if (tClass != null && map != null && !map.isEmpty()) try {
            t = tClass.newInstance();
            Field[] field = tClass.getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {     //遍历所有属性
                String name = field[j].getName();    //获取属性的名字
                Class<?> type = field[j].getType();    //获取属性的名字
                Object val = map.get(name);
                name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
                try {
                    Method m = tClass.getMethod("set" + name, type);
                    m.invoke(t, val);
                } catch (Exception e) {
                    System.out.print("出错了" + e.getMessage());
                }
            }
        } catch (Exception e) {

        }
        return t;
    }
}
