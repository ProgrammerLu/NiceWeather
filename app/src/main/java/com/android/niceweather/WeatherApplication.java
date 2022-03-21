package com.android.niceweather;

import android.app.Application;
import android.content.Context;

import com.android.niceweather.logic.isolation.HttpHelper;
import com.android.niceweather.logic.retrofitRequest.RetrofitRequest;

public class WeatherApplication extends Application {
    public static final String BASE_URL = "https://api.caiyunapp.com/";
    private static Context context;
    public static final String TOKEN = "AA0bhJZP0SGvGkTj";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initNetWork();
    }

    private void initNetWork() {
        HttpHelper.init(new RetrofitRequest());
    }

    public static Context getContext() {
        return context;
    }
}
