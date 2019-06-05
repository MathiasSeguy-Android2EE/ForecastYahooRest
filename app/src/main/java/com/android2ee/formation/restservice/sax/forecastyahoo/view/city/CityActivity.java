package com.android2ee.formation.restservice.sax.forecastyahoo.view.city;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android2ee.formation.restservice.sax.forecastyahoo.MotherActivity;
import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.AddedCityCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.CitiesCallBack;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.interfaces.ConnectivityIsBackIntf;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.City;
import com.android2ee.formation.restservice.sax.forecastyahoo.view.city.arrayadapter.CityArrayAdapter;
import com.android2ee.formation.restservice.sax.forecastyahoo.view.forecast.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends MotherActivity implements CitiesCallBack, ConnectivityIsBackIntf, AddedCityCallBack {
	/**
	 * For the save and restore, the key for the list of the cities
	 */
	private static final String CITY_RESULT_LIST = "CITY_RESULT_LIST";
	/**
	 * For the save and restore, the key for the editText
	 */
	private static final String EDT_SEARCHED_CITY = "EDT_SEARCHED_CITY";
	/**
	 * For the bundle in the Intent that launch the MainActivity, the key of the selected city
	 */
	public static final String SELECTED_CITY = "SELECTED_CITY";
    /**
     * To know if this Activity is launched at the first start of the application
     * Wa need to launch MainActivity when the city is selected in that case
     */
    public static final String FIRST_START = "FIRST_START";
	/******************************************************************************************/
	/** Attribute **************************************************************************/
	/******************************************************************************************/
	/**
	 * The edit text where the user give the name of the serached city
	 */
	private EditText edtSearchedCity = null;
	/**
	 * The button to launch the search of the city
	 */
	private Button btnSearch = null;
	/**
	 * The listView that displays the list of the Cities found
	 */
	private ListView lsvCityList = null;
	/**
	 * The arraylist that contains the cities found
	 */
	private ArrayList<City> cities = null;
	/**
	 * The arrayAdapter to display the city
	 */
	private CityArrayAdapter arrayAdapterCities = null;
	/**
	 * The boolean to know if the edittext is empty
	 */
	private boolean isEdtSizeEnough = true;
	/**
	 * The selected city
	 */
	private City selectedCity;

	/******************************************************************************************/
	/** Managing LifeCycle **************************************************************************/
	/******************************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		edtSearchedCity = (EditText) findViewById(R.id.edt_citySearchedName);
        //add the ime action
        edtSearchedCity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            /**
             * Called when an action is being performed.
             *
             * @param v        The view that was clicked.
             * @param actionId Identifier of the action.  This will be either the
             *                 identifier you supplied, or {@link EditorInfo#IME_NULL
             *                 EditorInfo.IME_NULL} if being called due to the enter key
             *                 being pressed.
             * @param event    If triggered by an enter key, this is the event;
             *                 otherwise, this is null.
             * @return Return true if you have consumed the action, else false.
             */
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.edt_citySearchedName_ime
                        ||actionId == EditorInfo.IME_ACTION_DONE
                        ||actionId == EditorInfo.IME_NULL) {
                    // do here your stuff
                    searchCity();
                    return true;
                }
                return false;
            }
        });
		btnSearch = (Button) findViewById(R.id.btn_search_city);
		lsvCityList = (ListView) findViewById(R.id.lsvCityList);
		cities = new ArrayList<City>();
		arrayAdapterCities = new CityArrayAdapter(this, cities);
		lsvCityList.setAdapter(arrayAdapterCities);
		// adding listeners
		btnSearch.setEnabled(false);
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchCity();

			}
		});
		lsvCityList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectCity(position);
			}
		});
		edtSearchedCity.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				setEdtEmpty(s.length());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android2ee.formation.restservice.sax.forecastyahoo.MotherActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		manageSearchButtonStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		edtSearchedCity.setText(savedInstanceState.getString(EDT_SEARCHED_CITY));
		cities.clear();
		for (Parcelable parcel : savedInstanceState.getParcelableArrayList(CITY_RESULT_LIST)) {
			cities.add((City) parcel);
		}
		arrayAdapterCities.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle,
	 * android.os.PersistableBundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(EDT_SEARCHED_CITY, edtSearchedCity.getText().toString());
		outState.putParcelableArrayList(CITY_RESULT_LIST, cities);
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
		manageSearchButtonStatus();
	}

	/**
	 * Change the status enable/disable of the btnSearch depending on the connectivity status
	 * and the value contained by the editText
	 */
	private void manageSearchButtonStatus() {

		if (isConnected && !isEdtSizeEnough) {
			// do the animation only if the button state changes from disable to enable
			if (!btnSearch.isEnabled()) {
				((TransitionDrawable) btnSearch.getBackground()).startTransition(500);
			}
		} else {
			// do the animation only if the button state changes from enable to disable
			if (btnSearch.isEnabled()) {
				((TransitionDrawable) btnSearch.getBackground()).reverseTransition(500);
			}
		}
		// and then update the button state
		btnSearch.setEnabled(isConnected && !isEdtSizeEnough);
	}

	/**
	 * Update the boolean isEdtSizeEnough and then call the manage button function
	 * 
	 * @param edtSize
	 */
	private void setEdtEmpty(int edtSize) {
		if (edtSize > 1) {
			isEdtSizeEnough = false;
		} else {
			isEdtSizeEnough = true;
		}
		// then manage the button status
		manageSearchButtonStatus();
		
	}

	/******************************************************************************************/
	/** Business Methods **************************************************************************/
	/******************************************************************************************/
	/**
	 * This method is called when you need to search for a city
	 * The connection state is managed by the service, activity don't have to deal with that when
	 * requesting data
	 */
	private void searchCity() {
		Log.e("CityActivity ", "searchCity called ");
		// Call the service
		MyApplication.instance.getServiceManager().getCitiesServicesRequester()
				.findCities(this, new City(edtSearchedCity.getText().toString(), null));
	}

	/**
	 * This method is called when a city is selected
	 * 
	 * @param position
	 *            The position of the city
	 */
	private void selectCity(int position) {
		// first find the city
		selectedCity = arrayAdapterCities.getItem(position);
		// then add it to the list of cities
		MyApplication.instance.getServiceManager().getCityServiceData().addCity(selectedCity, this);
		// the code to follow is in the cityAdded method
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android2ee.formation.restservice.sax.forecastyahoo.service.city.callback.AddedCityCallBack
	 * #cityAdded(com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.City)
	 */
	@Override
	public void cityAdded(City city) {
		Log.e("CityActivity ", "cityAdded=" + city);
		// set it the sharedPreference as the selected city
		SharedPreferences prefs = getSharedPreferences(SELECTED_CITY, MODE_PRIVATE);
        if (prefs.getString(CityActivity.SELECTED_CITY, null) == null) {
            //then first launch
            //if this is the first start you have to relaunch mainActivity
            Intent launchMainActivity = new Intent(this, MainActivity.class);
            startActivity(launchMainActivity);
        }
		prefs.edit().putString(SELECTED_CITY, selectedCity.getWoeid()).commit();
		prefs = null;
		// then finish
		finish();


	}

	/******************************************************************************************/
	/** CitiesCallBack **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android2ee.formation.restservice.sax.forecastyahoo.service.city.CitiesCallBack#forecastLoaded
	 * (java.util.List)
	 */
	@Override
	public void citiesLoaded(List<City> cities) {
		Log.e("CityActivity ", "citiesLoaded called ");
		this.cities.clear();
		// if returned cities are not null, fill the list with them
		if (cities != null) {
			for (City city : cities) {
				Log.e("CityActivity ", "Found " + city);
				this.cities.add(city);
			}
		}
		arrayAdapterCities.notifyDataSetChanged();
	}

}
