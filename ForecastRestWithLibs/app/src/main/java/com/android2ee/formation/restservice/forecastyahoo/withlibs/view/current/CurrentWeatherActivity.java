package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity.CityActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.main.MainCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.sys.SysCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather_data.WeatherDataCardView;

public class CurrentWeatherActivity extends AppCompatActivity {
    private static final String TAG = "CurrentWeatherActivity";

    private MainCardView mainCardView;
    private TextView tvWind;
    private TextView tvSnow;
    private TextView tvRain;
    private TextView tvClouds;
    private RecyclerView rvWeatherList;
    private WeatherDataCardView weatherDataCardView;
    private SysCardView sysCardView;

    CurrentWeatherActivityModel model;

    private WeatherData weatherData;
    private long cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityId=getIntent().getLongExtra(CityActivity.CITY_ID,-1);
        MyLog.e(TAG,"found the cityId = "+cityId);
        model=ViewModelProviders.of(this, new CurrentWeatherModelFactory(cityId)).get(CurrentWeatherActivityModel.class);
        setContentView(R.layout.activity_current_weather);
        initCardViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //start observing
        model.getLiveData().observe(this, new Observer<WeatherData>() {
            @Override
            public void onChanged(@Nullable WeatherData weatherData) {
                onChangedLiveData(weatherData);
            }
        });
    }

    private void onChangedLiveData(@Nullable WeatherData weatherData) {
        if(weatherData==null){
            //ben we do nothing, stupid liveData behavior
        }else {
            this.weatherData = weatherData;
            initLifecycleOwners();
            updateView(weatherData);
        }
    }

    private void initCardViews() {
        mainCardView = findViewById(R.id.cdv_main);
        tvWind = findViewById(R.id.txv_winds);
        tvSnow = findViewById(R.id.txv_snow);
        tvRain = findViewById(R.id.txv_rain);
        tvClouds = findViewById(R.id.txv_clouds);
        rvWeatherList = findViewById(R.id.rv_weather_list);
        weatherDataCardView = findViewById(R.id.cdv_weather_data);
        sysCardView = findViewById(R.id.cdv_sys);
    }

    private void initLifecycleOwners() {
        mainCardView.setLifecycleOwner(this, weatherData.get_id());
        weatherDataCardView.setLifecycleOwner(this, cityId);
        sysCardView.setLifecycleOwner(this, weatherData.get_id());
    }

    private void updateView(WeatherData weatherData){
        //todo
        this.weatherData=weatherData;
    }
}
