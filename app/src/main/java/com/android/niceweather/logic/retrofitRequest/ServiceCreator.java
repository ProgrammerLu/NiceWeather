package com.android.niceweather.logic.retrofitRequest;

import com.android.niceweather.WeatherApplication;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(WeatherApplication.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public static <T> T create(Class<T> serviceClass) {
       return retrofit.create(serviceClass);
    }

}
