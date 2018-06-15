package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 26/05/2018.
 */

public class CurrentWeatherModelFactory extends ViewModelProvider.NewInstanceFactory {
    /**
     * The model associated with the Battle Id
     */
    private long cityId;

    public CurrentWeatherModelFactory(long battleId) {
        this.cityId = battleId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CurrentWeatherActivityModel(cityId);
    }
}
