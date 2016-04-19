/**
 * <ul>
 * <li>RetrofitBuilder</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.com</li>
 * <li>23/02/2016</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.com.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.MoshiConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Mathias Seguy - Android2EE on 23/02/2016.
 */
public class RetrofitBuilder {

    private static RetrofitServiceIntf webService=null;
    /***********************************************************
     * complex/Complete Retrofit WebServer
     **********************************************************/
    @NonNull
    public static RetrofitServiceIntf getComplexClient(Context ctx) {
        if(webService==null) {
            //get the OkHttp client
            OkHttpClient client = getOkHttpClient(ctx);
            String baseUrl=ctx.getResources().getString(R.string.root_url);
            //now it's using the cach
            //Using my HttpClient
            Retrofit raCustom = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    //You need to add a converter if you want your Json to be automagicly convert into the object
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
            webService = raCustom.create(RetrofitServiceIntf.class);
        }
        return webService;
    }

    @NonNull
    public static OkHttpClient getOkHttpClient(Context ctx) {
        //define the OkHttp Client with its cach!
        //Assigning a CacheDirectory
        File myCacheDir = new File(ctx.getCacheDir(), "OkHttpCache");
        //you should create it...
        int cacheSize = 1024 * 1024;
        Cache cacheDir = new Cache(myCacheDir, cacheSize);
//        Interceptor customLoggingInterceptor = new CustomLoggingInterceptor();
        HttpLoggingInterceptor httpLogInterceptor = new HttpLoggingInterceptor();
        httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                //add a cach
                .cache(cacheDir)
                        //add interceptor (here to log the request)
//                .addInterceptor(customLoggingInterceptor)
                .addInterceptor(httpLogInterceptor)
                .build();


    }
}
