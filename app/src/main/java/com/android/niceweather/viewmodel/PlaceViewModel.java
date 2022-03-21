package com.android.niceweather.viewmodel;

import android.content.Intent;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.niceweather.WeatherApplication;
import com.android.niceweather.logic.dataModel.placeModel.Place;
import com.android.niceweather.logic.dataModel.placeModel.PlaceResponse;
import com.android.niceweather.repository.Repository;
import com.android.niceweather.ui.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

public class PlaceViewModel extends ViewModel {
    private List<Place> placeList = new ArrayList<>();
    private final Repository repository = Repository.getInstance();
    private MutableLiveData<List<Place>> placeLiveData;

    public List<Place> getPlaceList() {
        return placeList;
    }

    public MutableLiveData<List<Place>> getPlaceLiveData() {
        if(placeLiveData == null) {
            placeLiveData = new MutableLiveData<>();
            placeLiveData.setValue(new ArrayList<>());
        }
        return placeLiveData;
    }

    public void searchPlace(String query) {
        repository.searchPlace(query, new Action1<PlaceResponse>() {
            @Override
            public void call(PlaceResponse placeResponse) {
                placeLiveData.setValue(placeResponse.getPlaces());
            }
        });
    }

    public void savePlace(Place place) {
        repository.savePlace(place);
    }

    public void isSavedPlace() {
        repository.isSavedPlace(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if(aBoolean) {
                    repository.getSavedPlace(new Action1<Place>() {
                        @Override
                        public void call(Place place) {
                            Intent intent  = new Intent(WeatherApplication.getContext(), WeatherActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("location_lng", place.getLocation().getLng());
                            intent.putExtra("location_lat", place.getLocation().getLat());
                            intent.putExtra("place_name", place.getName());
                            WeatherApplication.getContext().startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
