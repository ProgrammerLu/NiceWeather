package com.android.niceweather.logic.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.niceweather.WeatherApplication;
import com.android.niceweather.logic.dataModel.placeModel.Place;
import com.google.gson.Gson;

public class PlaceDao {
    private PlaceDao() {
    }

    public static PlaceDao getInstance() {
        return PlaceDaoHolder.mInstance;
    }

    private static class PlaceDaoHolder {
        private static final PlaceDao mInstance = new PlaceDao();
    }

    private SharedPreferences getSharedPreferences() {
        return WeatherApplication.getContext().getSharedPreferences("practice_weather", Context.MODE_PRIVATE);
    }

    public void savePlace(Place place) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("place", new Gson().toJson(place));
        editor.apply();
    }

    public Place getSavedPlace() {
        if(getSharedPreferences().contains("place")) {
            String place = getSharedPreferences().getString("place", "");
            return new Gson().fromJson(place, Place.class);
        } else {
            return null;
        }
    }

}
