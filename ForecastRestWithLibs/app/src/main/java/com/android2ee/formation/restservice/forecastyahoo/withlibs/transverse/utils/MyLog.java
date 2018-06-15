/**
 * <ul>
 * <li>MyMyLog</li>
 * <li>com.android2ee.mystoptabac.transverse</li>
 * <li>17/05/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils;


import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.BuildConfig;


/**
 * Created by Mathias Seguy - Android2EE on 17/05/2016.
 */
public class MyLog {

    private MyLog() {
    }
    public static int v(String tag, String msg) {
        if(BuildConfig.DEBUG){
            return Log.v(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }
    public static int v(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG){
            return Log.v(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }
    public static int d(String tag, String msg) {
        if(BuildConfig.DEBUG){
            return Log.d(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }

    public static int d(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG){
            return Log.d(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }
    public static int i(String tag, String msg) {
        if(BuildConfig.DEBUG){
            return Log.i(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }

    public static int i(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG){
            return Log.i(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }
    public static int w(String tag, String msg) {
        if(BuildConfig.DEBUG){
            return Log.w(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }

    public static int w(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG){
            return Log.w(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }
    public static int w(String tag, Throwable tr) {
        if(BuildConfig.DEBUG){
            return Log.w(tag,tr);
        }else{
            //do nothing
            return -1;
        }
    }
    public static int e(String tag, String msg) {
        if(BuildConfig.DEBUG){
            return Log.e(tag,msg);
        }else{
            //do nothing
            return -1;
        }
    }

    public static int e(String tag, String msg, Throwable tr) {
        if(BuildConfig.DEBUG){
            return Log.e(tag,msg,tr);
        }else{
            //do nothing
            return -1;
        }
    }

}
