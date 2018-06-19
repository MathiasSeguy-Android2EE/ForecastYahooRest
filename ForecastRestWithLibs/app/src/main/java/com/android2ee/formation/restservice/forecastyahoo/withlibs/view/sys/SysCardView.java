package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.sys;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.Sys;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class SysCardView extends MotherCardView {

    private static final String TIME_FORMAT = "HH:mm";

    /***********************************************************
     *  Attributes
     **********************************************************/

    private TextView tvCountry;
    private TextView tvSunrise;
    private TextView tvSunset;
    private AppCompatImageView ivSunset;
    private AppCompatImageView ivSunrise;

    SysViewModel model;

    /***********************************************************
     *  Constructors
     **********************************************************/
    public SysCardView(@NonNull Context context) {
        super(context);
    }

    public SysCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SysCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
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

    @SuppressLint("NewApi")
    private void onChangedLiveData(@Nullable Sys sys) {
        if (sys != null) {
            tvCountry.setText(sys.getCountry());
            Date sunrise = new java.util.Date(sys.getSunrise()*1000L);
            Date sunset = new java.util.Date(sys.getSunset()*1000L);
            tvSunrise.setText(new SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(sunrise));
            tvSunset.setText(new SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(sunset));
            if(ivSunset.getDrawable() instanceof AnimatedVectorDrawableCompat){
                ((AnimatedVectorDrawableCompat)ivSunset.getDrawable()).start();
            }else{
                ((AnimatedVectorDrawable)ivSunset.getDrawable()).start();
            }
            if(ivSunrise.getDrawable() instanceof AnimatedVectorDrawableCompat){
                ((AnimatedVectorDrawableCompat)ivSunrise.getDrawable()).start();
            }else{
                ((AnimatedVectorDrawable)ivSunrise.getDrawable()).start();
            }
        }
    }

    private void initViews() {
        tvCountry = findViewById(R.id.tv_country);
        tvSunrise = findViewById(R.id.tv_sunrise);
        tvSunset = findViewById(R.id.tv_sunset);
        ivSunset = findViewById(R.id.iv_sunset);
        ivSunrise = findViewById(R.id.iv_sunrise);

    }

    @Override
    protected void initObservers() {
        model = ViewModelProviders.of(activity, new SysModelFactory(contextId)).get(SysViewModel.class);

        model.getLiveData().observe(activity, new Observer<Sys>() {
            @Override
            public void onChanged(@Nullable Sys sys) {
                onChangedLiveData(sys);
            }
        });

    }

    @Override
    protected void removeObservers() {
        //TODO
    }

    private void updateWith(@NonNull Main main) {
        // TODO
    }

}
