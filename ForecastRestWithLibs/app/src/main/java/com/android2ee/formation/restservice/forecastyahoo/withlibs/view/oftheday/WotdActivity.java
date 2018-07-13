package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.oftheday;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.calculated.WeatherOfTheDay;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.CityNavDrawerActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.ForecastWeatherActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.arrayadapter.LinearLayoutManagerFixed;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.oftheday.adapter.WotdAdapter;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 22/06/2018.
 */
public class WotdActivity extends CityNavDrawerActivity{
    private static final String TAG = "WotdActivity";
    private RecyclerView rcvWeatherOfTheDay;
    private WotdAdapter adapter;
    private WotdActivityModel model;
    /**     * The name of the city on Stage     */
    private String cityName;
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



    private void updateArrayAdapter(List<WeatherOfTheDay> weatherForecatsItemWithMainAndWeathers){
//you just need to updateEntity
        adapter.updateList(weatherForecatsItemWithMainAndWeathers);
    }

    /***********************************************************
     *  Managing Menu
     **********************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Launch the search cityactivity
                launchCityActivity(false);
                return true;
            case R.id.action_delete:
                //Delete the current city
                onDeleteCurrentCity();
                return true;
            case R.id.action_switch:
                launchForecast();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * The method called when the delete action is launched by the user
     * either through the menu or the ime of the editText
     */
    private void onDeleteCurrentCity(){
        //TODO
//        FragmentManager fm = getSupportFragmentManager();
//        deleteDialog=(DeleteAlert)fm.findFragmentByTag("deleteDialog");
//        if(deleteDialog==null){
//            deleteDialog=new DeleteAlert();
//            deleteDialog.setDeletionCallBack(this);
//        }
//        deleteDialog.show(getSupportFragmentManager(), "deleteDialog");
    }

    /**
     *
     */
    private void launchForecast() {
        MyLog.e(TAG,"launchForecast");

        Intent launchMainActivity = new Intent(this, ForecastWeatherActivity.class);
        startActivity(launchMainActivity);
        //and die
        finish();
    }
}
