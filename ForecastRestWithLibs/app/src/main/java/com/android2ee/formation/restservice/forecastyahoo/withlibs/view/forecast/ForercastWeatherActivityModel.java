package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.graphics.Bitmap;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecatsItemWithMainAndWeathers;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.PictureCacheDownloader;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherViewModel;

import java.lang.ref.WeakReference;
import java.util.HashMap;
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
     * The main Live Data (WeatherForecastItem) of the Use case: This is the main entity represents by the activity
     */
    LiveData<List<WeatherForecatsItemWithMainAndWeathers>> data;


    /***********************************************************
     *  Constructors
     **********************************************************/
    public ForercastWeatherActivityModel() {
        onStageCities = MyApplication.instance.getServiceManager().getCityService().loadOnStageCities();
        //instanciate your LiveData for your UI
        data = Transformations.switchMap(onStageCities, new Function<List<City>, LiveData<List<WeatherForecatsItemWithMainAndWeathers>>>() {
            @Override
            public LiveData<List<WeatherForecatsItemWithMainAndWeathers>> apply(List<City> input) {
                if (input.size() != 0) {
                    return MyApplication.instance.getServiceManager().getForecastRepository().loadForecastItemWithMainAndWeatherSync(input.get(0).get_id());
                } else {
                    return null;
                }

            }});
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<List<WeatherForecatsItemWithMainAndWeathers>> getData() {
        return data;
    }

    public LiveData<List<City>> getOnStageCities() {
        return onStageCities;
    }


    HashMap<String,WeakReference<MutableLiveData<Bitmap>>> iconNameToDownloadedBitmap = new HashMap<>();
    public MutableLiveData<Bitmap> getIconBitmap(String name){
        if(iconNameToDownloadedBitmap.get(name)!=null){
            //why naming it john? because john is a good fellow when not null :')
            MutableLiveData<Bitmap> john= iconNameToDownloadedBitmap.get(name).get();
            if(john!=null){
                return john;
            }
        }
        MutableLiveData<Bitmap> iconBM;
        //creation case: Check the disk
        if(PictureCacheDownloader.isPictureSavedOnDisk(name)){
            iconBM=PictureCacheDownloader.loadPictureFromDiskAsynch(name);
        }else{
            //nothing on the disk, download
            iconBM=MyApplication.instance.getServiceManager().getForecastRepository().downloadPictureSafelyAsynch("http://openweathermap.org/img/w/"+name+".png");
        }
        iconNameToDownloadedBitmap.put(name, new WeakReference<>(iconBM));
        return iconBM;
    }

}
