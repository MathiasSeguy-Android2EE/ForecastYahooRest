package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather_data.WeatherDataBeaconCardView;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeatherActivity extends MotherActivity {
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
    CurrentWeatherActivityModel model;
    /**     * Pointer to the LiveData observed     */
    private WeatherData weatherData;
    /**     * Context of reference of the displayed object
     * This is the cityId for the WeatherData to displays
     */
    private long cityId;
    /**
     * The Alertdialog to confirm deletion
     */
    private DeleteAlert deleteDialog;
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get you data context
        cityId=getIntent().getLongExtra(CityActivity.CITY_ID, NULL_VALUE);
        MyLog.e(TAG,"found the cityId = "+cityId);
        //load your model
        model=ViewModelProviders.of(this, new CurrentWeatherModelFactory(cityId)).get(CurrentWeatherActivityModel.class);
        //set and init your views
        setContentView(R.layout.activity_current_weather);
        initCardViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(cityId==NULL_VALUE){
            launchCityActivity(true);
            return;
        }
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

    /**
     * When you have the Id of your weatherData displayed, init your others card models and views
     */
    private void initLifecycleOwners() {
        mainCardView.setLifecycleOwner(this, weatherData.get_id());
        weatherDataBeaconCardView.setLifecycleOwner(this, cityId);
        sysCardView.setLifecycleOwner(this, weatherData.get_id());
    }

    /***********************************************************
     *  Managing UI updates (because data changed)
     **********************************************************/
    /**
     * the liveData WeatherData has changed
     * @param weatherData New entity to display
     */
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

    /**
     * Update the view
     * @param weatherData
     */
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
        }
        deleteDialog.show(getSupportFragmentManager(), "deleteDialog");
    }
    /**
     * Delete the current city and then do what is needed in term of navigation
     */
    private void deleteCurrentCity(){
        model.deleteCity(cityId);
    }
    /******************************************************************************************/
    /** Managing navigation **************************************************************************/
    /******************************************************************************************/

    /**
     * When there is no city in the Database, you have to launch the search activity from here
     */
    public void launchCityActivity(boolean finish){
        // then launch the CityActivity to select a city
        Intent launchCityActivity = new Intent(this, CityActivity.class);
        startActivity(launchCityActivity);
        if(finish){
            finish();
        }
    }
    /******************************************************************************************/
    /** Managing AlertDialog **************************************************************************/
    /******************************************************************************************/

    /**
     * The AlertDialog that displays the message are you sure you want to delete
     */
    @SuppressLint("ValidFragment")
    public class DeleteAlert extends DialogFragment {

        public DeleteAlert(){
            //empty constructor is mandatory
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("");
            builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteCurrentCity();
                }
            });
            builder.setNegativeButton(R.string.btn_no, null);
            return builder.create();
        }

        /**
         * Called when the fragment is visible to the user and actively running.
         * This is generally
         * tied to {@link android.app.Activity#onResume() Activity.onResume} of the containing
         * Activity's lifecycle.
         */
        @Override
        public void onResume() {
            super.onResume();
            //TODO
//            City cityToDelete=presenter.getCities().get(viewPager.getCurrentItem());
//            ((AlertDialog)getDialog()).setMessage(getString(R.string.alertdialog_message,cityToDelete.getName()));
        }
    }
}
