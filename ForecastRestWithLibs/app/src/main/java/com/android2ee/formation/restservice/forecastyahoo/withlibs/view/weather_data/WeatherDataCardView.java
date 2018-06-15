package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather_data;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
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
        initViews();
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
        //TODO
    }

    @Override
    protected void removeObservers() {
        //TODO
    }

    private void updateWith(@NonNull WeatherData weatherData) {
        // TODO
    }
}
