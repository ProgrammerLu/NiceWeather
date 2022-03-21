package com.android.niceweather.ui.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.niceweather.R;
import com.android.niceweather.WeatherApplication;
import com.android.niceweather.logic.dataModel.weatherModel.DailyResponse;
import com.android.niceweather.logic.dataModel.weatherModel.RealtimeResponse;
import com.android.niceweather.logic.dataModel.weatherModel.Sky;
import com.android.niceweather.logic.dataModel.weatherModel.Weather;
import com.android.niceweather.viewmodel.WeatherViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    public WeatherViewModel weatherViewModel;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将背景图和状态栏融合
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_weather);

        weatherViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(WeatherViewModel.class);
        weatherViewModel.setPlaceName(getIntent().getStringExtra("place_name"));
        weatherViewModel.setLocationLng(getIntent().getStringExtra("location_lng"));
        weatherViewModel.setLocationLat(getIntent().getStringExtra("location_lat"));
        weatherViewModel.getWeatherLiveData().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if(weather != null) {
                    showWeatherInfo(weather);
                } else {
                    Toast.makeText(WeatherApplication.getContext(), "无法成功获取天气信息",
                            Toast.LENGTH_SHORT).show();
                }
            swipeRefresh.setRefreshing(false);
            }
        });
        //下拉刷新
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(com.google.android.material.R.color.design_default_color_primary);
        searchWeather();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchWeather();
            }
        });

        //切换城市
        Button navBtn = findViewById(R.id.navBtn);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        navBtn.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) { }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) { }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(drawerView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onDrawerStateChanged(int newState) { }
        });
    }

    public void searchWeather() {
        weatherViewModel.searchWeather(weatherViewModel.getLocationLng(), weatherViewModel.getLocationLat());
        swipeRefresh.setRefreshing(true);
    }

    private void showWeatherInfo(Weather weather) {
        RealtimeResponse.Realtime realtime = weather.getRealtime();
        DailyResponse.Daily daily = weather.getDaily();

        //填充now.xml中的数据
        TextView placeNameText = findViewById(R.id.placeName);
        placeNameText.setText(weatherViewModel.getPlaceName());
        TextView currentTempText = findViewById(R.id.currentTemp);
        currentTempText.setText(realtime.getTemperature().intValue() + "℃");
        TextView currentSkyText = findViewById(R.id.currentSky);
        currentSkyText.setText(Sky.getSky(realtime.getSkycon()).getInfo());
        TextView currentAQIText  =findViewById(R.id.currentAQI);
        currentAQIText.setText("空气指数" + realtime.getAirQuality().getAqi().getChn().intValue());
        findViewById(R.id.nowLayout).setBackgroundResource(Sky.getSky(realtime.getSkycon()).getBg());

        //填充forecast.xml布局中的数据
        LinearLayout forecastLayout = findViewById(R.id.forecastLayout);
        forecastLayout.removeAllViews();
        int size = daily.getSkycon().size();
        for(int i = 0; i < size; i++) {
            DailyResponse.Skycon skycon = daily.getSkycon().get(i);
            DailyResponse.Temperature temperature = daily.getTemperature().get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateInfo = view.findViewById(R.id.dateInfo);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateInfo.setText(simpleDateFormat.format(skycon.getDate()));
            ImageView skyIcon = view.findViewById(R.id.skyIcon);
            Sky sky = Sky.getSky(skycon.getValue());
            skyIcon.setImageResource(sky.getIcon());
            TextView skyInfo = view.findViewById(R.id.skyInfo);
            skyInfo.setText(sky.getInfo());
            TextView temperatureInfo = view.findViewById(R.id.temperatureInfo);
            temperatureInfo.setText(temperature.getMin().intValue() + " ~ " + temperature.getMax().intValue() + "℃");
            forecastLayout.addView(view);
        }

        //填充life_index.xml布局中的数据
        DailyResponse.LifeIndex lifeIndex = daily.getLifeIndex();
        TextView coldRiskText = findViewById(R.id.coldRiskText);
        coldRiskText.setText(lifeIndex.getColdRisk().get(0).getDesc());
        TextView dressingText = findViewById(R.id.dressingText);
        dressingText.setText(lifeIndex.getDressing().get(0).getDesc());
        TextView ultravioletText = findViewById(R.id.ultravioletText);
        ultravioletText.setText(lifeIndex.getUltraviolet().get(0).getDesc());
        TextView carWashingText = findViewById(R.id.carWashingText);
        carWashingText.setText(lifeIndex.getCarWashing().get(0).getDesc());

        findViewById(R.id.weatherLayout).setVisibility(View.VISIBLE);
    }
}