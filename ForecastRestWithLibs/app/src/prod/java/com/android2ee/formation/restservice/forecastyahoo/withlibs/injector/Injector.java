/**
 * <ul>
 * <li>Injector</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.injector</li>
 * <li>09/04/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.injector;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.com.DataCommunication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.daoold.DaoManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManager;

/**
 * Created by Mathias Seguy - Android2EE on 09/04/2016.
 * Inject the Production Class
 *  Enables injection of mock implementations at compile time.
 *  This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injector {

    /**
     * Return The ServiceManager for Production Context
     * @param application
     * @return The ServiceManager
     */
    public static ServiceManager getServiceManager(MyApplication application){
        return new ServiceManager(application);
    }

    public static DaoManager getDaoManager(){
        return DaoManager.getInstance();
    }

    public static DataCommunication getDataCommunication(){
        return DataCommunication.getINSTANCE();
    }
}
