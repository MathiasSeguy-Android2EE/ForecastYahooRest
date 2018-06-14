package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.main.MainCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.sys.SysCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather_data.WeatherDataCardView;

public class CurrentWeatherActivity extends AppCompatActivity {

    private MainCardView mainCardView;
    private TextView tvWind;
    private TextView tvSnow;
    private TextView tvRain;
    private TextView tvClouds;
    private RecyclerView rvWeatherList;
    private WeatherDataCardView weatherDataCardView;
    private SysCardView sysCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        initCardViews();
        initLifecycleOwners();
    }

    private void initCardViews() {
        mainCardView = findViewById(R.id.main_card_view);
        tvWind = findViewById(R.id.txv_winds);
        tvSnow = findViewById(R.id.txv_snow);
        tvRain = findViewById(R.id.txv_rain);
        tvClouds = findViewById(R.id.txv_clouds);
        rvWeatherList = findViewById(R.id.rv_weather_list);
        weatherDataCardView = findViewById(R.id.cdv_weather_data);
        sysCardView = findViewById(R.id.cdv_sys);
    }

    private void initLifecycleOwners() {
        mainCardView.setLifeCycleOwner(this);
        weatherDataCardView.setLifeCycleOwner(this);
        sysCardView.setLifeCycleOwner(this);
    }
}
