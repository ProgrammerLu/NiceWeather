package com.android.niceweather.logic.retrofitRequest;

import com.android.niceweather.logic.dataModel.weatherModel.DailyResponse;
import com.android.niceweather.logic.dataModel.weatherModel.RealtimeResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface WeatherService {
    @GET("v2.5/AA0bhJZP0SGvGkTj/{lng},{lat}/realtime.json")
    Observable<RealtimeResponse> getRealtimeWeather(@Path("lng") String lng, @Path("lat") String lat);

    @GET("v2.5/AA0bhJZP0SGvGkTj/{lng},{lat}/daily.json")
    Observable<DailyResponse> getDailyWeather(@Path("lng") String lng, @Path("lat") String lat);
}
