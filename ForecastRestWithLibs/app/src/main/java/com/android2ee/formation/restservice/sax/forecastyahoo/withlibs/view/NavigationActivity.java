package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.dao.ForecastDatabase;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.application.WeatherUpdate;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.current.City;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.current.CurrentWeatherActivity;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.findcity.CityActivity;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.forecast.ForecastWeatherActivity;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.oftheday.WotdActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public abstract class NavigationActivity extends MotherActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "NavigationDrawerActivit";
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * The DrawerLayout
     * Take in charge NavigationView,Toolbar and BottomBar
     */
    DrawerLayout drawer;
    /**
     * The toggle of the drawerLayout
     */
    ActionBarDrawerToggle toggle;
    /**
     * The navigation View to update through Menu
     */
    NavigationView navigationView;
    /**
     * Text view to display the last time an update occured
     */
    AppCompatTextView txvLastUpdateTry, txvLastUpdateSuccess;
    /**
     * All the cities in database
     */
    LiveData<List<City>> cities;
    /**
     * Number of cities in DB
     */
    LiveData<Integer> citiesCount;
    /**
     * The bottom nav bar
     */
    BottomNavigationView bottomNavView;

    /**
     * Time pattern to display the update
     */
    private static final String TIME_PATTERN = "EEE HH:mm";
    /**
     * Date Formatter
     */
    private SimpleDateFormat df;
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize time formatter
        df = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
    }

    /**
     * Initialisation of the views
     */
    protected void initView() {
        //Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //BottomNavBar
        bottomNavView=findViewById(R.id.bottom_navigation);
        bottomNavView.setSelectedItemId(getBootomNavAssociatedItemId());
        if(bottomNavView!=null) {
            bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        cities = MyApplication.instance.getServiceManager().getCityService().loadAllLiveDate();
        cities.observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                //cities list changes
                createCitiesMenuItem(cities);
            }
        });
        citiesCount = MyApplication.instance.getServiceManager().getCityService().countCities();
        citiesCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                checkCityNumber(integer);
            }
        });

        //WorkManager status management and display
        //update date widget
        LiveData<WeatherUpdate> lastUpdateTry= ForecastDatabase.getInstance().getWeatherUpdateDao().loadLastTry();
        LiveData<WeatherUpdate> lastUpdateSucceed= ForecastDatabase.getInstance().getWeatherUpdateDao().loadLastSuccess();
        lastUpdateTry.observe(this, new Observer<WeatherUpdate>() {
            @Override
            public void onChanged(@Nullable WeatherUpdate weatherUpdate) {
                refreshLastUpdateTry(weatherUpdate);
            }
        });
        lastUpdateSucceed.observe(this, new Observer<WeatherUpdate>() {
            @Override
            public void onChanged(@Nullable WeatherUpdate weatherUpdate) {
                refreshLastUpdateSuccess(weatherUpdate);
            }
        });
    }

    @Override
    public void onBackPressed() {
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
    private void createCitiesMenuItem(List<City> cities) {
        final Menu menu = navigationView.getMenu();
        menu.clear();
        //load the cities
        SubMenu citiesSubMenu = menu.addSubMenu(0, 0, 0, "Cities");
        MenuItem item;
        for (City city : cities) {
            item = citiesSubMenu.add(0, (int) city.get_id(), 0, "" + city.getName());
            item.setIcon(R.drawable.ic_skyline);
        }
        //load the static menu
        navigationView.inflateMenu(R.menu.navigation_drawer_menu);
    }

    /**
     * Update the field with the last update succeess' date
     * @param weatherUpdate
     */
    private void refreshLastUpdateSuccess(WeatherUpdate weatherUpdate){
        //the NavHeader is built after the onCreate, if you try to find this component during the onCreate method
        //you'll always have null
        if(txvLastUpdateSuccess==null){
            txvLastUpdateSuccess =findViewById(R.id.txv_navdrawer_last_update_succeess);
        }
        if(weatherUpdate!=null){
            txvLastUpdateSuccess.setText(df.format(weatherUpdate.getTimeInMillis()));
        }else{
            txvLastUpdateSuccess.setText("N/A");
        }
    }
    /**
     * Update the field with the last update try' date
     * @param weatherUpdate
     */
    private void refreshLastUpdateTry(WeatherUpdate weatherUpdate){//the NavHeader is built after the onCreate, if you try to find this component during the onCreate method
        //you'll always have null
        if(txvLastUpdateTry==null){
            txvLastUpdateTry=findViewById(R.id.txv_navdrawer_last_update_try);
        }
        if(weatherUpdate!=null){
            txvLastUpdateTry.setText(df.format(weatherUpdate.getTimeInMillis()));
        }else{
            txvLastUpdateTry.setText("N/A");
        }
    }
    /***********************************************************
     *  Manage MenuItem selection
     *  MenuItem from Toolbar, NavBar and BottomBar
     **********************************************************/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //other icon
        if (onOptionsItemSelected(item)) {
            //do nothing, it has been managed
        }else {
            //First find if this item is diaplaying a city
            // Handle navigation view item clicks here.
            for (City city : cities.getValue()) {
                if (id == city.get_id()) {
                    selectCity(id);
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected abstract int getBootomNavAssociatedItemId();

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent browserIntent;
        //the double click on the selected itelm
       if(getBootomNavAssociatedItemId()==item.getItemId()){
            return true;
       }
       //normal selection of the item
        switch (item.getItemId()) {
            case R.id.nav_search:
                // Launch the search cityactivity
                launchCityActivity(false);
                return true;
            case R.id.nav_share:
                browserIntent = new Intent(android.content.Intent.ACTION_SEND);
                browserIntent.setType("text/plain");
                browserIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.sharing_app_message_subject));
                browserIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.sharing_app_message));
                startActivity(Intent.createChooser(browserIntent, getResources().getString(R.string.sharing_app_message_subject)));
                return true;
            case R.id.nav_gplay:
                browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=android2ee"));
                startActivity(browserIntent);
                return true;
            case R.id.action_show_android2ee:
                // open browser on the web pages
                browserIntent = new Intent("android.intent.action.VIEW");
                // open the browser on android2ee
                browserIntent.setData(Uri.parse(getResources().getString(R.string.android2ee_url)));
                startActivity(browserIntent);
                return true;
            case R.id.action_medium_android2ee:
                // open browser on the web pages
                browserIntent = new Intent("android.intent.action.VIEW");
                // open the browser on android2ee
                browserIntent.setData(Uri.parse(getResources().getString(R.string.android2ee_medium_url)));
                startActivity(browserIntent);
                return true;
            case R.id.action_dvp_android2ee:
                // open browser on the web pages
                browserIntent = new Intent("android.intent.action.VIEW");
                browserIntent.setData(Uri.parse(getResources().getString(R.string.mse_dvp_url)));
                startActivity(browserIntent);
                return true;
            case R.id.action_youtube_android2ee:
                // open browser on the web pages
                browserIntent = new Intent("android.intent.action.VIEW");
                browserIntent.setData(Uri.parse(getResources().getString(R.string.mse_youtube_url)));
                startActivity(browserIntent);
                return true;
            case R.id.action_show_mathias:
                // open browser on the web pages
                browserIntent = new Intent("android.intent.action.VIEW");
                browserIntent.setData(Uri.parse(getResources().getString(R.string.mse_linkedin_url)));
                startActivity(browserIntent);
                return true;
            case R.id.action_mail_mathias:
                // load string for email:
                String subject = getResources().getString(R.string.mail_subject);
                String body = getResources().getString(R.string.mail_body);
                // send an email
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { getResources().getString(R.string.mse_email) });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                return true;
            //Bottom Navigation items
            case R.id.menu_now:
                // Launch the search cityactivity
                launchNowActivity();
                return true;
            case R.id.menu_wotf:
                //Delete the current city
                launchWeatherOfTheDayActivity();
                return true;
            case R.id.menu_3h:
                launch3HActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /***********************************************************
    * Navigation Methods
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
    private void launchWeatherOfTheDayActivity() {
        MyLog.e(TAG,"launchWeatherOfTheDayActivity");
        //launch the main activity
        Intent launchMainActivity = new Intent(this, WotdActivity.class);
        startActivity(launchMainActivity);
        finish();
    }
    private void launch3HActivity() {
        MyLog.e(TAG,"launch3HActivity");
        //launch the main activity
        Intent launchMainActivity = new Intent(this, ForecastWeatherActivity.class);
        startActivity(launchMainActivity);
        finish();
    }
    private void launchNowActivity() {
        MyLog.e(TAG,"launchNowActivity");
        //launch the main activity
        Intent launchMainActivity = new Intent(this, CurrentWeatherActivity.class);
        startActivity(launchMainActivity);
        finish();
    }
}
