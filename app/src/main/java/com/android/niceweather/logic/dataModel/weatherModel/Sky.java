package com.android.niceweather.logic.dataModel.weatherModel;

import com.android.niceweather.R;

import java.util.HashMap;

public class Sky {
    private String info;
    private int icon;
    private int bg;

    public Sky(String info, int icon, int bg) {
        this.info = info;
        this.icon = icon;
        this.bg = bg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    private static HashMap<String, Sky> sky = new HashMap<>();
    static {
        sky.put("CLEAR_DAY", new Sky("晴", R.drawable.ic_clear_day, R.drawable.bg_clear_day));
        sky.put("CLEAR_NIGHT", new Sky("晴", R.drawable.ic_clear_night, R.drawable.bg_clear_night));
        sky.put("PARTLY_CLEAR_DAY", new Sky("多云", R.drawable.ic_partly_cloud_day, R.drawable.bg_partly_cloudy_day));
        sky.put("PARTLY_CLEAR_NIGHT", new Sky("多云", R.drawable.ic_partly_cloud_night, R.drawable.bg_partly_cloudy_night));
        sky.put("CLOUDY", new Sky("阴", R.drawable.ic_cloudy, R.drawable.bg_cloudy));
        sky.put("WIND", new Sky("大风", R.drawable.ic_cloudy, R.drawable.bg_wind));
        sky.put("LIGHT_RAIN", new Sky("小雨", R.drawable.ic_light_rain, R.drawable.bg_rain));
        sky.put("MODERATE_RAIN", new Sky("中雨", R.drawable.ic_moderate_rain, R.drawable.bg_rain));
        sky.put("HEAVY_RAIN", new Sky("大雨", R.drawable.ic_heavy_rain, R.drawable.bg_rain));
        sky.put("STORM_RAIN", new Sky("暴雨", R.drawable.ic_storm_rain, R.drawable.bg_rain));
        sky.put("THUNDER_SHOWER", new Sky("雷阵雨", R.drawable.ic_thunder_shower, R.drawable.bg_rain));
        sky.put("SLEET", new Sky("雨夹雪", R.drawable.ic_sleet, R.drawable.bg_rain));
        sky.put("LIGHT_SHOW", new Sky("小雪", R.drawable.ic_light_snow, R.drawable.bg_snow));
        sky.put("MODERATE_SHOW", new Sky("中雪", R.drawable.ic_moderate_snow, R.drawable.bg_snow));
        sky.put("HEAVY_SHOW", new Sky("大雪", R.drawable.ic_heavy_snow, R.drawable.bg_snow));
        sky.put("STORM_SHOW", new Sky("暴雪", R.drawable.ic_heavy_snow, R.drawable.bg_snow));
        sky.put("HAIL", new Sky("冰雹", R.drawable.ic_hail, R.drawable.bg_snow));
        sky.put("LIGHT_HAZE", new Sky("轻度雾霾", R.drawable.ic_light_haze, R.drawable.bg_fog));
        sky.put("MODERATE_HAZE", new Sky("中度雾霾", R.drawable.ic_moderate_haze, R.drawable.bg_fog));
        sky.put("HEAVY_HAZE", new Sky("重度雾霾", R.drawable.ic_heavy_haze, R.drawable.bg_fog));
        sky.put("FOG", new Sky("雾", R.drawable.ic_fog, R.drawable.bg_fog));
        sky.put("DUST", new Sky("浮尘", R.drawable.ic_fog, R.drawable.bg_fog));
    }

    public static Sky getSky(String skycon) {
        if(sky.containsKey(skycon))
            return sky.get(skycon);
        else return sky.get("CLEAR_DAY");
    }
}
