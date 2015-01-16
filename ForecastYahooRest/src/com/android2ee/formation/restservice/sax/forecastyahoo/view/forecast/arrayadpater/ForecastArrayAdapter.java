/**<ul>
 * <li>ForecastRestYahooSax</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.view.forecast.arrayadpater</li>
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
package com.android2ee.formation.restservice.sax.forecastyahoo.view.forecast.arrayadpater;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.util.SparseArrayCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.YahooForcast;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to display the forecast in the listView
 */
public class ForecastArrayAdapter extends ArrayAdapter<YahooForcast> {
	
	/**
	 * Handler to launch the animation runnable
	 */
	Handler handlerForAnimation;
	/**
	 * An animation by item is needed
	 * You need one animation by item
	 * else the animation is relaunch for every item (one animpation for all)
	 */
	SparseArrayCompat<Animation> sparseAnimation;
	/**
	 * An animation by item is needed
	 * The same, you need one runnable by item
	 */
	SparseArrayCompat<MyRunnable> sparseRunnable;

	/**
	 * To detect the first launch
	 */
	int firstLaunch = 0;
	/**
	 * The layout inflater
	 */
	LayoutInflater inflater;
	/**
	 * The Context
	 */
	Context ctx;
	/**
	 * To know if the device is postJellyBean or not
	 */
	boolean postJB;

	/**
	 * @param context
	 * @param resource
	 */
	public ForecastArrayAdapter(Context context, List<YahooForcast> forecast) {
		super(context, R.layout.item_forecast, forecast);
		inflater = LayoutInflater.from(context);
		ctx = context;
		postJB = context.getResources().getBoolean(R.bool.postJB);
		//instantiate the sprase array
		sparseAnimation=new SparseArrayCompat<Animation>(getCount());
		sparseRunnable=new SparseArrayCompat<MyRunnable>(getCount());
		//instantiate the sparse array
		handlerForAnimation = new Handler();
		for(int i=0;i<getCount();i++) {
			sparseAnimation.put(i, AnimationUtils.loadAnimation(context, R.anim.anim_item_updated));
			sparseRunnable.put(i, new MyRunnable( i));
		}

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
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		rowView = convertView;
		forcast = getItem(position);
		if (rowView == null) {
			// always add the layout, the parent and false
			rowView = inflater.inflate(R.layout.item_forecast, parent, false);
			ViewHolder vh = new ViewHolder(rowView);
			rowView.setTag(vh);
		}
		viewHolder = (ViewHolder) rowView.getTag();
		if (postJB) {
			viewHolder.getImvIcon().setBackground(forcast.getImage());
		} else {
			viewHolder.getImvIcon().setBackgroundDrawable(forcast.getImage());
		}
		if (forcast.getDate() != null) {
			viewHolder.getTxvDate().setText(DateFormat.format("E dd MMM", forcast.getDate()));
		} else {
			viewHolder.getTxvDate().setText("unknown");
		}
		viewHolder.getTxvTendance().setText(forcast.getTendance());
		if (forcast.getTempMax() != -1000) {
			viewHolder.getTxvMax().setVisibility(View.VISIBLE);
			viewHolder.getTxvMin().setVisibility(View.VISIBLE);
			viewHolder.getTxvMax().setText(ctx.getString(R.string.max, forcast.getTempMax()));
			viewHolder.getTxvMin().setText(ctx.getString(R.string.min, forcast.getTempMin()));
		} else {
			viewHolder.getTxvMax().setVisibility(View.GONE);
			viewHolder.getTxvMin().setVisibility(View.GONE);
		}
		if (forcast.getTemp() != -1000) {
			viewHolder.getTxvCurrent().setVisibility(View.VISIBLE);
			viewHolder.getTxvCurrent().setText(ctx.getString(R.string.temp, forcast.getTemp()));
		} else {
			viewHolder.getTxvCurrent().setVisibility(View.GONE);
		}
		// launch animations to show the update to the user (not the first time but only when refreshing)
		if (firstLaunch>=2) {
			//hide the item
			viewHolder.getLinRoot().setVisibility(View.GONE);
			//find the runnable and set the vierwHolder
			sparseRunnable.get(position).setVh(viewHolder);
			//then launch the runnable in 300ms *pos
			handlerForAnimation.postDelayed(sparseRunnable.get(position), 300 * position);			
		}
		return rowView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#notifyDataSetChanged()
	 */
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		firstLaunch ++;
	}

	/******************************************************************************************/
	/** Runnable for animation **************************************************************************/
	/******************************************************************************************/
	public class MyRunnable implements Runnable {
		/**
		 * The viewHolder that contains the view to animate
		 */
		private ViewHolder vh;
		/**
		 * The position of the item
		 */
		private int position;

		public MyRunnable( int pos) {
			this.position = pos;
		}

		/**
		 * @param vh the vh to set
		 */
		public final void setVh(ViewHolder vh) {
			if(this.vh!=null){
                //it means an already runnable is associated with this item
                //we need to remove it (else it gonna run the animation twice
                //and it's strange for the user)
                handlerForAnimation.removeCallbacks(sparseRunnable.get(position));
            }
            this.vh = vh;
		}

		public void run() {
			//first set the linearlayout visible
			vh.getLinRoot().setVisibility(View.VISIBLE);
			//find the animation to launch
			Animation animationUpdate=sparseAnimation.get(position);
			//run it
			vh.getImvIcon().startAnimation(animationUpdate);
			vh.getTxvCurrent().startAnimation(animationUpdate);
			vh.getTxvDate().startAnimation(animationUpdate);
			vh.getTxvMax().startAnimation(animationUpdate);
			vh.getTxvMin().startAnimation(animationUpdate);
			vh.getTxvTendance().startAnimation(animationUpdate);
		}
	}

	/******************************************************************************************/
	/** The ViewHolder pattern **************************************************************************/
	/******************************************************************************************/

	private class ViewHolder {
		View view;
		LinearLayout linRoot;
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
			if (null == txvMax) {
				txvMax = (TextView) view.findViewById(R.id.txv_max);
			}
			return txvMax;
		}

		/**
		 * @return the linRoot
		 */
		public final LinearLayout getLinRoot() {
			if (null == linRoot) {
				linRoot = (LinearLayout) view.findViewById(R.id.lay_item);
			}
			return linRoot;
		}

	}

}
