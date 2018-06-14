package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherCardView;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class WeatherCardView extends MotherCardView {

    /***********************************************************
     *  Attributes
     **********************************************************/

    private ImageView ivIco;
    private TextView tvDescription;
    private TextView tvMain;

    /***********************************************************
     *  Constructors
     **********************************************************/
    public WeatherCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public WeatherCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeatherCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /***********************************************************
     *  Private methods
     **********************************************************/

    private void init() {
        initViews();
        initObservers();
    }

    private void initViews() {
        ivIco = findViewById(R.id.imv_ico);
        tvDescription = findViewById(R.id.txv_description);
        tvMain = findViewById(R.id.txv_main);
    }

    private void initObservers() {
        //TODO
    }

    private void updateWith(@NonNull Weather weather) {
        tvDescription.setText(weather.getDescription());
        tvMain.setText(weather.getMain());
        // TODO set ico
    }

}
