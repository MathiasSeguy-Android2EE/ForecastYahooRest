package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.current.weather_data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.dao.ForecastDatabase;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.WeatherData;

public class WeatherDataBeaconCardViewModel extends ViewModel {
    LiveData<WeatherData> data;
    public WeatherDataBeaconCardViewModel(long cityId) {
        data = ForecastDatabase.getInstance().getWeatherDataDao().loadLiveDataCurrentByCityId(cityId);
    }

    public LiveData<WeatherData> getLiveData(){
        return data;
    }
}
