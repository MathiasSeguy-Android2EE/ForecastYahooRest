/**
 * <ul>
 * <li>MotherPresenter</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.view</li>
 * <li>09/03/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.FakeEvent;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Mathias Seguy - Android2EE on 09/03/2016.
 * Have to be inherited by the Presenter
 * That way, MotherActivity can register and unregister them on EventBus
 */
@Deprecated
public class MotherPresenter {
    @Subscribe
    public void onEvent(FakeEvent event){
        //just to avoid
        //EventBusException: Subscriber class WeatherDataUpdater and its super classes
        // have no public methods with the @Subscribe annotation
    }
}
