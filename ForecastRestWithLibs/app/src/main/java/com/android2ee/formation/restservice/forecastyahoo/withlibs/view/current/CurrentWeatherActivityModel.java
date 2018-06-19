package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
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
    /**
     * The main Live Data of the Use case: This is the main entity represents by the activity
     */
    LiveData<WeatherData> data;
    /**
     * Sub LiveData depending on the data:
     * When the data changes, this live data should request new live stream
     * Because it's a sub list of the object WeatherData
     * But as we manage weatherData as an entity, weatherData.getWeathers==null always
     * So this is the big new pattern to learn: it uses Transformations
     */
    LiveData<List<Weather>> weatherForWeatherData;
    /***********************************************************
    *  Constructors
    **********************************************************/
    /**
     * You manage your entity by their id
     * So you need to know which id is the entity you want to manage
     * @param cityId
     */
    public CurrentWeatherActivityModel(long cityId) {
        if(cityId==CurrentWeatherActivity.NULL_VALUE){
            return;
        }
        //instanciate your LiveData for your UI
        data= ForecastDatabase.getInstance().getWeatherDataDao().loadLiveDataCurrentByCityId(cityId);
        //transform the main data to the weather List
        //So you said, if the liveData (first paremeter) changes, I rebuild a new LiveData
        //you use switchmap because you change your query (the cityId has changed)
        weatherForWeatherData= Transformations.switchMap(data, new Function<WeatherData, LiveData<List<Weather>>>() {
            @Override
            public LiveData<List<Weather>> apply(WeatherData input) {
                //So you have the new WeatherData live streamed by the livedata
                //and you want to load its list of weathers
                //So you instanciate your new LiveData according to that query:
                //I want the Weathers with the foreign key weatherdata_id equels to weatherData.getId
                //And it's done
                //And the previous liveData unregister from its previous query and plug to the new one
                //because you switchMap (if you had map only, you would have both streams)
                return ForecastDatabase.getInstance().getWeatherDao().loadLiveDataWeatherForWeatherData(input.get_id());

            }
        });
    }

    /***********************************************************
    *  Business Methods
    **********************************************************/
    /**
     * Delete the current City
     * @param cityId
     */
    public void deleteCity(long cityId){
        //Call the service
        MyApplication.instance.getServiceManager().getCityService().deleteCityByIdAsynch(cityId);
    }

    /***********************************************************
     *  Getters for the Views
     **********************************************************/
    /**
     * The liveData to observe when displaying a WethaerData with the id passed in parameter of the constructor
     * @return the liveData to observe when displaying a WeatherData
     */
    public LiveData<WeatherData> getLiveData(){
        return data;
    }

    /**
     * The liveData to observe when displaying the weathers list of the WethaerData with the id passed in parameter of the constructor
     * @return The liveData to observe when displaying the weathers list
     */
    public LiveData<List<Weather>> getWeather(){
        return weatherForWeatherData;
    }
}
