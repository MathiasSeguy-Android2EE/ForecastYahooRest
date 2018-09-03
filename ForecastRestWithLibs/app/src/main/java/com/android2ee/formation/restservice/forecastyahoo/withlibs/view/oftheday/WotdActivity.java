package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.oftheday;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.calculated.WeatherOfTheDay;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.DayHashCreator;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.NavigationActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.arrayadapter.LinearLayoutManagerFixed;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.oftheday.adapter.WotdAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 22/06/2018.
 */
public class WotdActivity extends NavigationActivity {
    private static final String TAG = "WotdActivity";
    /***********************************************************
    *  Attributes
    **********************************************************/
    private RecyclerView rcvWeatherOfTheDay;
    private WotdAdapter adapter;
    private WotdActivityModel model;
    /**     * The name of the city on Stage     */
    private String cityName;
    /**
     * The hash of the day today
     */
    private int todayHash;

    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model= ViewModelProviders.of(this).get(WotdActivityModel.class);
        setContentView(R.layout.activity_wotd_entrypoint);
        rcvWeatherOfTheDay =findViewById(R.id.rcv_weather_of_the_day);
        // use a linear layout manage
        LinearLayoutManager limn=new LinearLayoutManagerFixed(this);
        limn.setOrientation(LinearLayoutManager.VERTICAL);
        rcvWeatherOfTheDay.setLayoutManager(limn);
        // specify an adapter (see also next example)
        adapter = new WotdAdapter(this,model);
        rcvWeatherOfTheDay.setAdapter(adapter);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //find today Hash
        Calendar cal=new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        todayHash= DayHashCreator.getTempKeyFromDay(cal);
        //obsreve
        model.getOnStageCities().observe(this, new Observer<List<City>>() {
                    @Override
                    public void onChanged(@Nullable List<City> cities) {
                        updateCity(cities);
                    }
                }
        );
        model.getData().observe(this, new Observer<List<WeatherOfTheDay>>() {
                    @Override
                    public void onChanged(@Nullable List<WeatherOfTheDay> weatherOfTheDays) {
                        updateArrayAdapter(weatherOfTheDays);
                    }
                }
        );
    }
    /***********************************************************
    *  Business Methods
    **********************************************************/
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
        Intent launchMainActivity = new Intent(this, WotdActivity.class);
        startActivity(launchMainActivity);
        //and die
        finish();
    }


    /**
     * Is called by this when data are updated from the liveData
     * @param weatherForecatsItemWithMainAndWeathers
     */
    private void updateArrayAdapter(List<WeatherOfTheDay> weatherForecatsItemWithMainAndWeathers){
        //you just need to updateEntity
        adapter.updateList(weatherForecatsItemWithMainAndWeathers);
        //select the item of the day
        //Display the right item in the middle of the screen
        scrollToToday(weatherForecatsItemWithMainAndWeathers);
    }

    /**
     * Scrool to Today
     * @param weatherForecatsItemWithMainAndWeathers
     */
    private void scrollToToday(List<WeatherOfTheDay> weatherForecatsItemWithMainAndWeathers) {
        int itemOfTodayPosition=0;
        for (int i = 0; i < weatherForecatsItemWithMainAndWeathers.size(); i++) {
            if(todayHash==weatherForecatsItemWithMainAndWeathers.get(i).getDayHash()){
                itemOfTodayPosition=i;
            }
        }
        if(itemOfTodayPosition!=0){
            rcvWeatherOfTheDay.scrollToPosition(itemOfTodayPosition);
        }
    }

    @Override
    protected int getBootomNavAssociatedItemId() {
        return R.id.menu_wotf;
    }
}
