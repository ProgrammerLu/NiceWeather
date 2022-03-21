package com.android.niceweather.logic.isolation;

import com.android.niceweather.logic.dataModel.placeModel.PlaceResponse;
import com.android.niceweather.logic.dataModel.weatherModel.Weather;
import rx.functions.Action1;

public interface IHttpRequest {
    void searchPlace(String query, Action1<PlaceResponse> action1);
    void searchWeather(String locationLng, String locationLat, Action1<Weather> action1);
}
