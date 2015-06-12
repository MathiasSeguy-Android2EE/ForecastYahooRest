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

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseBooleanArray;
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

import java.util.List;

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
     * To know when the item is flipped or not
     * When flipped it show us its back side else its front side
     */
    SparseBooleanArray isFlipped;

	/**
	 * To detect the first launch
	 */
	int notifyDataSetChangedCallsNumber = 0;
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
     * To know if the device is postHoneyComb or not
     */
    boolean postHC;
    /**
     * Drawable used for the backside of the item
     */
    Drawable[] drawableBackground;


    /**
     *
     * @param context
     * @param forecast
     */
    public ForecastArrayAdapter(Context context, List<YahooForcast> forecast) {
		super(context, R.layout.item_forecast, forecast);
		inflater = LayoutInflater.from(context);
		ctx = context;
        postJB = context.getResources().getBoolean(R.bool.postJB);
        postHC = context.getResources().getBoolean(R.bool.postHC);
		//instantiate the handler
		handlerForAnimation = new Handler();
        isFlipped=new SparseBooleanArray();
        drawableBackground=new Drawable[5];
        drawableBackground[0]=context.getResources().getDrawable(R.drawable.back1);
        drawableBackground[1]=context.getResources().getDrawable(R.drawable.back2);
        drawableBackground[2]=context.getResources().getDrawable(R.drawable.back3);
        drawableBackground[3]=context.getResources().getDrawable(R.drawable.back4);
        drawableBackground[4]=context.getResources().getDrawable(R.drawable.back5);
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
        Log.e("ForecastArrayAdapter","getView "+position);
		rowView = convertView;
		forcast = getItem(position);
		if (rowView == null) {
			// always add the layout, the parent and false
            rowView = inflater.inflate(R.layout.item_forecast, null, false);
            ViewHolder vh = new ViewHolder(rowView,position);
			rowView.setTag(vh);
		}
		viewHolder = (ViewHolder) rowView.getTag();
        //used for animation
        viewHolder.currentPosition=position;
		if (postJB) {
			viewHolder.getImvIcon().setBackground(forcast.getImage());
            viewHolder.getImvBack().setBackground(drawableBackground[position%5]);
		} else {
            viewHolder.getImvIcon().setBackgroundDrawable(forcast.getImage());
            viewHolder.getImvBack().setBackgroundDrawable(drawableBackground[position % 5]);
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
        //because the first time is not an update, it's just loading data from db
		if (notifyDataSetChangedCallsNumber >=2) {
            viewHolder.launchUpdateAnimation(notifyDataSetChangedCallsNumber);
		}
        //and finally manage the visibility of the side : front or back side is visible
        manageSideVisibility(position);
		return rowView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#notifyDataSetChanged()
	 */
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		notifyDataSetChangedCallsNumber++;
	}
    /**************************************************
     * Flipping Animation tricks
     * **************************************************
     */

    /**
     * If the element has been flipped, flip it else set it has not flipped
     * @param position
     */
    private void manageSideVisibility(int position){
        if(isFlipped.get(position)){
            //the backside is visible
            viewHolder.getImvBack().setVisibility(View.VISIBLE);
            viewHolder.getLinRoot().setVisibility(View.GONE);
            viewHolder.getTxvShouldBeVisible().setText("IMVBACK should be visible");
        }else{
            //the ffront is visible
            viewHolder.getImvBack().setVisibility(View.GONE);
            viewHolder.getLinRoot().setVisibility(View.VISIBLE);
            viewHolder.getTxvShouldBeVisible().setText("LinROOT should be visible");
        }
    }
	/******************************************************************************************/
	/** Runnable for animation **************************************************************************/
	/******************************************************************************************/
	public class MyRunnable implements Runnable {
		/**
		 * The viewHolder that contains the view to animate
		 */
		private ViewHolder vh;

		public MyRunnable(ViewHolder vh) {
			this.vh=vh;
		}

		public void run() {
            vh.animateUpdate();
		}
	}

	/******************************************************************************************/
	/** The ViewHolder pattern **************************************************************************/
	/******************************************************************************************/

	private class ViewHolder {
        //To delete when bug fixed ==>
        TextView txvShouldBeVisible;
        public final TextView getTxvShouldBeVisible() {
            if (null == txvShouldBeVisible) {
                txvShouldBeVisible = (TextView) view.findViewById(R.id.txv_shouldbevisible);
            }
            return txvShouldBeVisible;
        }
        //<====




		View view;
		LinearLayout linRoot;
		TextView txvDate;
		TextView txvTendance;
		ImageView imvIcon;
		TextView txvCurrent;
		TextView txvMin;
        TextView txvMax;
        TextView txvUpdating;
        //For Update animation
        Animation updateAnimation;
        MyRunnable animationRunnable;
        int dataTimeStamp=0;
        //For animatibbbbbbon
        ImageView imvBack;
        int currentPosition;
        //PostHoneyComb
        Animator flipAnimatorIn;
        Animator flipAnimatorOut;
        Animator reverseFlipAnimatorIn;
        Animator reverseFlipAnimatorOut;
        AnimatorSet setFlip;
        AnimatorSet setReverse;
        //PreHoneyComb
        Animation animInLegacy;
        Animation animOutLegacy;
        int id;
		/**
		 * @param rowview
		 */
		private ViewHolder(View rowview,int position) {
			super();
			this.view = rowview;
            animationRunnable=new MyRunnable(this);
            id=position;
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
                imvIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(postHC){
                            animateItem();
                        }else{
                            flipItemLegacy();
                        }
                    }
                });
			}
			return imvIcon;
		}
        /**
         * @return the imvBack
         */
        public final ImageView getImvBack() {
            if (null == imvBack) {
                imvBack = (ImageView) view.findViewById(R.id.imvBack);
                imvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(postHC){
                            reverseAnimateItem();
                        }else{
                            reverseItemLegacy();
                        }
                    }
                });
            }
            return imvBack;
        }
        /**
         * @return the txvTendance
         */
        public final TextView getTxvUpdating() {
            if (null == txvUpdating) {
                txvUpdating = (TextView) view.findViewById(R.id.txv_updating);
            }
            return txvUpdating;
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
        /**************************************************
         * Animation tricks
         * All Version
         * The UpdateAnimation
         * **************************************************
         */
        /**
         * Launch the Update Animation
         */
        public void animateUpdate() {
            if (updateAnimation==null) {
                updateAnimation=AnimationUtils.loadAnimation(getContext(), R.anim.anim_item_updated);
                updateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        getTxvUpdating().setVisibility(View.VISIBLE);}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                            getTxvUpdating().setVisibility(View.GONE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }

            if (isFlipped.get(currentPosition)) {
                getImvBack().startAnimation(updateAnimation);
            } else {
                //run it
                getLinRoot().startAnimation(updateAnimation);
            }
        }
        /**
         * Launch the Update Animation
         */
        public void launchUpdateAnimation(int ndscCallsNumber){
            if(dataTimeStamp!=ndscCallsNumber) {
                //it means an already runnable is associated with this item
                //we need to remove it (else it gonna run the animation twice
                //and it's strange for the user)
                handlerForAnimation.removeCallbacks(animationRunnable);
                //then launched it in few seconds
                handlerForAnimation.postDelayed(animationRunnable, 300 * currentPosition);
                Log.e("tag", "launchUpdateAnimation in " + 300 * currentPosition + " for item " + currentPosition);
                dataTimeStamp=ndscCallsNumber;
            }
        }

        /**************************************************
         * Animation tricks
         * preHoneyComb : 4 Gingerbread in fact
         * **************************************************
         */
        private void flipItemLegacy(){
            if(animInLegacy==null){
                animInLegacy= AnimationUtils.loadAnimation(getContext(),R.anim.forecast_item_in);
            }
            if(animOutLegacy==null){
                animOutLegacy= AnimationUtils.loadAnimation(getContext(),R.anim.forecast_item_out);
            }
            animOutLegacy.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {}
                public void onAnimationEnd(Animation animation) {
                    Log.e("ForecastArrayAdapter","flipItemLegacy onAnimationEnd called ");
                    getImvBack().setVisibility(View.VISIBLE);
                    getImvBack().startAnimation(animInLegacy);
                    getLinRoot().setVisibility(View.GONE);
                }
                public void onAnimationRepeat(Animation animation) {}
            });
            getLinRoot().startAnimation(animOutLegacy);

            isFlipped.put(currentPosition,true);

        }
        private void reverseItemLegacy(){
            if(animInLegacy==null){
                animInLegacy= AnimationUtils.loadAnimation(getContext(),R.anim.forecast_item_in);
            }
            if(animOutLegacy==null){
                animOutLegacy= AnimationUtils.loadAnimation(getContext(),R.anim.forecast_item_out);
            }
            animInLegacy.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {}
                public void onAnimationEnd(Animation animation) {
                    getLinRoot().setVisibility(View.VISIBLE);
                    getLinRoot().startAnimation(animInLegacy);
                    getImvBack().setVisibility(View.GONE);
                }
                public void onAnimationRepeat(Animation animation) {}
            });
            getImvBack().startAnimation(animOutLegacy);

            isFlipped.put(currentPosition,false);

        }

        /**************************************************
         * Animation tricks
         * postHoneyComb
         * **************************************************
         */

        @SuppressLint("NewApi")
        private void animateItem(){
            initialiseFlipAnimator();
            setFlip.start();
            isFlipped.put(currentPosition,true);
        }
        @SuppressLint("NewApi")
        private void reverseAnimateItem(){
            initialiseReverseFlipAnimator();
            setReverse.start();
            isFlipped.put(currentPosition,false);
        }
        @SuppressLint("NewApi")
        private void initialiseReverseFlipAnimator() {
            if(reverseFlipAnimatorIn==null){
                reverseFlipAnimatorIn= AnimatorInflater.loadAnimator(getContext(), R.animator.flip_in);
                reverseFlipAnimatorIn.addListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        getLinRoot().setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        getImvBack().setVisibility(View.GONE);
                    }
                });
                reverseFlipAnimatorIn.setTarget(getLinRoot());
                reverseFlipAnimatorOut= AnimatorInflater.loadAnimator(getContext(),R.animator.flip_out);
                reverseFlipAnimatorOut.setTarget(imvBack);
                setReverse=new AnimatorSet();
                setReverse.playTogether(reverseFlipAnimatorIn,reverseFlipAnimatorOut);
            }
        }

        @SuppressLint("NewApi")
        private void initialiseFlipAnimator(){
            Log.e("ForecastArrayAdapter","initialiseFlipAnimator");
            if(flipAnimatorIn==null){
                flipAnimatorIn= AnimatorInflater.loadAnimator(getContext(),R.animator.flip_in);
                flipAnimatorIn.setTarget(getImvBack());
                flipAnimatorOut= AnimatorInflater.loadAnimator(getContext(),R.animator.flip_out);
                flipAnimatorOut.setTarget(getLinRoot());
                flipAnimatorIn.addListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.e("tag","anim onAnimationStart");
                        getImvBack().setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.e("tag","anim onAnimationEnd");
                        getLinRoot().setVisibility(View.GONE);
                    }
                });
                setFlip=new AnimatorSet();
                setFlip.playTogether(flipAnimatorIn, flipAnimatorOut);
            }
        }
    }

    @SuppressLint("NewApi")
    public abstract class SimpleAnimatorListener implements Animator.AnimatorListener {
        /**
         * <p>Notifies the start of the animation.</p>
         *
         * @param animation The started animation.
         */
        public abstract void onAnimationStart(Animator animation);

        /**
         * <p>Notifies the end of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         *
         * @param animation The animation which reached its end.
         */
        public abstract void onAnimationEnd(Animator animation) ;

        /**
         * <p>Notifies the cancellation of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         *
         * @param animation The animation which was canceled.
         */
        @Override
        public void onAnimationCancel(Animator animation) {
            onAnimationEnd(animation);
        }

        /**
         * <p>Notifies the repetition of the animation.</p>
         *
         * @param animation The animation which was repeated.
         */
        @Override
        public void onAnimationRepeat(Animator animation) {
            onAnimationStart(animation);
        }
    }
}