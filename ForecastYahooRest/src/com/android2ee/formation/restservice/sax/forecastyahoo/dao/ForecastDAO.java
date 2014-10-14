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
package com.android2ee.formation.restservice.sax.forecastyahoo.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.dao.DBOpenHelper.Constants;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManaged;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.YahooForcast;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.YahooForecastComparator;

public class ForecastDAO {
	/** * The database */
	private SQLiteDatabase db;
	/** * The database creator and updater helper */
	DBOpenHelper dbOpenHelper;
	/**
	 * The date parser used to store calendar object in the database
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	/** Constructor. */
	public ForecastDAO() {
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
			ExceptionManager.manage(new ExceptionManaged(this.getClass(),R.string.exc_database_cannot_open, ex));
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
	 * Load All the data from the DataBase
	 * @return
	 */
	public ArrayList<YahooForcast> loadAll() {
		openDB();
		// Using man made query
		// The projection define what are the column you want to retrieve
		String[] projections = new String[] { 
				Constants.KEY_COL_ID,
				Constants.KEY_COL_DATE,
				Constants.KEY_COL_TENDANCE,
				Constants.KEY_COL_CODE_IMAGE,
				Constants.KEY_COL_MIN_TEMP,
				Constants.KEY_COL_MAX_TEMP,
				Constants.KEY_COL_TEMP };
		// The order by clause (column name followed by Asc or DESC)
		String orderBy = Constants.KEY_COL_ID + "  ASC";
		// Maximal size of the results list
		Cursor cursor = db.query(Constants.MY_TABLE, projections, null, null, null, null, orderBy,
				null);
		return buildResult(cursor);
	}
	/**
	 * Rebuild the YahooForecast objects stored in the cursor
	 * @param cursor The cursor that contains the data
	 * @return The list of forecasts
	 */
	private ArrayList<YahooForcast> buildResult(Cursor cursor) {
		ArrayList<YahooForcast> forcasts = new ArrayList<YahooForcast>();
		
		// then browse the result:
		if (cursor.moveToFirst()) {
			// The elements to retrieve
			String date;
			Calendar dateCal;
			String tendance;
			String codeImage;
			int minTemp;
			int maxTemp;
			int temp;
			// The associated index within the cursor
			int index_date= cursor.getColumnIndex(Constants.KEY_COL_DATE);
			int index_tendance= cursor.getColumnIndex(Constants.KEY_COL_TENDANCE);
			int index_codeImage= cursor.getColumnIndex(Constants.KEY_COL_CODE_IMAGE);
			int index_minTemp= cursor.getColumnIndex(Constants.KEY_COL_MIN_TEMP);
			int index_maxTemp= cursor.getColumnIndex(Constants.KEY_COL_MAX_TEMP);
			int index_temp= cursor.getColumnIndex(Constants.KEY_COL_TEMP);
			//The current Forecast
			YahooForcast currentForecast;
			do {
				//retrieve value
				date = cursor.getString(index_date);
				tendance = cursor.getString(index_tendance);
				codeImage = cursor.getString(index_codeImage);
				minTemp = cursor.getInt(index_minTemp);
				maxTemp = cursor.getInt(index_maxTemp);
				temp = cursor.getInt(index_temp);
				dateCal=Calendar.getInstance();
				try {
					dateCal.setTime(sdf.parse(date));
				} catch (ParseException e) {
					ExceptionManager.manage(new ExceptionManaged(this.getClass(),R.string.exc_date_parsing, e));
				}
				//build forecast
				currentForecast=new YahooForcast(dateCal, tendance, codeImage, minTemp, maxTemp, temp);
				//add it to the list
				forcasts.add(currentForecast);				
			} while (cursor.moveToNext());
		}
		Log.e("ForecastDao", "loadAll and built result found "+forcasts.size()+" items number");
		// And never ever forget to close the cursor when you have finished with it:
		cursor.close();
		closeDB();
		//and return result
		return forcasts;
	}
	
	/**
	 * Save the list of forecast
	 * @param forcasts The forcasts to save
	 * @return true if the save was ok, else return false
	 */
	public boolean saveAll(ArrayList<YahooForcast> forcasts) {
		boolean insertionOk=true;
		Collections.sort(forcasts, new YahooForecastComparator());
		//As they are sorted I can store them as this
		openDB();
		//first flush data
		db.delete(Constants.MY_TABLE, null, null);
		//then save all
		ContentValues contentValues = new ContentValues();
		long rowId;
		for(YahooForcast forcast:forcasts) {
			contentValues.put(Constants.KEY_COL_DATE, sdf.format(forcast.getDate().getTime()) );
			contentValues.put(Constants.KEY_COL_TENDANCE, forcast.getTendance());
			contentValues.put(Constants.KEY_COL_CODE_IMAGE, forcast.getCodeImage());
			contentValues.put(Constants.KEY_COL_MIN_TEMP, forcast.getTempMin());
			contentValues.put(Constants.KEY_COL_MAX_TEMP, forcast.getTempMax());
			contentValues.put(Constants.KEY_COL_TEMP, forcast.getTemp());
			// Insert the line in the database
			rowId = db.insert(Constants.MY_TABLE, null, contentValues);
			if(rowId<0) {
				insertionOk=false;
				try {
					throw new SQLiteException();
				}catch(SQLiteException e) {
					ExceptionManager.manage(new ExceptionManaged(this.getClass(),R.string.exc_sql_insert, e));
				}
			}
		}
		
		
		closeDB();
		return insertionOk;
	}
	
	
}