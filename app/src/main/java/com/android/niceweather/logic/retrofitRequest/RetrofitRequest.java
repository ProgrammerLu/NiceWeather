package com.android.niceweather.logic.retrofitRequest;

import com.android.niceweather.logic.dataModel.placeModel.Place;
import com.android.niceweather.logic.dataModel.placeModel.PlaceResponse;
import com.android.niceweather.logic.dataModel.weatherModel.DailyResponse;
import com.android.niceweather.logic.dataModel.weatherModel.RealtimeResponse;
import com.android.niceweather.logic.dataModel.weatherModel.Weather;
import com.android.niceweather.logic.isolation.IHttpRequest;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class RetrofitRequest implements IHttpRequest {

    @Override
    public void searchPlace(String query, Action1<List<Place>> action1) {
        PlaceService placeService = ServiceCreator.create(PlaceService.class);
        placeService.searchPlaces(query)
                .filter(new Func1<PlaceResponse, Boolean>() {
                    @Override
                    public Boolean call(PlaceResponse placeResponse) {
                        return placeResponse.getStatus().equals("ok");
                    }
                })
                .map(new Func1<PlaceResponse,List<Place>>() {
                    @Override
                    public List<Place> call(PlaceResponse placeResponse) {
                        return placeResponse.getPlaces();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1);
    }

    @Override
    public void searchWeather(String locationLng, String locationLat, Action1<Weather> action1) {
        WeatherService weatherService = ServiceCreator.create(WeatherService.class);

        Observable<RealtimeResponse.Realtime> obs1 = weatherService.getRealtimeWeather(locationLng, locationLat)
                .filter(new Func1<RealtimeResponse, Boolean>() {
                    @Override
                    public Boolean call(RealtimeResponse realtimeResponse) {
                        return realtimeResponse.getStatus().equals("ok");
                    }
                })
                .map(new Func1<RealtimeResponse, RealtimeResponse.Realtime>() {
                    @Override
                    public RealtimeResponse.Realtime call(RealtimeResponse realtimeResponse) {
                        return realtimeResponse.getResult().getRealtime();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<DailyResponse.Daily> obs2 = weatherService.getDailyWeather(locationLng, locationLat)
                .filter(new Func1<DailyResponse, Boolean>() {
                    @Override
                    public Boolean call(DailyResponse dailyResponse) {
                        return dailyResponse.getStatus().equals("ok");
                    }
                })
                .map(new Func1<DailyResponse, DailyResponse.Daily>() {
                    @Override
                    public DailyResponse.Daily call(DailyResponse dailyResponse) {
                        return dailyResponse.getResult().getDaily();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable.zip(obs1, obs2, new Func2<RealtimeResponse.Realtime, DailyResponse.Daily, Weather>() {
            @Override
            public Weather call(RealtimeResponse.Realtime realtime, DailyResponse.Daily daily) {
                return new Weather(realtime, daily);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(action1);

    }
}
