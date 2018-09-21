/**
 * <ul>
 * <li>WeatherPresenterTest</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view</li>
 * <li>11/04/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.forecast.testpresenter;

import android.test.AndroidTestCase;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.injector.transverse.DataGenerator;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.DataCheck;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.DeepDataCheck;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mathias Seguy - Android2EE on 11/04/2016.
 * Ok, the importnat point here is that you validate your action at te View level
 * So the presenter is tested and we check the result using the ViewMocked
 * So we sure the action is ok and do what it has to do on view
 */
public class WeatherPresenterTest  extends AndroidTestCase {

    public static final String TAG = "WeatherPresenterTest";

    public void setUp() throws Exception {
        super.setUp();
        MyLog.e(TAG, "setUp() is called");
    }

    public void tearDown() throws Exception {
        super.tearDown();
        MyLog.e(TAG, "tearDown() is called");
    }

    /***********************************************************
     * Testing the City Load and city get
     **********************************************************/
    private AtomicBoolean citiesLoaded=new AtomicBoolean(false);
    public void testLoadCities()throws Exception {
        //Ok, initialization is a pain
        //You have to initialize your ViewMacked
        WeatherViewMocked view=new WeatherViewMocked(this);
        //The presenter to test
        WeatherPresenter presenter=new WeatherPresenter(view);
        //make yourself the link
        view.setPresenter(presenter);
        //register on EventBus (because it's done by the real view)
        EventBus.getDefault().register(presenter);
        presenter.loadCities();
        int timeout = 0;
        while (!citiesLoaded.get()) {
            Thread.currentThread().sleep(500);
            MyLog.e(TAG, "waiting for the citiesLoaded moment in testLoadCities"+Thread.currentThread().getName());

            if (timeout == 5) {
                fail("Timeout");
            }
            timeout++;
        }
        //and don't forget to unregister
        EventBus.getDefault().unregister(presenter);

    }

    public void loadCitiesCallBack(WeatherPresenter presenter){
        //track entrance
        MyLog.e(TAG, "loadCitiesCallBack() has been called on thread "+Thread.currentThread().getName());
        assertNotNull(presenter.getCities());
        //By the way we can admit that we have test also the getCities, don't we ?
        ArrayList<City> cities= (ArrayList<City>) presenter.getCities();
        assertTrue(cities.size()==4);
        for(int i=0;i<4;i++){
            DataCheck.getInstance().checkCity(cities.get(i));
        }
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(1102), cities.get(0));
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(1312), cities.get(1));
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(0411), cities.get(2));
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(1911), cities.get(3));
        citiesLoaded.set(true);
    }
    /***********************************************************
     * Testing the City Load and city get
     **********************************************************/
    private AtomicBoolean citiesDeletedEmptyCase=new AtomicBoolean(false),citiesDeleted=new AtomicBoolean(false);
    public void testDeleteCity() throws InterruptedException {
        //don't know how to test that
        //Ok, initialization is a pain
        //You have to initialize your ViewMacked
        WeatherViewMocked view=new WeatherViewMocked(this);
        //The presenter to test
        WeatherPresenter presenter=new WeatherPresenter(view);
        //make yourself the link
        view.setPresenter(presenter);
        presenter.deleteCity(DataGenerator.getCity());
       // and then, wtf
        while (!citiesDeletedEmptyCase.get()) {
            Thread.currentThread().sleep(500);
            MyLog.e(TAG, "waiting for the citiesDeleted empty case moment in testDeleteCity"+Thread.currentThread().getName());
        }
        presenter.getCities().add(DataGenerator.getCity(1));
        presenter.getCities().add(DataGenerator.getCity(2));
        presenter.deleteCity(DataGenerator.getCity(2));
        while (!citiesDeleted.get()) {
            Thread.currentThread().sleep(500);
            MyLog.e(TAG, "waiting for the citiesDeleted moment in testDeleteCity"+Thread.currentThread().getName());
        }
    }
    public void launchCityActivity(WeatherPresenter presenter){
        //track entrance
        MyLog.e(TAG, "launchCityActivity() has been called on thread "+Thread.currentThread().getName());
        assertNotNull(presenter.getCities());
        //By the way we can admit that we have test also the getCities, don't we ?
        ArrayList<City> cities= (ArrayList<City>) presenter.getCities();
        assertTrue(cities.size()==0);
        citiesDeletedEmptyCase.set(true);
    }
    public void cityDeleted(WeatherPresenter presenter){
        //track entrance
        MyLog.e(TAG, "cityDeleted() has been called on thread "+Thread.currentThread().getName());
        assertNotNull(presenter.getCities());
        //By the way we can admit that we have test also the getCities, don't we ?
        ArrayList<City> cities= (ArrayList<City>) presenter.getCities();
        assertTrue(cities.size()==1);

            DataCheck.getInstance().checkCity(cities.get(0));
        DeepDataCheck.getInstance().checkCity(DataGenerator.getCity(1), cities.get(0));
        citiesDeleted.set(true);
    }
}
