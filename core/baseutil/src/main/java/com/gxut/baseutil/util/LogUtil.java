//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gxut.baseutil.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * android log 工具类，方便打印全部信息，防止由于log信息过长出现android studio打印不完全的问题
 * init 使用时候最好初始化，在正式发布时候设置不打印log
 */
public class LogUtil {

    private static boolean needShowLog = true;

    public static void init(boolean needShowLog) {
        LogUtil.needShowLog = needShowLog;
    }

    public static void i(String message) {
        i(getFileLineMethod(), message);
    }

    public static void d(String message) {
        d(getFileLineMethod(), message);
    }

    public static void v(String message) {
        v(getFileLineMethod(), message);
    }

    public static void e(String message) {
        e(getFileLineMethod(), message);
    }

    public static void i(String tag, String message) {
        showI(tag, message);
    }

    public static void d(String tag, String message) {
        showD( tag, message);
    }

    public static void e(String tag, String message) {
        showE( tag, message);
    }

    public static void v(String tag, String message) {
        showV(  tag, message);
    }


    private static String getFileLineMethod() {
        StackTraceElement traceElement = (new Exception()).getStackTrace()[2];
        StringBuffer toStringBuffer = (new StringBuffer("[")).append(traceElement.getFileName()).append(" | ").append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("]");
        return toStringBuffer.toString();
    }


    private static void showI(String tag, String mesage) {
        if (LogUtil.needShowLog && !TextUtils.isEmpty(mesage) && !TextUtils.isEmpty(tag)) {
            if (mesage.length() > 3000) {
                int endLength = 0;
                while (endLength < mesage.length()) {
                    Log.i(tag, mesage.substring(endLength, Math.min(mesage.length(), endLength += 3000)));
                }
            }else {
                Log.i(tag, mesage);
            }
        }
    }

    private static void showD(String tag, String mesage) {
        if (LogUtil.needShowLog && !TextUtils.isEmpty(mesage) && !TextUtils.isEmpty(tag)) {
            if (mesage.length() > 3000) {
                int endLength = 0;
                while (endLength < mesage.length()) {
                    Log.d(tag, mesage.substring(endLength, Math.min(mesage.length(), endLength += 3000)));
                }
            }
        } else {
            Log.d(tag, mesage);
        }
    }

    private static void showV(String tag, String mesage) {
        if (LogUtil.needShowLog && !TextUtils.isEmpty(mesage) && !TextUtils.isEmpty(tag)) {
            if (mesage.length() > 3000) {
                int endLength = 0;
                while (endLength < mesage.length()) {
                    Log.v(tag, mesage.substring(endLength, Math.min(mesage.length(), endLength += 3000)));
                }
            }
        } else {
            Log.v(tag, mesage);
        }
    }

    private static void showE(String tag, String mesage) {
        if (LogUtil.needShowLog && !TextUtils.isEmpty(mesage) && !TextUtils.isEmpty(tag)) {
            if (mesage.length() > 3000) {
                int endLength = 0;
                while (endLength < mesage.length()) {
                    Log.e(tag, mesage.substring(endLength, Math.min(mesage.length(), endLength += 3000)));
                }
            }
        } else {
            Log.e(tag, mesage);
        }
    }


    public  static void showLong(Context context,String method, String tag, String mesage) {
        if (needShowLog) {
            try {
                if (!needShowLog || tag == null || mesage == null) {
                    return;
                }
                Class cl = Class.forName("android.util.Log");
                Method m = cl.getMethod(method, new Class[]{String.class, String.class});
                if (mesage.length() > 3000) {
                    int chunkCount = mesage.length() / 3000;

                    for (int i = 0; i <= chunkCount; ++i) {
                        int max = 3000 * (i + 1);
                        m.invoke(context, new Object[]{tag, mesage.substring(3000 * i, Math.min(mesage.length(), max))});
                    }
                } else {
                    m.invoke(context, new Object[]{tag, mesage});
                }
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }
    }
}
