package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity.CityActivity;

import java.util.List;

public abstract class CityNavDrawerActivity extends MotherActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "NavigationDrawerActivit";
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * The navigation View to update through Menu
     */
    NavigationView navigationView;
    /**
     * All the cities in database
     */
    LiveData<List<City>> cities;
    /**
     * Number of cities in DB
     */
    LiveData<Integer> citiesCount;
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //manage the cities displayed

    }

    /**
     * Initialisation of the views
     */
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cities = MyApplication.instance.getServiceManager().getCityService().loadAllLiveDate();
        cities.observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                //cities list changes
                updateCities(cities);
            }
        });
        citiesCount = MyApplication.instance.getServiceManager().getCityService().countCities();
        citiesCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                checkCityNumber(integer);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /***********************************************************
     * Observers
     **********************************************************/
    /**
     * If there is no cities in DB, just launch CityActity
     *
     * @param count
     */
    private void checkCityNumber(Integer count) {
        MyLog.e(TAG,"checkCityNumber="+count);
        if (count == 0) {
            launchCityActivity(true);
        }
    }

    /**
     * When cities changes, update the menu of the NavDrawer
     * @param cities
     */
    private void updateCities(List<City> cities) {
        final Menu menu = navigationView.getMenu();
        menu.clear();
        SubMenu citiesSubMenu = menu.addSubMenu(0, 0, 0, "Cities");
        MenuItem item;
        for (City city : cities) {
            item = citiesSubMenu.add(0, (int) city.get_id(), 0, "" + city.getName());
            item.setIcon(R.drawable.ic_skyline);
        }
        navigationView.inflateMenu(R.menu.activity_navigation_drawer_drawer);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        for (City city : cities.getValue()) {
            if (id == city.get_id()) {
                selectCity(id);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        }

        //other icon
        if (id == R.id.nav_search) {
            // Launch the search cityactivity
            launchCityActivity(false);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /***********************************************************
    * Business Methods
    **********************************************************/
    /**
     * @param cityId
     */
    public abstract void selectCity(long cityId);

    /**
     * @param finish
     */
    public void launchCityActivity(boolean finish) {
        // then launch the CityActivity to select a city
        Intent launchCityActivity = new Intent(this, CityActivity.class);
        startActivity(launchCityActivity);
        if (finish) {
            finish();
        }
    }
}
