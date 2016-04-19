/**
 * <ul>
 * <li>ConnectivityChangeEvent</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event;

/**
 * Created by Mathias Seguy - Android2EE on 24/02/2016.
 */
public class ConnectivityChangeEvent {
    private boolean isConnected;
    private boolean isWifiConnected;
    private int networkType;

    public ConnectivityChangeEvent() {
    }

    public ConnectivityChangeEvent(int networkType, boolean isConnected, boolean isWifiConnected) {
        this.networkType = networkType;
        this.isConnected = isConnected;
        this.isWifiConnected = isWifiConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isWifiConnected() {
        return isWifiConnected;
    }

    public void setIsWifiConnected(boolean isWifiConnected) {
        this.isWifiConnected = isWifiConnected;
    }

    public int getNetworkType() {
        return networkType;
    }

    public void setNetworkType(int networkType) {
        this.networkType = networkType;
    }
}
