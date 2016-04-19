/**
 * <ul>
 * <li>ForecastRestYahooSax</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo</li>
 * <li>28 mai 2014</li>
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

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.WeatherPresenterIntf;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.fragment.WeatherCityFrag;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 19/05/2015.
 * This class is an Adapter, it's goal is to give at the right time the right view
 * So nothing complex here
 * This class only deal with Fragments !!!
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MyPagerAdapter";
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * The dataset (it'is handled by the presenter)
     */
 //   private final ArrayList<City> dataset;
    /**
     * The provider of the dataset
     */
    private final WeatherPresenterIntf provider;
    /**
     * The list of ordered fragments
     */
    private final ArrayList<WeatherCityFrag> fragments;
    /**
     *
     */
    private final AppCompatActivity ctx;


    //On fournit à l'adapter la liste des fragments à afficher

    /***
     * The constructor
     * @param ctx The Context
     */
    public MyPagerAdapter(AppCompatActivity ctx, WeatherPresenterIntf provider) {
        super(ctx.getSupportFragmentManager());
        this.ctx=ctx;
        this.provider= provider;
        fragments = new ArrayList<>();
        ArrayList<City> dataset=provider.getCities();
        if(dataset.size()!=0) {
            for (City city : dataset) {
                fragments.add(WeatherCityFrag.newInstance(city.getCityId()));
            }
        }
    }
    @Override
    public int getCount() {
        return provider.getCities().size();
    }
    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return provider.getCities().get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /***********************************************************
     * Managing dynamic add/remove of the pageFragment
     **********************************************************/
    private long baseId = 0;
    //this is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(Object object) {
        //track entrance
        Log.e(TAG, "getItemPosition() has been called"+ PagerAdapter.POSITION_NONE);
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }
    /**
     * This method is used to create the tag of the fragment when adding them to the backstack
     * The viewPager looks at the fragment using that tag
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        //track entrance
        Log.e(TAG, "getItemId() has been called and returns "+(baseId + position));
        // give an ID different from position when position has been changed
        return baseId + position;
    }
    /**
     * Notify that the position of a fragment has been changed.
     * Create a new ID for each position to force recreation of the fragment
     */
    public void notifyRebuildAll() {
        //track entrance
        Log.e(TAG, "notifyRebuildAll() has been called");
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getCount() +getCount();
        //then also rebuild your id to display
        //A stuff I never did before, instanciate my fragment
        fragments.clear();
        ArrayList<City> dataset=provider.getCities();
        if(dataset.size()!=0) {
            for (City city : dataset) {
                fragments.add(WeatherCityFrag.newInstance(city.getCityId()));
            }
        }
        //and then notify
        notifyDataSetChanged();
    }

}

