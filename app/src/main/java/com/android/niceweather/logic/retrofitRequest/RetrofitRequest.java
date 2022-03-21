package com.android.niceweather.logic.retrofitRequest;

import com.android.niceweather.logic.dataModel.placeModel.PlaceResponse;
import com.android.niceweather.logic.dataModel.weatherModel.DailyResponse;
import com.android.niceweather.logic.dataModel.weatherModel.RealtimeResponse;
import com.android.niceweather.logic.dataModel.weatherModel.Weather;
import com.android.niceweather.logic.isolation.IHttpRequest;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RetrofitRequest implements IHttpRequest {

    @Override
    public void searchPlace(String query, Action1<PlaceResponse> action1) {
        PlaceService placeService = ServiceCreator.create(PlaceService.class);
        placeService.searchPlaces(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(action1);
    }

    @Override
    public void searchWeather(String locationLng, String locationLat, Action1<Weather> action1) {
        WeatherService weatherService = ServiceCreator.create(WeatherService.class);
        Observable<RealtimeResponse> obs1 = weatherService.getRealtimeWeather(locationLng, locationLat).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<DailyResponse> obs2 = weatherService.getDailyWeather(locationLng, locationLat).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observable.zip(obs1, obs2, (realtimeResponse, dailyResponse) -> {
            if(realtimeResponse.getStatus().equals("ok") && dailyResponse.getStatus().equals("ok"))
                return new Weather(realtimeResponse.getResult().getRealtime()
                        , dailyResponse.getResult().getDaily());
            else {
                throw new RuntimeException("realtime response status is " + realtimeResponse.getStatus()
                        + "daily response status is " + dailyResponse.getStatus());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(action1);
    }
}
