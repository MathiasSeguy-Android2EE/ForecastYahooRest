package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.current.sys;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.dao.ForecastDatabase;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.Sys;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.MotherViewModel;

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

}
