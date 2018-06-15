package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.MotherViewModel;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 15/06/2018.
 */
public class CurrentWeatherActivityModel extends MotherViewModel {
    LiveData<WeatherData> data;
    public CurrentWeatherActivityModel(long cityId) {
        data=ForecastDatabase.getInstance().getWeatherDataDao().loadLiveDataCurrentByCityId(cityId);
    }

    public LiveData<WeatherData> getLiveData(){
        return data;
    }

    @Override
    public void onViewAttached() {

    }
}
