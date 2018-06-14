package com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.main;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.MotherViewModel;

import java.util.List;

public class MainViewModel extends MotherViewModel {

    /***********************************************************
     *  Attributes
     **********************************************************/
    private LiveData<List<Main>> allMainLiveData;

    /***********************************************************
     *  Lifecycle
     **********************************************************/
    @Override
    public void onViewAttached() {
        //ignore for the moment
    }

    /***********************************************************
     *  Business Methods
     **********************************************************/
    public LiveData<List<Main>> getAllMainLiveData() {
        if (allMainLiveData == null) {
            allMainLiveData = ForecastDatabase.getInstance().getMainDao().loadAllLiveData();
        }
        return allMainLiveData;
    }

}
