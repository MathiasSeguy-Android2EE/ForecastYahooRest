/**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong> with the smart contribution of <strong>Julien PAPUT</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ****************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br>
 * </br>
 * For any information (Advice, Expertise, J2EE or Android Training, Rates, Business):</br>
 * <em>mathias.seguy.it@gmail.com</em></br>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Séguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br>
 * </br>
 * Pour tous renseignements (Conseil, Expertise, Formations J2EE ou Android, Prestations, Forfaits):</br>
 * <em>mathias.seguy.it@gmail.com</em></br>
 * *****************************************************************************************************************</br>
 * Merci à vous d'avoir confiance en Android2EE les Ebooks de programmation Android.
 * N'hésitez pas à nous suivre sur twitter: http://fr.twitter.com/#!/android2ee
 * N'hésitez pas à suivre le blog Android2ee sur Developpez.com : http://blog.developpez.com/android2ee-mathias-seguy/
 * *****************************************************************************************************************</br>
 * com.android2ee.android.tuto</br>
 * 25 mars 2011</br>
 */
package com.android2ee.formation.restservice.sax.forecastyahoo.dao.city;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.dao.DBOpenHelper;
import com.android2ee.formation.restservice.sax.forecastyahoo.dao.DBOpenHelper.Constants;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManaged;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.City;

public class CityDAO {
	/** * The database */
	private SQLiteDatabase db;
	/** * The database creator and updater helper */
	DBOpenHelper dbOpenHelper;

	/** Constructor. */
	public CityDAO() {
		// Create or retrieve the database
		dbOpenHelper = new DBOpenHelper(MyApplication.instance, Constants.DATABASE_NAME, null/* CursorFactory */,
				Constants.DATABASE_VERSION);
	}

	/******************************************************************************************/
	/** Database open/close operations *********************************/
	/******************************************************************************************/

