package com.android.niceweather.logic.isolation;

import com.android.niceweather.logic.dataModel.placeModel.Place;
import com.android.niceweather.logic.dataModel.weatherModel.Weather;

import java.util.List;

import rx.functions.Action1;

public interface IHttpRequest {
    void searchPlace(String query, Action1<List<Place>> action1);
    void searchWeather(String locationLng, String locationLat, Action1<Weather> action1);
}
