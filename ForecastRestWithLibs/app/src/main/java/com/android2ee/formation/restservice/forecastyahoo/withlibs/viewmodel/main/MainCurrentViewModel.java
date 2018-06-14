package com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.main;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;

public class MainCurrentViewModel extends MainViewModel {

    public MainCurrentViewModel(long contextId) {
        super(contextId);
    }

    /***********************************************************
     *  Business Methods
     **********************************************************/

    public LiveData<Main> getMainLiveData() {
        if (mainLiveData == null) {
            mainLiveData = ForecastDatabase.getInstance()
                    .getMainDao()
                    .loadLiveDataWeatherForWeatherData(getContextId());
        }
        return mainLiveData;
    }

}
