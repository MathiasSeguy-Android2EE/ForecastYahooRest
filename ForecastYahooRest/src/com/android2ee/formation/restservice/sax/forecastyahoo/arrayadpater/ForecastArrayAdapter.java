/**<ul>
 * <li>ForecastRestYahooSax</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.arrayadpater</li>
 * <li>22 nov. 2013</li>
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
 * This code is free for any usage but can't be distribute.</br>
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
package com.android2ee.formation.restservice.sax.forecastyahoo.arrayadpater;

import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.model.YahooForcast;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li></li>
 *        </ul>
 */
public class ForecastArrayAdapter extends ArrayAdapter<YahooForcast> {

	LayoutInflater inflater;
	boolean postJB;

	/**
	 * @param context
	 * @param resource
	 */
	public ForecastArrayAdapter(Context context, List<YahooForcast> forecast) {
		super(context, R.layout.item_forecast, forecast);
		inflater = LayoutInflater.from(context);
		postJB = context.getResources().getBoolean(R.bool.postJB);
	}

	/**
	 * Private static better than temp
	 */
	private static View rowView;

	/**
	 * Private static better than temp
	 */
	private static YahooForcast forcast;
	/**
	 * Private static better than temp
	 */
	private static ViewHolder viewHolder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		rowView = convertView;
		forcast = getItem(position);
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.item_forecast, null);
			ViewHolder vh = new ViewHolder(rowView);
			rowView.setTag(vh);
		}
		viewHolder = (ViewHolder) rowView.getTag();
		if (postJB) {
			viewHolder.getImvIcon().setBackground(forcast.getImage());
		} else {
			viewHolder.getImvIcon().setBackgroundDrawable(forcast.getImage());
		}
		if(forcast.getDate()!=null) {
			;
//			viewHolder.getTxvDate().setText(forcast.getDate().toString());

			viewHolder.getTxvDate().setText(DateFormat.format("E dd MMM", forcast.getDate()));
		}else {
			viewHolder.getTxvDate().setText(forcast.getStrDate());
		}
		viewHolder.getTxvTendance().setText(forcast.getTendance());
		if (forcast.getTempMax() != -1000) {
			viewHolder.getTxvMax().setVisibility(View.VISIBLE);
			viewHolder.getTxvMin().setVisibility(View.VISIBLE);
			viewHolder.getTxvMax().setText("Max :" + forcast.getTempMax());
			viewHolder.getTxvMin().setText("Min: " + forcast.getTempMin());
		} else {
			viewHolder.getTxvMax().setVisibility(View.GONE);
			viewHolder.getTxvMin().setVisibility(View.GONE);
		}
		if (forcast.getTemp() != -1000) {
			viewHolder.getTxvCurrent().setVisibility(View.VISIBLE);
			viewHolder.getTxvCurrent().setText("Temp: " + forcast.getTemp());
		} else {
			viewHolder.getTxvCurrent().setVisibility(View.GONE);
		}
		return rowView;
	}

	/******************************************************************************************/
	/** The ViewHolder pattern **************************************************************************/
	/******************************************************************************************/

	private class ViewHolder {
		View view;
		TextView txvDate;
		TextView txvTendance;
		ImageView imvIcon;
		TextView txvCurrent;
		TextView txvMin;
		TextView txvMax;

		/**
		 * @param rowview
		 */
		private ViewHolder(View rowview) {
			super();
			this.view = rowview;
		}

		/**
		 * @return the txvDate
		 */
		public final TextView getTxvDate() {
			if (null == txvDate) {
				txvDate = (TextView) view.findViewById(R.id.date);
			}
			return txvDate;
		}

		/**
		 * @return the txvTendance
		 */
		public final TextView getTxvTendance() {
			if (null == txvTendance) {
				txvTendance = (TextView) view.findViewById(R.id.txv_tendance);
			}
			return txvTendance;
		}

		/**
		 * @return the imvIcon
		 */
		public final ImageView getImvIcon() {
			if (null == imvIcon) {
				imvIcon = (ImageView) view.findViewById(R.id.icon);
			}
			return imvIcon;
		}

		/**
		 * @return the txvCurrent
		 */
		public final TextView getTxvCurrent() {
			if (null == txvCurrent) {
				txvCurrent = (TextView) view.findViewById(R.id.txv_current);
				txvCurrent.setText("Toto");
			}
			return txvCurrent;
		}

		/**
		 * @return the txvMin
		 */
		public final TextView getTxvMin() {
			if (null == txvMin) {
				txvMin = (TextView) view.findViewById(R.id.txv_min);
			}
			return txvMin;
		}

		/**
		 * @return the txvMax
		 */
		public final TextView getTxvMax() {
			Log.e("Toto", "Ok text view Max before loading");
			if (null == txvMax) {
				txvMax = (TextView) view.findViewById(R.id.txv_max);
			}
			return txvMax;
		}

	}

}
