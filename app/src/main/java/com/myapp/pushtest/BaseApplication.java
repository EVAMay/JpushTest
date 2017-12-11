package com.myapp.pushtest;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lenovo on 2016/10/9.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
    }
}
