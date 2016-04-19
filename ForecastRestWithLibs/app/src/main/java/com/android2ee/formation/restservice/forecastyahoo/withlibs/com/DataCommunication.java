/**
 * <ul>
 * <li>DataCommunication</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.com</li>
 * <li>24/02/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.com;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.com.noconnectivity.NoConnectivityDataCommunication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.com.retrofit.ConnectedDataCommunication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.ConnectivityChangeEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.FindCitiesResponse;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.CityForecast;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Mathias Seguy - Android2EE on 24/02/2016.
 * This class aims to make the Http call according to the interface DataCommunicationIntf
 * It's based on the principle of NoConnectivity No Http call
 */
public class DataCommunication implements DataCommunicationIntf{
    /**
     * The communication bus used
     */
    DataCommunicationIntf communication;
    /**
     * When the device is network connected this communication way is used
     */
    ConnectedDataCommunication connected=null;
    /**
     * When the device is NOT network connected this communication way is used
     */
    NoConnectivityDataCommunication noConnectivityDataCommunication = null;

    /***********************************************************
     * Singleton Design Pattern
     **********************************************************/
    private static DataCommunication INSTANCE;
    public static DataCommunication getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE=new DataCommunication();
        }
        return INSTANCE;
    }

    private DataCommunication() {
            //then according to your connectivity status, intialize your network communication
            if(MyApplication.instance.isConnected()){
                if(connected==null){
                    connected=new ConnectedDataCommunication();
                }
                communication=connected;
            }else{
                if(noConnectivityDataCommunication==null){
                    noConnectivityDataCommunication=new NoConnectivityDataCommunication();
                }
                communication=noConnectivityDataCommunication;
            }
    }

    /**
     * To be called when the application died
     * This is the main problem of the singleton pattern
     * They live as long as the process live
     */
    public void releaseMemory(){
        INSTANCE=null;
    }
    /***********************************************************
     * Business method
     **********************************************************/
    @Subscribe
    /**
     * Subscribe to the ConnectivityChangeEvent
     * When the connectivity changes you are notified here
     * So just switch to the right communication element
     */
    public void onEvent(ConnectivityChangeEvent  event){
        if(event.isConnected()){
            if(connected==null){
                connected=new ConnectedDataCommunication();
            }
            communication=connected;
        }else{
            if(noConnectivityDataCommunication==null){
                noConnectivityDataCommunication=new NoConnectivityDataCommunication();
            }
            communication=noConnectivityDataCommunication;
        }
    }

    @Override
    public FindCitiesResponse findCityByName(String cityName) {
       return communication.findCityByName(cityName);
    }

    @Override
    public Weather findWeatherByCityId(long cityId) {
        return communication.findWeatherByCityId(cityId);
    }

    @Override
    public CityForecast findForecastByCityId(long cityId) {
        return communication.findForecastByCityId(cityId);
    }
}
