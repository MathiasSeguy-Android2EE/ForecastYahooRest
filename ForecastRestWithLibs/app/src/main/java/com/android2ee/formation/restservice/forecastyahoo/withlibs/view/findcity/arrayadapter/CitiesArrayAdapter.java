/**<ul>
 * <li>ForecastYahooRest</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.view.city.arrayadapter</li>
 * <li>17 nov. 2014</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity.arrayadapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManaged;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManager;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.City;

import java.util.ArrayList;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class CitiesArrayAdapter extends ArrayAdapter<City> {
	private static final String TAG = "CitiesArrayAdapter";
	/**
	 * *The layout inflater to build the view
	 */
	LayoutInflater inflater;
	/**
	 * Context 
	 */
	Context ctx;
	/**
	 * The Earth icon to display googleMap
	 */
	Drawable earth;
	/**
	 *
	 */
	Boolean postJellyBean;
	/**
	 *
	 */
	ArrayList<City> list;
	/**
	 * @param context
	 * @param list
	 */
	public CitiesArrayAdapter(Context context, ArrayList<City> list) {
		super(context, R.layout.item_city, list);
		inflater = LayoutInflater.from(context);
		earth = ContextCompat.getDrawable(context, R.drawable.ic_googlemap);
		ctx=context;
        if(ctx.getResources().getBoolean(R.bool.postJB)){
            postJellyBean=true;
        }else{
            postJellyBean=false;
        }
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		Log.e(TAG, "notifyDataSetChanged() called with: ");
	}
/******************************************************************************************/
	/** Managing View **************************************************************************/
	/******************************************************************************************/
	private static City city;
	private static View rowView;
	private static ViewHolder viewHolder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		rowView = convertView;
		city = getItem(position);
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.item_city, parent, false);
			viewHolder = new ViewHolder(rowView);
			rowView.setTag(viewHolder);			
		}
		viewHolder = (ViewHolder) rowView.getTag();
		viewHolder.getTxvCityName().setText(city.getName());
		viewHolder.getTxvCityType().setText("Not available anymore");
//		if(city.getEphemeris()!=null){
//			viewHolder.getTxvCountry().setText(city.getEphemeris().getCountry());
//		} else {
//			viewHolder.getTxvCountry().setText("Not available anymore");
//		}
        if(postJellyBean){
            viewHolder.getImvGoogleMap().setBackground(earth);
        }else{
            viewHolder.getImvGoogleMap().setBackgroundDrawable(earth);
        }
		viewHolder.setLatLon(city.getCoord().getLat(), city.getCoord().getLon());
		return rowView;
	}


	/******************************************************************************************/
	/** View Holder **************************************************************************/
	/******************************************************************************************/

	private class ViewHolder {
		TextView txvCityType = null;
		TextView txvCityName = null;
		TextView txvCountry = null;
		ImageView imvGoogleMap = null;
		Uri uri;
		View rowView;

		/**
		 * @param rowView
		 */
		public ViewHolder(View rowView) {
			super();
			this.rowView = rowView;
			imvGoogleMap = (ImageView) rowView.findViewById(R.id.imvGoogleMap);
			imvGoogleMap.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					launchGoogleEarth(v) ;
				}
			});
		}

		/**
		 * @return the txvCityType
		 */
		public final TextView getTxvCityType() {
			if (txvCityType == null) {
				txvCityType = (TextView) rowView.findViewById(R.id.txvCityType);
			}
			return txvCityType;
		}

		/**
		 * @return the txvCityName
		 */
		public final TextView getTxvCityName() {
			if (txvCityName == null) {
				txvCityName = (TextView) rowView.findViewById(R.id.txvCityName);
			}
			return txvCityName;
		}

		/**
		 * @return the txvCountry
		 */
		public final TextView getTxvCountry() {
			if (txvCountry == null) {
				txvCountry = (TextView) rowView.findViewById(R.id.txvCountry);
			}
			return txvCountry;
		}

		/**
		 * @return the imvGoogleMap
		 */
		public final ImageView getImvGoogleMap() {
			return imvGoogleMap;
		}

		/**
		 * Launch GoogleMap using an Intent
		 */
		private void launchGoogleEarth(View v) {
			try {
				ctx.startActivity(new Intent(Intent.ACTION_VIEW,uri));
			}catch (ActivityNotFoundException exc) {
				ExceptionManager.manage(new ExceptionManaged(getClass(), R.string.exc_no_map_found, exc));
			}
		}
		/**
		 * Define the LatLong to use when launching GoogleMap
		 * @param lat latitude
		 * @param longi longitude
		 */
		public void setLatLon(float lat, float longi) {
			uri= Uri.parse("geo:" + lat + "," + longi);
		}
	}
}
