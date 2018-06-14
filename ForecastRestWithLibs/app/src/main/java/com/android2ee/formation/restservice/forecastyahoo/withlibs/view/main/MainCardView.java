package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.main;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.main.MainViewModel;

import java.util.List;

public class MainCardView extends MotherCardView {

    /***********************************************************
     *  Attributes
     **********************************************************/

    private TextView tvTemperature;
    private TextView tvTemperatureMin;
    private TextView tvTemperatureMax;
    private TextView tvHumidity;
    private TextView tvPressure;

    /***********************************************************
     *  Constructors
     **********************************************************/
    public MainCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public MainCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setLifecycleOwner(AppCompatActivity activity) {
        super.setLifecycleOwner(activity);
        // Init observer only after having a lifeCycleOwner
        initObservers();
    }

    /***********************************************************
     *  ModelView management
     **********************************************************/
    @Override
    public Class<MainViewModel> getCardViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public String getCardViewModelKey() {
        return MainCardView.class.getName();
    }

    /***********************************************************
     *  Private methods
     **********************************************************/

    private void init() {
        initViews();
    }

    private void initViews() {
        tvTemperature = findViewById(R.id.tv_temperature);
        tvTemperatureMin = findViewById(R.id.tv_min_temperature);
        tvTemperatureMax = findViewById(R.id.tv_max_temperature);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvPressure = findViewById(R.id.tv_pressure);
    }

    private void initObservers() {
        MainViewModel viewModel = (MainViewModel) getViewModel();
        viewModel.getAllMainLiveData().observe(activity,new Observer<List<Main>>() {
            @Override
            public void onChanged(@Nullable List<Main> mains) {
                if (mains != null) {
                    updateWith(mains.get(0));
                }
            }
        });
    }

    private void updateWith(@NonNull Main main) {
        tvTemperature.setText(getContext().getString(R.string.main_temperature, main.getTemp()));
        tvTemperatureMin.setText(getContext().getString(R.string.main_temperature_min, main.getTempMin()));
        tvTemperatureMax.setText(getContext().getString(R.string.main_temperature_max, main.getTempMax()));
        tvHumidity.setText(getContext().getString(R.string.main_humidity, main.getHumidity()));
        tvPressure.setText(getContext().getString(R.string.main_pressure, main.getPressure()));
    }

}
