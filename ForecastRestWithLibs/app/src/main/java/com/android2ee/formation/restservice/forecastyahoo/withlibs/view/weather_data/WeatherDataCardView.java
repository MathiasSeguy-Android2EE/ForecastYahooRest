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
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherCardView;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class WeatherDataCardView extends MotherCardView {

    /***********************************************************
     *  Attributes
     **********************************************************/

    private TextView tvName;
    private TextView tvVisibility;
    private TextView tvCoord;
    private TextView tvBase;
    private TextView tvDt;

    WeatherDataCardViewModel model;
    private WeatherData weatherData;

    /***********************************************************
     *  Constructors
     **********************************************************/
    public WeatherDataCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public WeatherDataCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeatherDataCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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

    private void init() {
//        initViews();
    }

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
        model = ViewModelProviders.of(activity, new WeatherDataModelFactory(contextId)).get(WeatherDataCardViewModel.class);

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
//        tvVisibility.setText(weatherData.get); //TODO
        tvCoord.setText(weatherData.getCoord().toString());
        tvBase.setText(weatherData.getBase());
        tvDt.setText(Long.toString(weatherData.getTimeStampUTC()));

    }
}
