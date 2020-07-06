package com.zhen.mvvm.base;

import android.app.Application;

public class MvvmApp extends Application {
    private static Application instance;

    public static Application getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
