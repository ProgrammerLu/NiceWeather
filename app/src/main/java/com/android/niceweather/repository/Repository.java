package com.android.niceweather.repository;

import com.android.niceweather.logic.dao.PlaceDao;
import com.android.niceweather.logic.dataModel.placeModel.Place;
import com.android.niceweather.logic.dataModel.placeModel.PlaceResponse;
import com.android.niceweather.logic.dataModel.weatherModel.Weather;
import com.android.niceweather.logic.isolation.HttpHelper;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class Repository {
    private final HttpHelper httpHelper = HttpHelper.getInstance();
    private final PlaceDao placeDao = PlaceDao.getInstance();

    public static Repository getInstance() {
        return RepositoryHolder.mInstance;
    }

    private static class RepositoryHolder {
        private static final Repository mInstance = new Repository();
    }

    private Repository(){ }


    public void searchPlace(String query, Action1<PlaceResponse> action1) {
        httpHelper.searchPlace(query, action1);
    }

    public void searchWeather(String locationLng, String locationLat, Action1<Weather> action1) {
        httpHelper.searchWeather(locationLng, locationLat, action1);
    }

    public void savePlace(Place place) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                placeDao.savePlace(place);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void getSavedPlace(Action1<Place> action1) {
        Observable.create(new Observable.OnSubscribe<Place>() {
            @Override
            public void call(Subscriber<? super Place> subscriber) {
                Place place = placeDao.getSavedPlace();
                subscriber.onNext(place);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(action1);
    }

    public void isSavedPlace(Action1<Boolean> action1) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                Boolean isSavedPlace = placeDao.isSavedPlace();
                subscriber.onNext(isSavedPlace);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(action1);
    }
}
