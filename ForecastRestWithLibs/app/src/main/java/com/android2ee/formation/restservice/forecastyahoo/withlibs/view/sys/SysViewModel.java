package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.sys;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.Sys;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.MotherViewModel;

/**
 * Created by Marion Aubard on 15/06/2018.
 */
public class SysViewModel extends MotherViewModel {

    LiveData<Sys> data;

    public SysViewModel(long weatherDataId) {
        data = ForecastDatabase.getInstance().getSysDao().loadLiveDataSysByWeatherDataId(weatherDataId);
    }

    public LiveData<Sys> getLiveData(){
        return data;
    }

    @Override
    public void onViewAttached() {

    }
}
