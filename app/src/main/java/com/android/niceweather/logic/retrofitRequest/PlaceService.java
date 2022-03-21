package com.android.niceweather.logic.retrofitRequest;

import com.android.niceweather.WeatherApplication;
import com.android.niceweather.logic.dataModel.placeModel.PlaceResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface PlaceService {

    @GET("v2/place?token=" + WeatherApplication.TOKEN + "&lang=zh_CN")
    Observable<PlaceResponse> searchPlaces(@Query("query") String query);
}
