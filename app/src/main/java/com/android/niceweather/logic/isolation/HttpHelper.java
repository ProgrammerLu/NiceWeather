package com.android.niceweather.logic.isolation;

import com.android.niceweather.logic.dataModel.placeModel.PlaceResponse;
import com.android.niceweather.logic.dataModel.weatherModel.Weather;

import rx.functions.Action1;

public class HttpHelper implements IHttpRequest{
    public static HttpHelper getInstance() {
        return HttpHelperHolder.mInstance;
    }

    private HttpHelper(){}

    private static class HttpHelperHolder {
        private static final HttpHelper mInstance = new HttpHelper();
    }

    private static IHttpRequest mHttpRequest = null;

    public static void init(IHttpRequest httpRequest) {
        mHttpRequest = httpRequest;
    }

    @Override
    public void searchPlace(String query, Action1<PlaceResponse> action1) {
        mHttpRequest.searchPlace(query, action1);
    }

    @Override
    public void searchWeather(String locationLng, String locationLat, Action1<Weather> action1) {
        mHttpRequest.searchWeather(locationLng, locationLat, action1);
    }
}
