package com.android.niceweather.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.niceweather.logic.dataModel.weatherModel.Weather;
import com.android.niceweather.repository.Repository;
import rx.functions.Action1;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<Weather> weatherLiveData;
    private String locationLng;
    private String locationLat;
    private String placeName;
    private final Repository repository = Repository.getInstance();


    public String getPlaceName() {
        return placeName;
    }

    public String getLocationLng() {
        return locationLng;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public MutableLiveData<Weather> getWeatherLiveData() {
        if(weatherLiveData == null) {
            weatherLiveData = new MutableLiveData<>();
        }
        return weatherLiveData;
    }

    public void searchWeather(String locationLng, String locationLat) {
        repository.searchWeather(locationLng, locationLat, new Action1<Weather>() {
            @Override
            public void call(Weather weather) {
                weatherLiveData.setValue(weather);
            }
        });
    }


}
