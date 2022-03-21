package com.android.niceweather.logic.dataModel.placeModel;


import com.google.gson.annotations.SerializedName;

public class Place {
    private String name;

    @SerializedName("formatted_address")
    private String address;
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
