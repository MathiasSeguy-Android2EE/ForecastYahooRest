package com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.main;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.MotherViewModel;

import java.util.List;

public abstract class MainViewModel extends MotherViewModel {

    private Long contextId;

    public MainViewModel(long contextId) {
        this.contextId = contextId;
    }

    /***********************************************************
     *  Attributes
     **********************************************************/
    LiveData<List<Main>> allMainLiveData;

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
    long getContextId() {
        return contextId;
    }

    public abstract LiveData<List<Main>> getAllMainLiveData();

}