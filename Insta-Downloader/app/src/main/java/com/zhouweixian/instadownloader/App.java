package com.zhouweixian.instadownloader;

import android.app.Application;
import android.content.Context;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;


public class App extends Application{
    public static Context app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=getApplicationContext();
        RxRetrofitApp.init(this,BuildConfig.DEBUG);
    }
}
