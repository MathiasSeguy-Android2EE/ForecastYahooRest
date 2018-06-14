package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.main.MainCardView;

public class CurrentWeatherActivity extends AppCompatActivity {

    private MainCardView mainCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        initCardViews();
        initLifecycleOwners();
    }

    private void initCardViews() {
        mainCardView = findViewById(R.id.main_card_view);
    }

    private void initLifecycleOwners() {
        mainCardView.setLifeCycleOwner(this);
    }
}
