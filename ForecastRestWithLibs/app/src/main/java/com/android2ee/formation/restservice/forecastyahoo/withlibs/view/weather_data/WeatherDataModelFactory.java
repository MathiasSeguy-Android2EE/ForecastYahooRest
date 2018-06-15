package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather_data;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.CurrentWeatherActivityModel;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 26/05/2018.
 */

public class WeatherDataModelFactory extends ViewModelProvider.NewInstanceFactory {
    /**
     * The model associated with the Battle Id
     */
    private long cityId;

    public WeatherDataModelFactory(long cityId) {
        this.cityId = cityId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherDataCardViewModel(cityId);
    }
}
