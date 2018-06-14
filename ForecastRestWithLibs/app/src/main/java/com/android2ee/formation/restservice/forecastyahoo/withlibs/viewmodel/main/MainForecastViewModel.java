package com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.main;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;

import java.util.List;

public class MainForecastViewModel extends MainViewModel {

    public MainForecastViewModel(long contextId) {
        super(contextId);
    }

    /***********************************************************
     *  Business Methods
     **********************************************************/

    public LiveData<List<Main>> getAllMainLiveData() {
        if (allMainLiveData == null) {
            allMainLiveData = ForecastDatabase.getInstance()
                    .getMainDao()
                    .loadLiveDataWeatherForWeatherForecastItem(getContextId());
        }
        return allMainLiveData;
    }

}
