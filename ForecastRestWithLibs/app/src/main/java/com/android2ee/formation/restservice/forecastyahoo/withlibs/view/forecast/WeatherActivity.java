package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TabLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.injector.PresenterInjector;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.test.EspressoIdlingResource;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherPresenter;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity.CityActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.viewpager.MyPagerAdapter;


public class WeatherActivity extends MotherActivity implements WeatherViewIntf{
    private static final String TAG = "WeatherActivity";
    /***********************************************************
     *  Presenter
     **********************************************************/
    /**
     * The Presenter associated with that view
     */
     WeatherPresenterIntf presenter=null;
    /***********************************************************
     * Attributes
     **********************************************************/
    TextView txvText;
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * The TabLayout itself :)
     */
    TabLayout tabLayout;
    /**
     * The page Adapter : Manage the list of views (in fact here, it's fragments)
     * And send them to the ViewPager
     */
    private MyPagerAdapter pagerAdapter;
    /**
     * The ViewPager is a ViewGroup that manage the swipe from left to right to left
     * Like a listView with a gesture listener...
     */
    private ViewPager viewPager;
    /**
     * The Alertdialog to confirm deletion
     */
    private DeleteAlert deleteDialog;

    /***********************************************************
     * Managing LifeCycle
     **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        presenter= PresenterInjector.getWeatherPresenter(this);
        setContentView(R.layout.activity_main);
        txvText= (TextView) findViewById(R.id.txvTest);
        //Find the Tab Layout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Define its gravity and its mode
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //Define the color to use (depending on the state a different color should be disaplyed)
        //Works only if done before adding tabs
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab_selector_color));
        //instanciate the PageAdapter
        pagerAdapter=new MyPagerAdapter(this,presenter);
        //Find the viewPager
        viewPager = (ViewPager) super.findViewById(R.id.viewpager);
        // Affectation de l'adapter au ViewPager
        viewPager.setAdapter(pagerAdapter);
        viewPager.setClipToPadding(true);
        //AND CLUE TABLAYOUT AND VIEWPAGER
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //presenter have to be instanciate in onStart (else a null pointer exception explose in your face at the first launch because
        //you come back from CityActivity without going through onCreate :'(
//        if(presenter==null){
//            presenter=new WeatherPresenter(this);
//        }
        Log.e(TAG, "onStart() called with: " + "");
        presenter.loadCities();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() called with: " + "");
        //invalidate the DialogFragment to avoid stupid memory leak
        if (deleteDialog != null) {
            if (deleteDialog.isVisible()) {
                deleteDialog.dismiss();
            }
            deleteDialog = null;
        }
    }

    /***********************************************************
     * Managing Presenters
     **********************************************************/
    @Override
    public MotherPresenter getPresenter() {
        return (MotherPresenter) presenter;
    }

    /***********************************************************
     *  Managing data
     **********************************************************/

    /**
     * The cities have been loaded, you have to update your view
     */
    @Override
    public void citiesLoaded() {
        //update the adapter
        pagerAdapter.notifyRebuildAll();
        //and clue again with the Tablayout
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void cityDeleted(City deletedCity) {
        //update the adapter
        pagerAdapter.notifyRebuildAll();
        //and clue again with the Tablayout
        tabLayout.setupWithViewPager(viewPager);
    }
/******************************************************************************************/
    /** Managing Menu **************************************************************************/
    /******************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Launch the search cityactivity
                launchCityActivity(false);
                return true;
            case R.id.action_delete:
                //Delete the current city
                onDeleteCurrentCity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /******************************************************************************************/
    /** Managing city deletion **************************************************************************/
    /******************************************************************************************/
    /**
     * The method called when the delete action is launched by the user
     * either through the menu or the ime of the editText
     */
    private void onDeleteCurrentCity(){
        FragmentManager fm = getSupportFragmentManager();
         deleteDialog=(DeleteAlert)fm.findFragmentByTag("deleteDialog");
        if(deleteDialog==null){
            deleteDialog=new DeleteAlert();
        }
        deleteDialog.show(getSupportFragmentManager(), "deleteDialog");
    }
    /**
     * Delete the current city and then do what is needed in term of navigation
     */
    private void deleteCurrentCity(){
        City cityToDelete=presenter.getCities().get(viewPager.getCurrentItem());
        presenter.deleteCity(cityToDelete);
    }
    /******************************************************************************************/
    /** Managing navigation **************************************************************************/
    /******************************************************************************************/

    /**
     * When there is no city in the Database, you have to launch the search activity from here
     */
    @Override
    public void launchCityActivity(boolean finish){
        // then launch the CityActivity to select a city
        Intent launchCityActivity = new Intent(this, CityActivity.class);
        startActivity(launchCityActivity);
//        if(finish){
//            finish();
//        }
    }
    /******************************************************************************************/
    /** Managing AlertDialog **************************************************************************/
    /******************************************************************************************/

    /**
     * The AlertDialog that displays the message are you sure you want to delete
     */
    @SuppressLint("ValidFragment")
    public class DeleteAlert extends DialogFragment {

        public DeleteAlert(){
            //empty constructor is mandatory
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("");
            builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteCurrentCity();
                }
            });
            builder.setNegativeButton(R.string.btn_no, null);
            return builder.create();
        }

        /**
         * Called when the fragment is visible to the user and actively running.
         * This is generally
         * tied to {@link android.app.Activity#onResume() Activity.onResume} of the containing
         * Activity's lifecycle.
         */
        @Override
        public void onResume() {
            super.onResume();
            City cityToDelete=presenter.getCities().get(viewPager.getCurrentItem());
            ((AlertDialog)getDialog()).setMessage(getString(R.string.alertdialog_message,cityToDelete.getName()));
        }
    }

    /***********************************************************
     *  Testing Only
     **********************************************************/
    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
