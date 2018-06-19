package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.MotherViewModel;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 15/06/2018.
 */
public class CurrentWeatherActivityModel extends MotherViewModel {
    /***********************************************************
     *  Managing the WeatherData
     **********************************************************/

    LiveData<WeatherData> data;
    LiveData<List<Weather>> weatherForWeatherData;
    LiveData<List<Weather>> allWeather=null;
    public CurrentWeatherActivityModel(long cityId) {
        data=ForecastDatabase.getInstance().getWeatherDataDao().loadLiveDataCurrentByCityId(cityId);
        weatherForWeatherData= Transformations.switchMap(data, new Function<WeatherData, LiveData<List<Weather>>>() {
            @Override
            public LiveData<List<Weather>> apply(WeatherData input) {
                return ForecastDatabase.getInstance().getWeatherDao().loadLiveDataWeatherForWeatherData(input.getCityId());

            }
        });
    }

    /***********************************************************
     *  Getters for the Views
     **********************************************************/
    public LiveData<WeatherData> getLiveData(){
        return data;
    }
    public LiveData<List<Weather>> getWeather(){
        return weatherForWeatherData;
    }
    @Override
    public void onViewAttached() {

    }
}
