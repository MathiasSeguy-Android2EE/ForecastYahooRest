package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.NavigationActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.adapter.WeatherRecyclerViewAdapter;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.main.MainCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.sys.SysCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.weather_data.WeatherDataBeaconCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.dialog.DeleteAlert;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeatherActivity extends NavigationActivity implements DeleteAlert.DeletionCallBack, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "CurrentWeatherActivity";
    public static final int NULL_VALUE = -1;

    /***********************************************************
     *  UI Attributes
     **********************************************************/
    /**     * Txv Wind     */
    private TextView tvWind;
    /**     * CompatImageView containing AVD for Wind     */
    private AppCompatImageView ivWind;
    /**     * Txv Snow     */
    private TextView tvSnow;
    /**     * CompatImageView containing AVD for Snow     */
    private AppCompatImageView ivSnow;
    /**     * Txv Rain     */
    private TextView tvRain;
    /**     * CompatImageView containing AVD for Rain     */
    private AppCompatImageView ivRain;
    /**     * Txv Clouds     */
    private TextView tvClouds;
    /**     * CompatImageView containing AVD for Clouds     */
    private AppCompatImageView ivClouds;
    /**     *Refresh data     */
    private SwipeRefreshLayout swipeView;
    /***********************************************************
     *  Attributes Recycler View
     **********************************************************/
    /** Recycler View displaying the list of Weathers
     * Loaded by another LiveData     */
    private RecyclerView rvWeatherList;
    /** Adapter of the  RecyclerView rvWeatherList
     */
    private WeatherRecyclerViewAdapter recyclerViewAdapter;
    /***********************************************************
     *  Attributes Automnomous cardView (they have their own model)
     **********************************************************/
    /**
     * The CardView to use when displaying the beacon data of this WeatherData
     */
    private WeatherDataBeaconCardView weatherDataBeaconCardView;
    /**
     * The CardView to use when displaying the sys information (country/sunset/sunrise)
     */
    private SysCardView sysCardView;
    /**
     * The CardView to use when displaying the main inforation of the weather (min/max/temp/humidity/pressure)
     */
    private MainCardView mainCardView;

    /***********************************************************
     *  Attributes : Model and data
     **********************************************************/
    /**     * Model      */
    private CurrentWeatherActivityModel model;
    /**     * Pointer to the LiveData observed     */
    private WeatherData weatherData;
    /**     * The name of the city on Stage     */
    private String cityName;
    /**
     * The Alertdialog to confirm deletion
     */
    private DeleteAlert deleteDialog;
    /**
     * MenuItem's animated vector drawable icon
     */
    AnimatedVectorDrawableCompat atomicBombAnim;
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load your model
        model=ViewModelProviders.of(this).get(CurrentWeatherActivityModel.class);
        //set and init your views
        setContentView(R.layout.activity_current_entrypoint);
