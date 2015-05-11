package com.android2ee.formation.restservice.sax.forecastyahoo.view.forecast;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android2ee.formation.restservice.sax.forecastyahoo.MotherActivity;
import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.CitiesCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.CityCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.forecast.ForecastCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.interfaces.ConnectivityIsBackIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.City;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.YahooForcast;
import com.android2ee.formation.restservice.sax.forecastyahoo.view.city.CityActivity;
import com.android2ee.formation.restservice.sax.forecastyahoo.view.forecast.arrayadpater.ForecastArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MotherActivity implements ConnectivityIsBackIntf,
		SwipeRefreshLayout.OnRefreshListener, OnNavigationListener, ForecastCallBack, CityCallBack, CitiesCallBack {
	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/

	/**
	 * The ArrayAdapter to use
	 */
	private ForecastArrayAdapter arrayAdapter;
	/**
	 * The ListView
	 */
	private ListView listView = null;
	/**
	 * The ImageView that displays the yahooLogo
	 */
	private ImageView imvYahooLogo;
	/**
	 * The last update textview displaying when the data was last updated
	 */
	private TextView txvLastUpdate;
	/**
	 * The SwipeLayout
	 */
	private SwipeRefreshLayout swipeLayout = null;
	/**
	 * The displayed object
	 */
	private ArrayList<YahooForcast> forecasts;
	/**
	 * Data are loaded
	 */
	private boolean dataLoaded = false;
    /**
     * The NavigationItem is initialised, when you initialized it the first time
     * Don't load data, because the user doens't change the city, and the dataLoading is done by onResume
     */
    private boolean navigationItemInitialised=false;
	/**
	 * When refreshing the data
	 */
	private boolean isRefreshing = false;
	/**
	 * The city associated with the displayed forecasts
	 */
	private City currentCity = null;

	/******************************************************************************************/
	/** Managing Life Cycle **************************************************************************/
	/******************************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		forecasts = new ArrayList<YahooForcast>();
		// add the swipe to refresh feature
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		Log.e("MainActivity", "onCreate swipeLyout " + swipeLayout);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorSchemeResources(R.color.blue_pure, R.color.blue_pure_1, R.color.blue_pure_2,
				R.color.blue_pure_3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// no need to use such a method, the serviceData is caching the data dude
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// no need to use such a method, the serviceData is caching the data dude
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.e("MainActivity", "onPause");
		MyApplication.instance.unregisterAsConnectivityBackListener(this);
        //Manage the fucking FragmentDialog inner class memory leak
        //why AlertDialog are deprecated, tell me why
        FragmentManager fm = getSupportFragmentManager();
        DeleteAlert deleteDialog=(DeleteAlert)fm.findFragmentByTag("deleteDialog");
        if(deleteDialog!=null){
            deleteDialog.dismiss();
        }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// display the connection status to the user if no connected
		isConnected = MyApplication.instance.isConnected();
        //No need to register the MotherActivity do it also.
		//MyApplication.instance.registerAsConnectivityBackListener(this);
		Log.v("MainActivity", " isConnected=" + isConnected);
		// find the city associated with the forecast

		// this case is the current case
		// go in the sharedPreference
		SharedPreferences prefs = getSharedPreferences(CityActivity.SELECTED_CITY, MODE_PRIVATE);
		String woeid = prefs.getString(CityActivity.SELECTED_CITY, null);
		if (woeid != null) {
			MyApplication.instance.getServiceManager().getCityServiceData().loadCity(this, woeid);
			Log.e("MainActivity", "onResume found woeid in sharedPrefs : " + woeid);
		}
		// else default launch the CityActivity
		else {
			//first start so launch the activity search
            launchSearchActivity();
            //and then kill yourself, because if the user press back on the
            //SearchActivity at the first the application should die
            finish();
		}
		// load all the avaiable cities and update the actionbar navigation
		MyApplication.instance.getServiceManager().getCityServiceData().getCities(this);
	}

	/******************************************************************************************/
	/** Managing the city selection using the action bar **************************************************************************/
	/******************************************************************************************/
    //Don't use the SpinnerAdapter else you won't be able to tune the style of the spinner :'(
    //	private SpinnerAdapter mSpinnerAdapter;
    private ArrayAdapter<CharSequence> mSpinnerAdapter;
    private List<String> mCitiesName;
	private List<City> mCities;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android2ee.formation.restservice.sax.forecastyahoo.service.city.CitiesCallBack#citiesLoaded
	 * (java.util.List)
	 */
	@Override
	public void citiesLoaded(List<City> cities) {
        //this method is called when all the cities are loaded from the Dao to instanciate the Spinner of the ActionBar
		int selectedElement = 0, index = 0;
		if (cities != null) {
			mCities = cities;
			mCitiesName = new ArrayList<String>();
			for (City ci : mCities) {
				mCitiesName.add(ci.getName());
				Log.e("MainActivity", "citiesLoaded callback found city from DAO : " + ci.getName());
				if (currentCity != null) {
					if (ci.getName().equals(currentCity.getName())) {
						selectedElement = index;
						Log.e("MainActivity", "citiesLoaded callback found selectedElement : " + selectedElement);
					}
					index++;
				}
			}
			initializeActionBar(selectedElement);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android2ee.formation.restservice.sax.forecastyahoo.service.city.CityCallBack#cityLoaded
	 * (com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.City)
	 */
	public void cityLoaded(City city) {
        //this method is called when we load the current city according to its woeid stored in the sharedPref
		Log.e("MainActivity", "cityLoaded callback found city from DAO " + currentCity);
		if (city != null) {
			currentCity = city;
			Log.e("MainActivity", "cityLoaded callback found city from DAO : " + currentCity.getName());
			// set the current city in the navigation
			if (mCitiesName != null && !mCitiesName.isEmpty()) {
				int selectedElement = 0;
				for (String ci : mCitiesName) {
					if (ci.equals(currentCity.getName())) {
						getSupportActionBar().setSelectedNavigationItem(selectedElement);
						Log.e("MainActivity", "cityLoaded callback found selectedElement : " + selectedElement);
					}
					selectedElement++;
				}

			}
			// then load the data
			loadForecast();
		} else {
			// what is this case ?
			// should not be reached
		}
	}

	/**
	 * Initialize the NavigationMode of the Action Bar using the list of know cities
	 */
	private void initializeActionBar(int selectedElement) {
		Log.e("MainActivity", "initializeActionBar found selectedElement : " + selectedElement);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        mSpinnerAdapter = new ArrayAdapter(this, R.layout.actionbar_spinner_dropdown_item, mCitiesName);
        mSpinnerAdapter.setDropDownViewResource(R.layout.actionbar_spinner_dropdown_item);
		getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);
		// then set it to the current city
		getSupportActionBar().setSelectedNavigationItem(selectedElement);
	}

	/**
	 * @param position
	 * @param itemId
	 * @return
	 */
	public boolean onNavigationItemSelected(int position, long itemId) {
        Log.e("MainActivity", "onNavigationItemSelected : " + position);
		currentCity = mCities.get(position);
		// then store the currentcity in the shared preference
		// set it the sharedPreference as the selected city
		SharedPreferences prefs = getSharedPreferences(CityActivity.SELECTED_CITY, MODE_PRIVATE);
		prefs.edit().putString(CityActivity.SELECTED_CITY, currentCity.getWoeid()).commit();
		prefs = null;
		// then load the data
        if(navigationItemInitialised){
            loadForecast();
        }
        navigationItemInitialised=true;
		return false;
	}

	/******************************************************************************************/
	/** Managing connectivity **************************************************************************/
	/******************************************************************************************/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android2ee.formation.restservice.sax.forecastyahoo.transverse.interfaces.
	 * ConnectivityIsBackIntf#connectivityIsBack(boolean, int)
	 */
	@Override
	public void connectivityIsBack(boolean isWifi, int telephonyType) {
		// Ok so the connectivity is back, we should load the data
		if (MyApplication.instance.isConnected()) {
			// because that means the connectivity status has changed from not connection to
			// connected
			if (!dataLoaded) {
				Log.e("MainActivity", "connectivityIsBack called asking to load data");
				MyApplication.instance.getServiceManager().getForecastServiceData()
						.getForecast(this, currentCity.getWoeid());
			}
		}
		// else do nothing data already loaded
	}

	/******************************************************************************************/
	/** Loading Forecast **************************************************************************/
	/******************************************************************************************/

	/**
	 * Ask to load the data
	 * The connection state is managed by the service, activity don't have to deal with that when
	 * requesting data
	 */
	private void loadForecast() {
		Log.e("MainActivity", "loadForecast called asking to load data");
		// Ask for the weather what ever the connection is
		MyApplication.instance.getServiceManager().getForecastServiceData().getForecast(this, currentCity.getWoeid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android2ee.formation.restservice.sax.forecastyahoo.service.ForecastCallBack#forecastLoaded
	 * (java.util.List)
	 */
	public void forecastLoaded(List<YahooForcast> forecasts) {
		// first check if the return list is null or empty
		if (forecasts == null || forecasts.size() == 0) {
			// do nothing because it only occurs at the first launch when retrieving data from the
			// database which is empty
			// ExceptionManager.displayAnError(getString(R.string.no_data_message));
		} else {
			// else copy paste the data in this.forecast
			this.forecasts.clear();
			for (YahooForcast forcast : forecasts) {
				this.forecasts.add(forcast);
			}
			// then update the view
			updateGui();
		}
		// data are refreshed
		refreshed();
	}

	/**
	 * Update the gui with the list of forecast
	 */
	private void updateGui() {
		// Instanciate the listView
		if (listView == null) {
			listView = (ListView) findViewById(R.id.myListView);
			View viewFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
			imvYahooLogo = (ImageView) viewFooter.findViewById(R.id.imv_yahoo_logo);
			imvYahooLogo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    yahooRequirement();
                }

            });
			View viewHeader = LayoutInflater.from(this).inflate(R.layout.list_header, null);
			txvLastUpdate = (TextView) viewHeader.findViewById(R.id.txv_last_update);
			txvLastUpdate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onRefresh();
					Toast.makeText(MainActivity.this, getString(R.string.txv_last_update_swipe), Toast.LENGTH_LONG)
							.show();
				}

			});
			listView.addHeaderView(viewHeader);
			listView.addFooterView(viewFooter);
			arrayAdapter = new ForecastArrayAdapter(this, this.forecasts);
			listView.setAdapter(arrayAdapter);
		}
		// Set the listView Visible
		listView.setVisibility(View.VISIBLE);
		arrayAdapter.notifyDataSetChanged();
		// update the last update textView
		SharedPreferences prefs = MyApplication.instance.getSharedPreferences(MyApplication.CONNECTIVITY_STATUS,
				Context.MODE_PRIVATE);
		txvLastUpdate.setText(getString(R.string.txv_last_update,
				prefs.getString(MyApplication.instance.getString(R.string.last_update), "null")));
		// ok data loaded
		dataLoaded = true;

	}

	/******************************************************************************************/
	/** Manage the Yahoo Requiremenets **************************************************************************/
	/******************************************************************************************/

	/**
	 * Open the YahooRequirement URL
	 */
	private void yahooRequirement() {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(getString(R.string.yahoo_requirment)));
		startActivity(i);
	}

	/******************************************************************************************/
	/** Managing SwipeToRefresh listener **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener#onRefresh()
	 */
	@Override
	public void onRefresh() {
		// call service updater
		if (!isRefreshing) {
			swipeLayout.setRefreshing(true);
			isRefreshing = true;
			if (isConnected) {
				MyApplication.instance.getServiceManager().getForecastServiceUpdater()
						.updateForecastFromServer(this, currentCity.getWoeid());
			} else {
				// no connection dude, in a way you are refreshed.
				refreshed();
			}
		}
	}

	/**
	 * To be called when the data are refreshed
	 */
	private void refreshed() {
		// You have finished to refresh
		isRefreshing = false;
		swipeLayout.setRefreshing(false);
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
			launchSearchActivity();
			return true;
       case R.id.action_delete:
                //Delete the current city
                onDeleteCurrentCity();
                return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

    /**
     * The method called when the delete action is launched by the user
     * either through the menu or the ime of the editText
     */
    private void onDeleteCurrentCity(){
        FragmentManager fm = getSupportFragmentManager();
        DeleteAlert deleteDialog=(DeleteAlert)fm.findFragmentByTag("deleteDialog");
        if(deleteDialog==null){
            deleteDialog=new DeleteAlert();
        }
        deleteDialog.show(getSupportFragmentManager(), "deleteDialog");
    }
    /**
     * Delete the current city and then do what is needed in term of navigation
     */
    private void deleteCurrentCity(){
        //delete the city
        MyApplication.instance.getServiceManager().getCityServiceData().deleteCity(currentCity);
        //find another city to put in the sharedPref or null is none
        if(mCities.size()>1){
            String newWoeid;
            //it means there is another city to load,pick one randomly
            for(City city :mCities){
                if(city.getWoeid()!=currentCity.getWoeid()){
                    newWoeid=city.getWoeid();
                    //ok we found the new city
                    //delete the city from the sharedPref
                    SharedPreferences prefs = getSharedPreferences(CityActivity.SELECTED_CITY, MODE_PRIVATE);
                    prefs.edit().putString(CityActivity.SELECTED_CITY, newWoeid).commit();
                    currentCity=null;
                    //then reload every think
                    MyApplication.instance.getServiceManager().getCityServiceData().loadCity(this, newWoeid);
                    Log.e("MainActivity", "onResume found woeid in sharedPrefs : " + newWoeid);
                    // load all the available cities and update the actionbar navigation
                    MyApplication.instance.getServiceManager().getCityServiceData().getCities(this);
                    //then break the loop
                    break;
                }
            }
        }else{
            //delete the city from the sharedPref
            SharedPreferences prefs = getSharedPreferences(CityActivity.SELECTED_CITY, MODE_PRIVATE);
            prefs.edit().putString(CityActivity.SELECTED_CITY, null).commit();
            //it means iwe delete the last city so:
            //first start so launch the activity search
            launchSearchActivity();
            //and then kill yourself, because if the user press back on the
            //SearchActivity at the first launch the application should die
            finish();
        }

    }
	/******************************************************************************************/
	/** Managing navigation **************************************************************************/
	/******************************************************************************************/

	/**
	 * Launch the SearchActivity
	 */
	private void launchSearchActivity() {
		// then launch the CityActivity to select a city
		Intent launchCityActivity = new Intent(this, CityActivity.class);
		startActivity(launchCityActivity);
	}
    /******************************************************************************************/
    /** Managing AlertDialog **************************************************************************/
    /******************************************************************************************/

    /**
     * The AlertDialog that displays the message are you sure you want to delete
     */
    @SuppressLint("ValidFragment")
    private class DeleteAlert extends DialogFragment {

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
            ((AlertDialog)getDialog()).setMessage(getString(R.string.alertdialog_message,currentCity.getName()));
        }
    }

}