	/**
	 * * Open the database*
	 * 
	 * @throws SQLiteException
	 */
	public void openDB() throws SQLiteException {
		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_database_cannot_open, ex));
		}
	}

	/** *Close Database */
	public void closeDB() {
		db.close();
	}

	/******************************************************************************************/
	/** Managing Records **************************************************************************/
	/******************************************************************************************/
	/**
	 * Load a city according to its woeid
	 * 
	 * @return
	 */
	public City loadCityFromWoeid(String woeid) {
		openDB();
		Log.e("CitiesDao", "loadCityFromWoeid called");
		// Using man made query
		// The projection define what are the column you want to retrieve
		String[] projections = new String[] { Constants.KEY_CITY_COL_ID, Constants.KEY_CITY_COL_NAME,
				Constants.KEY_CITY_COL_WOEID, Constants.KEY_CITY_COL_TYPE, Constants.KEY_CITY_COL_COUNTRY,
				Constants.KEY_CITY_COL_LATITUDE, Constants.KEY_CITY_COL_LONGITUDE };
		// The order by clause (column name followed by Asc or DESC)
		String orderBy = Constants.KEY_CITY_COL_NAME + "  ASC";
		// Maximal size of the results list
		String where = Constants.KEY_CITY_COL_WOEID + "=?";
		String[] whereParams = new String[] { woeid };
		Cursor cursor = db.query(Constants.MY_CITY_TABLE, projections, where, whereParams, null, null, orderBy, null);
		Log.e("CitiesDao", "loadCityFromWoeid and built result found " + cursor.getCount());
		return buildCity(cursor);
	}

	/**
	 * Rebuild the YahooForecast objects stored in the cursor
	 * 
	 * @param cursor
	 *            The cursor that contains the data
	 * @return The list of forecasts
	 */
	private City buildCity(Cursor cursor) {
		// The current Forecast
		City currentCity = null;
		// then browse the result:
		if (cursor.moveToFirst()) {
			// The elements to retrieve
			String name;
			String woeid;
			String placeType;
			String counrty;
			String latitude, longitude;
			// The associated index within the cursor
			int index_name = cursor.getColumnIndex(Constants.KEY_CITY_COL_NAME);
			int index_woeid = cursor.getColumnIndex(Constants.KEY_CITY_COL_WOEID);
			int index_placeType = cursor.getColumnIndex(Constants.KEY_CITY_COL_TYPE);
			int index_country = cursor.getColumnIndex(Constants.KEY_CITY_COL_COUNTRY);
			int index_latitude = cursor.getColumnIndex(Constants.KEY_CITY_COL_LATITUDE);
			int index_longitude = cursor.getColumnIndex(Constants.KEY_CITY_COL_LONGITUDE);

			// retrieve value
			name = cursor.getString(index_name);
			woeid = cursor.getString(index_woeid);
			placeType = cursor.getString(index_placeType);
			counrty = cursor.getString(index_country);
			latitude = cursor.getString(index_latitude);
			longitude = cursor.getString(index_longitude);
			// build city
			currentCity = new City(name, woeid, placeType, counrty, latitude, longitude);
			// add it to the list
		}
		// And never ever forget to close the cursor when you have finished with it:
		cursor.close();
		closeDB();
		// and return result
		return currentCity;
	}

	/**
	 * Load All the data from the DataBase
	 * 
	 * @return
	 */
	public ArrayList<City> loadAll() {
		openDB();
		// Using man made query
		// The projection define what are the column you want to retrieve
		String[] projections = new String[] { Constants.KEY_CITY_COL_ID, Constants.KEY_CITY_COL_NAME,
				Constants.KEY_CITY_COL_WOEID, Constants.KEY_CITY_COL_TYPE, Constants.KEY_CITY_COL_COUNTRY,
				Constants.KEY_CITY_COL_LATITUDE, Constants.KEY_CITY_COL_LONGITUDE };
		// The order by clause (column name followed by Asc or DESC)
		String orderBy = Constants.KEY_CITY_COL_NAME + "  ASC";
		// Maximal size of the results list
		Cursor cursor = db.query(Constants.MY_CITY_TABLE, projections, null, null, null, null, orderBy, null);
		return buildResult(cursor);
	}

	/**
	 * Rebuild the YahooForecast objects stored in the cursor
	 * 
	 * @param cursor
	 *            The cursor that contains the data
	 * @return The list of forecasts
	 */
	private ArrayList<City> buildResult(Cursor cursor) {
		ArrayList<City> cities = new ArrayList<City>();

		// then browse the result:
		if (cursor.moveToFirst()) {
			// The elements to retrieve
			String name;
			String woeid;
			String placeType;
			String counrty;
			String latitude, longitude;
			// The associated index within the cursor
			int index_name = cursor.getColumnIndex(Constants.KEY_CITY_COL_NAME);
			int index_woeid = cursor.getColumnIndex(Constants.KEY_CITY_COL_WOEID);
			int index_placeType = cursor.getColumnIndex(Constants.KEY_CITY_COL_TYPE);
			int index_country = cursor.getColumnIndex(Constants.KEY_CITY_COL_COUNTRY);
			int index_latitude = cursor.getColumnIndex(Constants.KEY_CITY_COL_LATITUDE);
			int index_longitude = cursor.getColumnIndex(Constants.KEY_CITY_COL_LONGITUDE);
			// The current Forecast
			City currentCity;
			do {
				// retrieve value
				name = cursor.getString(index_name);
				woeid = cursor.getString(index_woeid);
				placeType = cursor.getString(index_placeType);
				counrty = cursor.getString(index_country);
				latitude = cursor.getString(index_latitude);
				longitude = cursor.getString(index_longitude);
				// build city
				currentCity = new City(name, woeid, placeType, counrty, latitude, longitude);
				// add it to the list
				cities.add(currentCity);
			} while (cursor.moveToNext());
		}
		Log.e("CitiesDao", "loadAll and built result found " + cities.size() + " items number");
		// And never ever forget to close the cursor when you have finished with it:
		cursor.close();
		closeDB();
		// and return result
		return cities;
	}

	/**
	 * Save the list of forecast
	 * 
	 * @param forcasts
	 *            The forcasts to save
	 * @return true if the save was ok, else return false
	 */
	public boolean add(City city) {
		boolean insertionOk = true;
		if (loadCityFromWoeid(city.getWoeid()) == null) {
			// As they are sorted I can store them as this
			openDB();
			// then save all
			ContentValues contentValues = new ContentValues();
			long rowId;
			contentValues.put(Constants.KEY_CITY_COL_NAME, city.getName());
			contentValues.put(Constants.KEY_CITY_COL_WOEID, city.getWoeid());
			contentValues.put(Constants.KEY_CITY_COL_TYPE, city.getPlaceType());
			contentValues.put(Constants.KEY_CITY_COL_COUNTRY, city.getCountry());
			contentValues.put(Constants.KEY_CITY_COL_LATITUDE, city.getLatitude());
			contentValues.put(Constants.KEY_CITY_COL_LONGITUDE, city.getLongitude());
			// Insert the line in the database
			rowId = db.insert(Constants.MY_CITY_TABLE, null, contentValues);
			if (rowId < 0) {
				insertionOk = false;
				try {
					throw new SQLiteException();
				} catch (SQLiteException e) {
					ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_sql_insert, e));
				}
			}

			closeDB();
		}else {
			ExceptionManager.displayAnError(MyApplication.instance.getString(R.string.err_adding_city_already_exists));
		}
		return insertionOk;
	}

	/**
	 * Delete a Record
	 * 
	 * @param rowId
	 */
	public boolean delete(City city) {

        try {
            openDB();
            Log.e("CityDAO", "delete : " + city + " woeid " + city.getWoeid());
            int numberOfUpdatedElements = db.delete(DBOpenHelper.Constants.MY_CITY_TABLE,
                    DBOpenHelper.Constants.KEY_CITY_COL_WOEID + "=" + city.getWoeid(), null);
    closeDB();
            Log.e("CityDAO", "delete : number of deleted element " + numberOfUpdatedElements);
            if (numberOfUpdatedElements == 1) {
                return true;
            } else {
                return false;
            }
        }catch(Exception e){

            Log.e("CityDAO", "delete : and the exception is :",e);
            return false;
        }
	}

}