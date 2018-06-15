package com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.main.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.main.MainCurrentViewModel;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.main.MainForecastViewModel;

public class MainModelFactory extends ViewModelProvider.NewInstanceFactory {

    private long contextId;
    private boolean isForecast;

    public MainModelFactory(long contextId,
                            boolean isForecast) {
        this.contextId = contextId;
        this.isForecast = isForecast;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (isForecast) {
            return (T) new MainForecastViewModel(contextId);
        } else {
            return (T) new MainCurrentViewModel(contextId);
        }
    }
}
