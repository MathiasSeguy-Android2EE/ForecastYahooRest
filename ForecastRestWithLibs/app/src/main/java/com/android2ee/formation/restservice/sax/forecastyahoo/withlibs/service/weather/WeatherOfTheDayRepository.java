package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.weather;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.dao.ForecastDatabase;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.MotherBusinessService;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.ServiceManagerIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.calculated.WeatherOfTheDay;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 05/07/2018.
 */
public class WeatherOfTheDayRepository extends MotherBusinessService implements WeatherOfTheDayRepositoryIntf{
    /**
     * Constructor
     *
     * @param srvManager
     */
    public WeatherOfTheDayRepository(ServiceManagerIntf srvManager) {
        super(srvManager);
    }

    /**
     * Clean your resource when your service die
     */
    @Override
    public void onDestroy() {

    }

    @Override
    public LiveData<List<WeatherOfTheDay>> loadLiveDataByCityId(long cityId){
        return ForecastDatabase.getInstance().getWeatherOfTheDayDao().loadLiveDataByCityId(cityId);
    }
}
