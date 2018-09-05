package com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather;

import android.support.annotation.NonNull;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.application.WeatherUpdate;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;

import androidx.work.Worker;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 15/08/2018.
 */
public class WeatherDataUpdaterWorker extends Worker {
    private static final String TAG = "WeatherDataUpdaterWorke";

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
        MyLog.e(TAG, "MotherActivity We are running a Worker ");
        WeatherUpdate weatherUpdate=new WeatherUpdate();
        //retrieve all you city Id and load them according
        try {
            WeatherDataUpdaterIntf weatherUpdaterService = MyApplication.instance.getServiceManager().getWeatherUpdaterService();
            for (Integer cityServerId : ForecastDatabase.getInstance().getCityDao().loadAllServerId()) {
                MyLog.e(TAG, "MotherActivity We are running a Worker and updating the following cityId "+cityServerId);
                weatherUpdaterService.downloadForecastWeatherSync(cityServerId);
                weatherUpdaterService.downloadCurrentWeatherSync(cityServerId);
            }
            weatherUpdate.setSucceed(true);
            weatherUpdate.setTimeInMillis(System.currentTimeMillis());
            return Result.SUCCESS;
        }catch (Exception e){
            weatherUpdate.setSucceed(false);
            weatherUpdate.setTimeInMillis(System.currentTimeMillis());
            return  Result.FAILURE;
        }finally {
            ForecastDatabase.getInstance().getWeatherUpdateDao().insert(weatherUpdate);
        }
    }

}
