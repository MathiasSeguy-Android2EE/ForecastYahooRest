package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather_data;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Marion Aubard on 14/06/2018.
 * This card is dedicated to display the data from the WeatherData object that represents
 * from where the data are provided (station/beacon...), when, where and the visibility associated
 * (why the visibility here, no idea, it's weird, yes we agree, but didn't find a best place)
 * So it obesrve a Weather Data and displays its:
 * Name/visibility/coord/base(station/beacon)
 */
public class WeatherDataBeaconCardView extends MotherCardView {

    /***********************************************************
     *  Attributes
     **********************************************************/

    private TextView tvName;
    private TextView tvVisibility;
    private TextView tvCoord;
    private TextView tvBase;
    private TextView tvDt;

    WeatherDataBeaconCardViewModel model;
    private WeatherData weatherData;

    private static final String TIME_PATTERN = "EEE HH:mm";

    /***********************************************************
     *  Constructors
     **********************************************************/
    public WeatherDataBeaconCardView(@NonNull Context context) {
        super(context);
    }

    public WeatherDataBeaconCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherDataBeaconCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /***********************************************************
     *  ViewModel management
     *********************************************************/

    @Override
    public Class getCardViewModelClass() {
        return null;
    }

    @Override
    public String getCardViewModelKey() {
        return null;
    }

    @NonNull
    @Override
    protected ViewModelProvider.Factory getCardViewFactory() {
        return null;
    }

    /***********************************************************
     *  Private methods
     **********************************************************/
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void onChangedLiveData(@Nullable WeatherData weatherData) {
        if(weatherData == null){
            //ben we do nothing, stupid liveData behavior
        }else {
            this.weatherData = weatherData;
            updateWith(weatherData);
        }
    }

    private void initViews() {
        tvName = findViewById(R.id.txv_name);
        tvVisibility = findViewById(R.id.txv_visibility);
        tvCoord = findViewById(R.id.txv_coord);
        tvBase = findViewById(R.id.txv_base);
        tvDt = findViewById(R.id.txv_dt);
    }

    @Override
    protected void initObservers() {
        model = ViewModelProviders.of(activity, new WeatherDataBeaconModelFactory(contextId)).get(WeatherDataBeaconCardViewModel.class);
        //start observing
        model.getLiveData().observe(activity, new Observer<WeatherData>() {
            @Override
            public void onChanged(@Nullable WeatherData weatherData) {
                onChangedLiveData(weatherData);
            }
        });
    }

    @Override
    protected void removeObservers() {
        //TODO
    }

    private void updateWith(@NonNull WeatherData weatherData) {
        tvName.setText(weatherData.getName());
        tvVisibility.setText(getResources().getString(R.string.visibility, weatherData.getVisibility()));
        tvCoord.setText(weatherData.getCoord().toString());
        tvBase.setText(weatherData.getBase());
        Date dtMilli = new java.util.Date(weatherData.getTimeStampUTC()*1000L);
        SimpleDateFormat df = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
        tvDt.setText(df.format(dtMilli));
    }
}
