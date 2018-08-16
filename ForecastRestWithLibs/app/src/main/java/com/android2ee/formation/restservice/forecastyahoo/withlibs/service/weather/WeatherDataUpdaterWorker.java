package com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather;

import android.support.annotation.NonNull;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.ForecastDatabase;

import androidx.work.Worker;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 15/08/2018.
 */
public class WeatherDataUpdaterWorker extends Worker {

    /**
     * Override this method to do your actual background processing.
     *
     * @return The result of the work, corresponding to a {@link Result} value.  If a
     * different value is returned, the result shall be defaulted to
     * {@link Result#FAILURE}.
     */
    @NonNull
    @Override
    public Result doWork() {
        //retrieve all you city Id and load them according
        WeatherDataUpdaterIntf weatherUpdaterService = MyApplication.instance.getServiceManager().getWeatherUpdaterService();
        for (Integer cityServerId : ForecastDatabase.getInstance().getCityDao().loadAllServerId()) {
            weatherUpdaterService.downloadForecastWeatherSync(cityServerId);
            weatherUpdaterService.downloadCurrentWeatherSync(cityServerId);
        }
        return Result.SUCCESS;
    }

}
