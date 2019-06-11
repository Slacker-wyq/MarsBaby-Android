package com.mars.baby;

import android.app.Application;

import com.vondear.rxtools.RxTool;

import org.xutils.x;

import cn.bmob.v3.Bmob;


public class MyApplication extends Application {

    public static final String  BASE_URL = "http://10.1.54.89:8080/MarsBaby/";
    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        Bmob.initialize(this,"0646fa9cefb3f3e66a2e6e26901c26ac");
    }
}