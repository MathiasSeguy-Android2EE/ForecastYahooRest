package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.weather;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.MotherBusinessServiceIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.forecast.WeatherForecatsItemWithMainAndWeathers;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 05/07/2018.
 */
public interface ForecastRepositoryIntf extends MotherBusinessServiceIntf{

    /**
     * Load the ForecastItemWithMainAndWeather list
     * @param cityId
     * @return
     */
    LiveData<List<WeatherForecatsItemWithMainAndWeathers>> loadForecastItemWithMainAndWeatherSync(Long cityId);
    /**
     *
     * @param urlGetPicture
     * @param bitmapMLD
     * @return
     */
    MutableLiveData<Bitmap> downloadPictureSafely(String urlGetPicture, MutableLiveData<Bitmap> bitmapMLD);

    /**
     * Should be called by the View
     */
    MutableLiveData<Bitmap> downloadPictureSafelyAsynch(String urlGetPicture);
}
