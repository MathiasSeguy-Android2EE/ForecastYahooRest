package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 15/06/2018.
 */
public class CurrentWeatherActivityModel extends ViewModel {
    LiveData<List<WeatherData>> data;
    public CurrentWeatherActivityModel() {
        data=ForecastDatabase.getInstance().getWeatherDataDao().loadLiveDataByCityId(cityId);
    }

    public LiveData<List<WeatherData>> getLiveData(){
        return data;
    }
}
