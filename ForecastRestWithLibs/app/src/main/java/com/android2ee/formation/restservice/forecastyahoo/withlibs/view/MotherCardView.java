package com.android2ee.formation.restservice.forecastyahoo.withlibs.view;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;

public class MotherCardView extends CardView {

    private LifecycleOwner lifecycleOwner;
    private WeatherData weatherData;

    /***********************************************************
     *  Constructors
     **********************************************************/
    public MotherCardView(@NonNull Context context) {
        super(context);
    }

    public MotherCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MotherCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(LifecycleOwner lifeCycleOwner, WeatherData weatherData) {
        this.lifecycleOwner = lifeCycleOwner;
        this.weatherData = weatherData;
    }
}
