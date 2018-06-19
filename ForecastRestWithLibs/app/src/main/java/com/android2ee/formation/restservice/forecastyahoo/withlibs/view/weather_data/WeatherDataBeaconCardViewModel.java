package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather_data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;

public class WeatherDataBeaconCardViewModel extends ViewModel {
    LiveData<WeatherData> data;
    public WeatherDataBeaconCardViewModel(long cityId) {
        data = ForecastDatabase.getInstance().getWeatherDataDao().loadLiveDataCurrentByCityId(cityId);
    }

    public LiveData<WeatherData> getLiveData(){
        return data;
    }
}
