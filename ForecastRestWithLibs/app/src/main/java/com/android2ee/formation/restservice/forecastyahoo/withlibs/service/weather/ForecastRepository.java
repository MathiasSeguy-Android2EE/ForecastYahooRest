package com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.database.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.MotherBusinessService;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecatsItemWithMainAndWeathers;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.PictureCacheDownloader;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 28/06/2018.
 */
public class ForecastRepository extends MotherBusinessService {
    private static final String TAG = "ForecastRepository";
    public ForecastRepository(ServiceManagerIntf srvManager) {
        super(srvManager);
    }


    public LiveData<List<WeatherForecatsItemWithMainAndWeathers>> loadForecastItemWithMainAndWeatherSync(Long cityId) {
        return ForecastDatabase.getInstance().getWeatherForecastItemDao().getWeatherForecastItemWithMainAndWeathersLD(cityId);
    }

    /**
     * Clean your resource when your service die
     */
    @Override
    public void onDestroy() {

    }

    /***********************************************************
     *  Attributes
     **********************************************************/


    /**
     *
     * @param urlGetPicture
     * @param bitmapMLD
     * @return
     */
    public static MutableLiveData<Bitmap> downloadPictureSafely(String urlGetPicture,MutableLiveData<Bitmap> bitmapMLD) {
        if(bitmapMLD!=null){
            bitmapMLD.postValue(PictureCacheDownloader.downloadPicture(urlGetPicture));
            return bitmapMLD;
        }else{
            MutableLiveData<Bitmap> ret=new MutableLiveData<>();
            ret.postValue(PictureCacheDownloader.downloadPicture(urlGetPicture));
            return ret;
        }
    }

    /**
     * Should be called by the View
     */
    static public MutableLiveData<Bitmap> downloadPictureSafelyAsynch(String urlGetPicture) {
        MutableLiveData<Bitmap> bitmapMLD=new MutableLiveData<>();
        MyApplication.instance.getServiceManager().getKeepAliveThreadsExecutor()
                .submit( new RunnableDownloadPictureSafely(urlGetPicture,bitmapMLD));
        return bitmapMLD;
    }

    /**
     * This is the runnable that will send the work in a background thread
     */
    static private class RunnableDownloadPictureSafely implements Runnable {
        String urlGetPicture;
        MutableLiveData<Bitmap> bitmapMLD;
        public RunnableDownloadPictureSafely(String urlGetPicture,MutableLiveData<Bitmap> bitmapMLD) {
            //
            this.urlGetPicture = urlGetPicture;
            this.bitmapMLD=bitmapMLD;
        }

        @Override
        public void run() {
            downloadPictureSafely(urlGetPicture,bitmapMLD);
        }
    }
}
