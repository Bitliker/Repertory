package com.gxut.baseutil.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;

/**
 * Created by Bitlike on 2017/12/5.
 */

public abstract class BaseApplication extends Application {

    private static boolean DEBUG = false;


    @Override
    public void onCreate() {
        super.onCreate();
        initDebug();
        //初始化配置
        try {
            initConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    initConfigThread();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    protected abstract void initConfig() throws Exception;

    protected abstract void initConfigThread() throws Exception;


    public static boolean isDebug() {
        return DEBUG;
    }

    private void initDebug() {
        try {
            ApplicationInfo info = getApplicationInfo();
            DEBUG = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            DEBUG = false;
        }
    }
}
