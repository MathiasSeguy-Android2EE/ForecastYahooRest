package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.current.weather_data;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 26/05/2018.
 */

public class WeatherDataBeaconModelFactory extends ViewModelProvider.NewInstanceFactory {
    /**
     * The model associated with the Battle Id
     */
    private long cityId;

    public WeatherDataBeaconModelFactory(long cityId) {
        this.cityId = cityId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherDataBeaconCardViewModel(cityId);
    }
}
