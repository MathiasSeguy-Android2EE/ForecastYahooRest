package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.weather;

import android.arch.lifecycle.LiveData;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.service.MotherBusinessServiceIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.calculated.WeatherOfTheDay;

import java.util.List;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 05/07/2018.
 */
public interface WeatherOfTheDayRepositoryIntf extends MotherBusinessServiceIntf{
    /**
     * Returuns teh elements of the WeatherOfTheDay for a specific city
     * @param cityId
     * @return
     */
    LiveData<List<WeatherOfTheDay>> loadLiveDataByCityId(long cityId);;
}