//        setContentView(R.layout.activity_current_content);
        initCardViews();
        super.initView();
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
        //start observing the weatherData entity
        model.getLiveData().observe(this, new Observer<WeatherData>() {
            @Override
            public void onChanged(@Nullable WeatherData weatherData) {
                onChangedLiveData(weatherData);
            }
        });
        //start observing the list of associated weathers entity
        model.getWeather().observe(this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(@Nullable List<Weather> weathers) {
                recyclerViewAdapter.updateWeatherData(weathers);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //invalidate the DialogFragment to avoid stupid memory leak
        if (deleteDialog != null) {
            if (deleteDialog.isVisible()) {
                deleteDialog.dismiss();
            }
            deleteDialog = null;
        }
        if(atomicBombAnim.isRunning()){
            atomicBombAnim.stop();
        }
    }

    /***********************************************************
     *  Managing UI initialization
     **********************************************************/
    /**
     * UI initialisation
     */
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
        weatherDataBeaconCardView = findViewById(R.id.cdv_weather_data);
        sysCardView = findViewById(R.id.cdv_sys);
        //Refresh Layout
        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_view);
        swipeView.setOnRefreshListener(this);
        swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeView.setDistanceToTriggerSync(20);// in dips
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used

        //manage recyclerview
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvWeatherList.setHasFixedSize(false);
        // use a linear layout manage
        LinearLayoutManager limn=new LinearLayoutManager(this);
        limn.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvWeatherList.setLayoutManager(limn);
        // specify an adapter (see also next example)
        recyclerViewAdapter = new WeatherRecyclerViewAdapter(new ArrayList<Weather>(), this,model);
        rvWeatherList.setAdapter(recyclerViewAdapter);
    }

    /**
     * When you have the Id of your weatherData displayed, init your others card models and views
     */
    private void updateLifecycleOwners() {
        MyLog.e(TAG,"Init observer with "+weatherData.get_id());
        mainCardView.setLifecycleOwner(this, weatherData.get_id());
        weatherDataBeaconCardView.setLifecycleOwner(this, weatherData.getCityId());
        sysCardView.setLifecycleOwner(this, weatherData.get_id());
    }

    /***********************************************************
     *  Managing Refresh
     **********************************************************/
    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        //onRefresh
        swipeView.setRefreshing(true);
        //load data
        model.updateDataOfCitiesOnStage();

    }
    @Override
    protected int getBootomNavAssociatedItemId() {
        return R.id.menu_now;
    }
    /***********************************************************
     *  Managing UI updates (because data changed)
     **********************************************************/
    /**
     * the liveData WeatherData has changed
     * @param weatherData New entity to display
     */
    private void onChangedLiveData(@Nullable WeatherData weatherData) {
        MyLog.e(TAG,"WeatherLiveData changed with "+weatherData);
        if(weatherData==null){
            //clear the UI
        }else {
            this.weatherData = weatherData;
            updateLifecycleOwners();
            updateView(weatherData);
        }
    }

    /**
     * Update the view
     * @param weatherData
     */
    private void updateView(WeatherData weatherData){
        //finish refreshing
        swipeView.setRefreshing(false);
        //update the UI
        tvWind.setText(""+(weatherData.getWind()!=null?weatherData.getWind().getSpeed():" 0 "));
        tvClouds.setText(""+(weatherData.getClouds()!=null?weatherData.getClouds().getAll():" 0 "));
        tvRain.setText(""+(weatherData.getRain()!=null?weatherData.getRain().get3h():" 0 "));
        tvSnow.setText(""+(weatherData.getSnow()!=null?weatherData.getSnow().get3h():" 0 "));
        startAVDAnimation(ivClouds.getDrawable());
        startAVDAnimation(ivWind.getDrawable());
        startAVDAnimation(ivRain.getDrawable());
        startAVDAnimation(ivSnow.getDrawable());
    }


    private void updateCity(List<City> cities){
        if(cities.size()!=0){
            cityName=cities.get(0).getName();
            super.getSupportActionBar().setSubtitle(cityName);
        }else{
            super.getSupportActionBar().setSubtitle("No onStage city found");
        }
    }
    /**
     * Start AnimationVectorDrawables
     * @param drawable Start the animation of this VectorDrawable
     */
    @SuppressLint("NewApi")
    private void startAVDAnimation(Drawable drawable) {
        if (drawable instanceof AnimatedVectorDrawableCompat) {
            ((AnimatedVectorDrawableCompat) drawable).start();
        } else {
            ((AnimatedVectorDrawable) drawable).start();
        }
    }
/******************************************************************************************/
    /** Managing Menu **************************************************************************/
    /******************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_current_menu, menu);
        //inflate your AnimatedVectorDrawable
        atomicBombAnim =AnimatedVectorDrawableCompat.create(this,R.drawable.ic_nuclear_bomb_anim);
        //deinfed it for your MenuItem
        menu.findItem(R.id.action_delete).setIcon(atomicBombAnim);
        //start the animation
        atomicBombAnim.start();
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
            case R.id.action_delete:
                atomicBombAnim.start();
                //Delete the current city
                onDeleteCurrentCity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /******************************************************************************************/
    /** Managing city deletion **************************************************************************/
    /******************************************************************************************/
    /**
     * The method called when the delete action is launched by the user
     * either through the menu or the ime of the editText
     */
    private void onDeleteCurrentCity(){
        FragmentManager fm = getSupportFragmentManager();
        deleteDialog=(DeleteAlert)fm.findFragmentByTag("deleteDialog");
        if(deleteDialog==null){
            deleteDialog=new DeleteAlert();
            deleteDialog.setDeletionCallBack(this);
        }
        deleteDialog.show(getSupportFragmentManager(), "deleteDialog");
    }

    /**
     * @param cityId
     */
    public void selectCity(long cityId) {
        MyLog.e(TAG,"New cityId on stage:"+cityId);
        MyApplication.instance.getServiceManager().getCityService().onStage(cityId);
        //launch the main activity
        Intent launchMainActivity = new Intent(this, CurrentWeatherActivity.class);
        startActivity(launchMainActivity);
        //and die
        finish();
    }
    /***********************************************************
     *  Managing DeletionCallback methods
     **********************************************************/
    @Override
    public String getCurrentCityName() {
        return cityName;
    }
    /**
     * Delete the current city and then do what is needed in term of navigation
     */
    @Override
    public void deleteCurrentCity(){
        model.deleteCity(weatherData.getCityId());
    }


}
