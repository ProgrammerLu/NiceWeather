package com.android.niceweather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.android.niceweather.logic.isolation.HttpHelper;
import com.android.niceweather.logic.retrofitRequest.RetrofitRequest;

public class WeatherApplication extends Application {
    public static final String BASE_URL = "https://api.caiyunapp.com/";
    public static final String TOKEN = "AA0bhJZP0SGvGkTj";
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
       return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initNetWork();
    }

    private void initNetWork() {
        HttpHelper.init(new RetrofitRequest());
    }

}
