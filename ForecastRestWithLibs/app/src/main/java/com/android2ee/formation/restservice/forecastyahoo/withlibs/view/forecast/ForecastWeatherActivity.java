package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.forecast.WeatherForecatsItemWithMainAndWeathers;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.NavigationActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.arrayadapter.ForecastItemAdapter;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.arrayadapter.LinearLayoutManagerFixed;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 22/06/2018.
 */
public class ForecastWeatherActivity extends NavigationActivity {
    private static final String TAG = "ForecastWeatherActivity";
    private RecyclerView rcv_forecast;
    private ForecastItemAdapter adapter;
    private ForercastWeatherActivityModel model;
    /**     * The name of the city on Stage     */
    private String cityName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model= ViewModelProviders.of(this).get(ForercastWeatherActivityModel.class);
        setContentView(R.layout.activity_forecast_entrypoint);
        rcv_forecast=findViewById(R.id.rcv_forecast);
        // use a linear layout manage
        LinearLayoutManager limn=new LinearLayoutManagerFixed(this);
        limn.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_forecast.setLayoutManager(limn);
        // specify an adapter (see also next example)
        adapter = new ForecastItemAdapter(this,model);
        rcv_forecast.setAdapter(adapter);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.getOnStageCities().observe(this, new Observer<List<City>>() {
                    @Override
                    public void onChanged(@Nullable List<City> cities) {
                        updateCity(cities);
                    }
                }
        );
        model.getData().observe(this, new Observer<List<WeatherForecatsItemWithMainAndWeathers>>() {
                    @Override
                    public void onChanged(@Nullable List<WeatherForecatsItemWithMainAndWeathers> weatherForecatsItemWithMainAndWeathers) {
                        updateArrayAdapter(weatherForecatsItemWithMainAndWeathers);
                    }
                }
        );
    }

    @Override
    protected int getBootomNavAssociatedItemId() {
        return R.id.menu_3h;
    }

    private void updateCity(List<City> cities){
        MyLog.e(TAG,"update city with"+(cities!=null?cities.size():"0")+" elements");
        if(cities.size()!=0){
            cityName=cities.get(0).getName();
            super.getSupportActionBar().setSubtitle(cityName);
        }else{
            super.getSupportActionBar().setSubtitle("No onStage city found");
        }
    }

    /**
     * @param cityId
     */
    public void selectCity(long cityId) {
        MyLog.e(TAG,"New cityId on stage:"+cityId);
        MyApplication.instance.getServiceManager().getCityService().onStage(cityId);
        //launch the main activity
        Intent launchMainActivity = new Intent(this, ForecastWeatherActivity.class);
        startActivity(launchMainActivity);
        //and die
        finish();
    }



    private void updateArrayAdapter(List<WeatherForecatsItemWithMainAndWeathers> weatherForecatsItemWithMainAndWeathers){
//you just need to updateEntity
        adapter.updateList(weatherForecatsItemWithMainAndWeathers);
    }

}
