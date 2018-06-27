package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 22/06/2018.
 */
public class ForercastWeatherActivityModel extends MotherViewModel {
    private static final String TAG = "ForercastWeatherActivit";
    /***********************************************************
     *  Attributes
     **********************************************************/

    /**
     * The cities displayed/on stage/on screen
     */
    LiveData<List<City>> onStageCities;
    /**
     * The main Live Data of the Use case: This is the main entity represents by the activity
     */
    LiveData<List<WeatherForecastItem>> data;
    /**
     * Sub LiveData depending on the data:
     * When the data changes, this live data should request new live stream
     * Because it's a sub list of the object WeatherForecastItem
     * But as we manage WeatherForecastItem as an entity, WeatherForecastItem.getWeathers==null always
     * So this is the big new pattern to learn: it uses Transformations
     */
    LiveData<List<Weather>> weatherForWeatherData;

    /***********************************************************
     *  Constructors
     **********************************************************/
    public ForercastWeatherActivityModel() {
        onStageCities = MyApplication.instance.getServiceManager().getCityService().loadOnStageCities();
        //instanciate your LiveData for your UI
        data = Transformations.switchMap(onStageCities, new Function<List<City>, LiveData<List<WeatherForecastItem>>>() {
            @Override
            public LiveData<List<WeatherForecastItem>> apply(List<City> input) {
                if (input.size() != 0) {
                    return ForecastDatabase.getInstance().getWeatherForecastItemDao().loadLiveDataByCityId(input.get(0).get_id());
                } else {
                    return new LiveData<List<WeatherForecastItem>>() {
                        @Nullable
                        @Override
                        public List<WeatherForecastItem> getValue() {
                            return new ArrayList<>();
                        }
                    };
                }
            }
        });
        //transform the main data to the weather List
        //So you said, if the liveData (first paremeter) changes, I rebuild a new LiveData
        //you use switchmap because you change your query (the cityId has changed)
        weatherForWeatherData = Transformations.switchMap(data, new Function<List<WeatherForecastItem>, LiveData<List<Weather>>>() {
            @Override
            public LiveData<List<Weather>> apply(List<WeatherForecastItem> input) {
                //TODO
                return null;
            }
        });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        holderToLiveData = null;
    }

    public LiveData<List<WeatherForecastItem>> getData() {
        return data;
    }

    public LiveData<List<City>> getOnStageCities() {
        return onStageCities;
    }
    /***********************************************************
     *  Holder pattern
     **********************************************************/
    /**
     * The sparse array that maps the holder with the LiveData to observe
     */
    SparseArray<MutableLiveData< WeatherForecastItem >> holderToLiveData = new SparseArray<>();
    /**
     * The weather to observe
     */
    SparseArray<LiveData< Weather >> holderToWeatherLD = new SparseArray<>();

    /**
     * Get the LiveData stream to observe in the ViewHolder
     * @param holderHash
     * @return
     */
    public MutableLiveData<WeatherForecastItem> getLiveDataForHolder(int holderHash){
        MyLog.e(TAG,"getLiveDataForHolder called with : "+holderHash);
        if (holderToLiveData.get(holderHash) != null) {
            return holderToLiveData.get(holderHash);
        }
        //else create it and add it to the HashMap
        MutableLiveData< WeatherForecastItem > current = new MutableLiveData< WeatherForecastItem >();
        holderToLiveData.put(holderHash, current);
        LiveData< Weather > weather=Transformations.switchMap(current, new Function<WeatherForecastItem, LiveData<Weather>>() {
            @Override
            public LiveData<Weather> apply(WeatherForecastItem input) {
                if (input != null) {
                    return ForecastDatabase.getInstance().getWeatherDao().loadLiveDataWeatherForWeatherForecastItemUnique(input.get_id());
                } else {
                    return new LiveData<Weather>() {
                        @Nullable
                        @Override
                        public Weather getValue() {
                            return null;
                        }
                    };
                }
            }
        });
        holderToWeatherLD.put(holderHash,weather);
        return current;
    }

    public LiveData<Weather> getHolderToWeatherLD(int holderHash) {
        return holderToWeatherLD.get(holderHash);
    }

    /**
     * Update the stream observe by the ViewHolder
     * @param holderHash The hasCode of the holder that observe the stream
     * @param position The position of the item to be plugged on this stream
     */
    public void bindHolder(int holderHash, int position) {
        final MutableLiveData<WeatherForecastItem> stream = holderToLiveData.get(holderHash);
        long forecastItemId=data.getValue().get(position).get_id();
        MyLog.e(TAG,"bindHolder called with : "+holderHash+"; stream="+stream+" for forecastId="+forecastItemId);
        if (stream != null) {
            ForecastDatabase.getInstance().getWeatherForecastItemDao()
                    .loadLiveDataById(forecastItemId)
                    .observeForever(new Observer<WeatherForecastItem>() {
                        @Override
                        public void onChanged(@Nullable WeatherForecastItem weatherForecastItem) {
                            stream.postValue(weatherForecastItem);
                            MyLog.e(TAG,"data has changed in the stream, stream should update obesrever for forecastId="+weatherForecastItem.get_id());
                        }
                    });
        }
    }
}
