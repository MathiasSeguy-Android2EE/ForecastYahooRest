/**
 * <ul>
 * <li>UsingLinearRV</li>
 * <li>com.android2ee.recyclerview.fragments</li>
 * <li>12/10/2015</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.PresenterInjector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.recyclerview.WeatherRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mathias Seguy - Android2EE on 12/10/2015.
 * This fragment aims to display a Weather and a CityForecast
 */
public class WeatherCityFrag extends Fragment implements WeatherCityViewIntf {
    private static final String TAG = "WeatherCityFrag";
    public static final String CITY_ID = "cityId";
    /***********************************************************
     *  Presenter
     **********************************************************/
    /**
     * The Presenter associated with that view
     */
    WeatherCityPresenterIntf presenter=null;
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     *The recycler view
     */
    private RecyclerView recyclerView;
    /**
     * µIts adapter
     */
    private RecyclerView.Adapter recyclerViewAdapter;
    /***********************************************************
     *  Managing LifeCycle
     **********************************************************/
    /**mandatory*/
    public static WeatherCityFrag newInstance(int cityID) {
        Bundle args = new Bundle();
        args.putInt(CITY_ID, cityID);
        WeatherCityFrag fragment = new WeatherCityFrag();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * The selected item view
     */
    private View selectedItemView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter= PresenterInjector.getWeatherCityPresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.weatherfragment,container,false);
        recyclerView= (RecyclerView) myView.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        recyclerView.setLayoutManager(getLayoutManager());
        // specify an adapter (see also next example)
        recyclerViewAdapter = getRecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        return myView ;
    }

    @Override
    public void onStart() {
        super.onStart();
        //register eventbus
        EventBus.getDefault().register(presenter);
        //load the weather and the forecast
        int cityId=getArguments().getInt(CITY_ID);
        presenter.loadCurrentWeather(cityId);
        presenter.loadForecast(cityId);
    }

    @Override
    public void onStop() {
        super.onStop();
        //unregister eventbus
        EventBus.getDefault().unregister(presenter);
    }

    /***********************************************************
     *  Managing RecyclerView
     **********************************************************/

    /**
     *
     * @return The Adapter to use
     */
    protected RecyclerView.Adapter getRecyclerViewAdapter(){
        Log.e(TAG, "getRecyclerViewAdapter() called with: " + "");
        return new WeatherRecyclerViewAdapter(presenter,getActivity());
    }
    /**
     *
     * @return The Layout to use when you are a daughter class
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        /** @param context Current context, will be used to access resources.
         * @param spanCount The number of columns or rows in the grid
         * @param orientation Layout orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
         * @param reverseLayout When set to true, layouts from end to start.*/
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        //define specific span of specific cells according to a rule
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int arg0) {
                return 1;// (arg0 % 3) == 0 ? 2 : 1;
            }
        });
        return gridLayoutManager;
    }
    /***********************************************************
     *  Implement the WeatherCityViewIntf
     **********************************************************/

    /**
     * Update the current weather
     */
    @Override
    public void updateCurrentWeather() {
        //TODO
    }

    /**
     * Update the cityForecast
     */
    @Override
    public void updateForecast() {
        Log.e(TAG, "updateForecast() called with: " + "");
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
