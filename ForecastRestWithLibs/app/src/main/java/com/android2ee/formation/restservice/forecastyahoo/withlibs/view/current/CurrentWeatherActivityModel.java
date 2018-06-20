package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.WeatherData;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.PictureCacheDownloader;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.viewmodel.MotherViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 15/06/2018.
 */
public class CurrentWeatherActivityModel extends MotherViewModel {
    /***********************************************************
     *  Managing the WeatherData
     **********************************************************/

    /**
     * The cities displayed/on stage/on screen
     */
    LiveData<List<City>> onStageCities;
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
    @Deprecated
    public CurrentWeatherActivityModel(long cityId) {
        this();
    }
    /**
     * You manage your entity by their id
     * So you need to know which id is the entity you want to manage
     * To do that we listen for the onStageCities stream
     */
    public CurrentWeatherActivityModel() {
        onStageCities=MyApplication.instance.getServiceManager().getCityService().loadOnStageCities();
        //instanciate your LiveData for your UI
        data= Transformations.switchMap(onStageCities, new Function<List<City>, LiveData<WeatherData>>() {
            @Override
            public LiveData<WeatherData> apply(List<City> input) {
                if(input.size()!=0){
                    return ForecastDatabase.getInstance().getWeatherDataDao().loadLiveDataCurrentByCityId(input.get(0).get_id());
                }else{
                    return new LiveData<WeatherData>() {
                        @Nullable
                        @Override
                        public WeatherData getValue() {
                            return null;
                        }
                    };
                }
            }
        });
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
                if(input!=null){
                    return ForecastDatabase.getInstance().getWeatherDao().loadLiveDataWeatherForWeatherData(input.get_id());
                }else{
                    return new LiveData<List<Weather>>() {
                        @Nullable
                        @Override
                        public List<Weather> getValue() {
                            return new ArrayList<>();
                        }
                    };
                }
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        holderToBitmapLiveData=null;
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

    /**
     * The liveData to observe when displaying the city of the WethaerData with the id passed in parameter of the constructor
     * @return The liveData to observe when displaying the city
     */
    public LiveData<List<City>> getOnStageCities() {
        return onStageCities;
    }

    /***********************************************************
     *  Managing Loading Image
     **********************************************************/
    /**
     * The sparse array that maps the holder with the stream of bitmap
     * The goal is to have for each Holder a specific LiveData<Bitmap> for its icon
     * So it updates it accordng to it's icon value
     */
    SparseArray<MutableLiveData<Bitmap>> holderToBitmapLiveData=new SparseArray<>();

    /**
     * First find you LiveData and register to it
     * This method provide you the liveData to observe
     * @param holderHash
     * @return
     */
    public MutableLiveData<Bitmap> getIconByHolder(int holderHash){
        if(holderToBitmapLiveData.get(holderHash)!=null){
            return holderToBitmapLiveData.get(holderHash);
        }
        //else create it and add it to the HashMap
        MutableLiveData<Bitmap> current=new MutableLiveData<Bitmap>();
        holderToBitmapLiveData.put(holderHash,current);
        return current;
    }

    /**
     * Then update the LiveData the Holder is observing, it will changes its icon
     * @param holderHash
     * @param iconName
     */
    public void updateIcon(int holderHash,String iconName){
        MutableLiveData<Bitmap> current=holderToBitmapLiveData.get(holderHash);
        if(current!=null){
            current.postValue(PictureCacheDownloader.loadPictureFromDisk(iconName));
        }
    }
}
