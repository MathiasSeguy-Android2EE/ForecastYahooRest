package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.adapter.WeatherRecyclerViewAdapter;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity.CityActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.main.MainCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.sys.SysCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather_data.WeatherDataCardView;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeatherActivity extends MotherActivity {
    private static final String TAG = "CurrentWeatherActivity";

    private TextView tvWind;
    private TextView tvSnow;
    private TextView tvRain;
    private TextView tvClouds;
    private RecyclerView rvWeatherList;
    private WeatherRecyclerViewAdapter recyclerViewAdapter;
    private WeatherDataCardView weatherDataCardView;
    private SysCardView sysCardView;
    private MainCardView mainCardView;

    private AppCompatImageView ivWind;
    private AppCompatImageView ivSnow;
    private AppCompatImageView ivRain;
    private AppCompatImageView ivClouds;

    private RecyclerView.LayoutManager gridLayoutManager;
    GridLayoutManager.SpanSizeLookup spanSizeLookup;

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
        model.getWeather().observe(this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(@Nullable List<Weather> weathers) {
                recyclerViewAdapter.updateWeatherData(weathers);
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
            //observe the Weathers

        }
    }

    private void initCardViews() {
        mainCardView = findViewById(R.id.cdv_main);
        tvWind = findViewById(R.id.txv_winds);
        tvSnow = findViewById(R.id.txv_snow);
        tvRain = findViewById(R.id.txv_rain);
        tvClouds = findViewById(R.id.txv_clouds);
        ivWind= findViewById(R.id.iv_winds);
        ivSnow=findViewById(R.id.iv_snow);
        ivRain=findViewById(R.id.iv_rain);
        ivClouds=findViewById(R.id.iv_clouds);
        rvWeatherList = findViewById(R.id.rv_weather_list);
        weatherDataCardView = findViewById(R.id.cdv_weather_data);
        sysCardView = findViewById(R.id.cdv_sys);
        //manage recyclerview
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvWeatherList.setHasFixedSize(false);
        // use a linear layout manage
        LinearLayoutManager limn=new LinearLayoutManager(this);
        limn.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvWeatherList.setLayoutManager(limn);
        // specify an adapter (see also next example)
        recyclerViewAdapter = new WeatherRecyclerViewAdapter(new ArrayList<Weather>(), this);
        rvWeatherList.setAdapter(recyclerViewAdapter);
    }
    private void initLifecycleOwners() {
        mainCardView.setLifecycleOwner(this, weatherData.get_id());
        weatherDataCardView.setLifecycleOwner(this, cityId);
        sysCardView.setLifecycleOwner(this, weatherData.get_id());
    }

    @SuppressLint("NewApi")
    private void updateView(WeatherData weatherData){
        //todo
        this.weatherData=weatherData;
        tvWind.setText(""+(weatherData.getWind()!=null?weatherData.getWind().getSpeed():" 0 "));
        tvClouds.setText(""+(weatherData.getClouds()!=null?weatherData.getClouds().getAll():" 0 "));
        tvRain.setText(""+(weatherData.getRain()!=null?weatherData.getRain().get3h():" 0 "));
        tvSnow.setText(""+(weatherData.getSnow()!=null?weatherData.getSnow().get3h():" 0 "));
        startAVDAnimation(ivClouds.getDrawable());
        startAVDAnimation(ivWind.getDrawable());
        startAVDAnimation(ivRain.getDrawable());
        startAVDAnimation(ivSnow.getDrawable());
    }


    @SuppressLint("NewApi")
    private void startAVDAnimation(Drawable drawable) {
        if (drawable instanceof AnimatedVectorDrawableCompat) {
            ((AnimatedVectorDrawableCompat) drawable).start();
        } else {
            ((AnimatedVectorDrawable) drawable).start();
        }
    }
}
