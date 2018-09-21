package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.forecast.arrayadapter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.forecast.WeatherForecatsItemWithMainAndWeathers;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.forecast.ForercastWeatherActivityModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 23/06/2018.
 */
public class ForecastItemHolder extends RecyclerView.ViewHolder{
    private static final String TAG = "ForecatsItemHolder";
    /**
     *
     */
    private static final float KELVIN_OFFSET_TO_CELSIUS = -273.15f;
    /***********************************************************
     *  Attributes
     **********************************************************/
    private View item;
    private AppCompatImageView imv_ico;
    private AppCompatTextView txvMain;
    /***********************************************************
     *  UxState Attributes
     **********************************************************/
    /**
     * This sparse array tracks every elements the user change its visibility (for the cdvCondition element)
     * When a visibility change occurs (a call to switchConditionVisibility method)
     * we store the itemId and the new visibility state to restore it when the item is displayed again
     */
    SparseIntArray itemIdToViewState;
    /***********************************************************
     *  UI Attributes of the WeatherCondition
     **********************************************************/
    /**
     * The card view displaying
     */
    private CardView cdvConditions;
    /**     * Txv Wind     */
    private TextView tvWind;
    /**     * CompatImageView containing AVD for Wind     */
    private AppCompatImageView ivWind;
    /**     * Txv Snow     */
    private TextView tvSnow;
    /**     * CompatImageView containing AVD for Snow     */
    private AppCompatImageView ivSnow;
    /**     * Txv Rain     */
    private TextView tvRain;
    /**     * CompatImageView containing AVD for Rain     */
    private AppCompatImageView ivRain;
    /**     * Txv Clouds     */
    private TextView tvClouds;
    /**     * CompatImageView containing AVD for Clouds     */
    private AppCompatImageView ivClouds;

    private TextView tvTemperature=null;
    private TextView tvHumidity=null;
    private TextView tvPressure=null;
    private ImageView ivDrop=null;

    private AppCompatTextView txvTime;
    AppCompatActivity ctx;
    String previousIconName=null;
    ForercastWeatherActivityModel model;
    Observer<Bitmap> bitmapObserver;
    /**
     * The full cardView (to change its background when the day displayed is today)
     */
    private CardView cdvHeader=null;
    /**
     * Color for the background of the cardView when today
     */
    private int todayColor;
    /**
     * Color for the background of the cardView when NOT today
     */
    private int currentDayColor;
    /***********************************************************
    *  Constructors
    **********************************************************/
    public ForecastItemHolder(View itemView, AppCompatActivity lfOwner, ForercastWeatherActivityModel model) {
        super(itemView);
        itemIdToViewState=new SparseIntArray();
        item=itemView;
        this.model=model;
        ctx=lfOwner;
        bitmapObserver = new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                //updating BitMap
                if(bitmap!=null){
                    imv_ico.setImageBitmap(bitmap);
                }
            }
        };
        tvWind = item.findViewById(R.id.txv_winds);
        tvSnow = item.findViewById(R.id.txv_snow);
        tvRain = item.findViewById(R.id.txv_rain);
        tvClouds = item.findViewById(R.id.txv_clouds);
        ivWind= item.findViewById(R.id.iv_winds);
        ivSnow=item.findViewById(R.id.iv_snow);
        ivRain=item.findViewById(R.id.iv_rain);
        ivClouds=item.findViewById(R.id.iv_clouds);
        imv_ico=item.findViewById(R.id.imv_ico);
        txvMain=item.findViewById(R.id.txv_main);
        tvTemperature = item.findViewById(R.id.tv_temperature);
        tvHumidity = item.findViewById(R.id.tv_humidity);
        tvPressure = item.findViewById(R.id.tv_pressure);
        ivDrop = item.findViewById(R.id.iv_drop);
        txvTime=item.findViewById(R.id.txv_time);
        cdvHeader=item.findViewById(R.id.cdv_header);
        cdvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchConditionVisibility();
            }
        });
        cdvConditions=item.findViewById(R.id.cdv_conditions);
        todayColor=ctx.getResources().getColor(R.color.colorPrimaryLight);
        currentDayColor=cdvHeader.getCardBackgroundColor().getDefaultColor();


    }
    /**
     * Update the view
     * @param weatherForecastItem
     */
    public void updateView(WeatherForecatsItemWithMainAndWeathers weatherForecastItem,boolean today){
        //update the UI
        tvWind.setText(""+(weatherForecastItem.getForecastItem().getWind()!=null?weatherForecastItem.getForecastItem().getWind().getSpeed():" 0 "));
        tvClouds.setText(""+(weatherForecastItem.getForecastItem().getClouds()!=null?weatherForecastItem.getForecastItem().getClouds().getAll():" 0 "));
        tvRain.setText(""+(weatherForecastItem.getForecastItem().getRain()!=null?weatherForecastItem.getForecastItem().getRain().get3h():" 0 "));
        tvSnow.setText(""+(weatherForecastItem.getForecastItem().getSnow()!=null?weatherForecastItem.getForecastItem().getSnow().get3h():" 0 "));
        tvSnow.setText(""+weatherForecastItem.getForecastItem().get_id());
        txvMain.setText(weatherForecastItem.getWeathers().get(0).getMain());
        tvTemperature.setText(ctx.getString(R.string.main_temperature, weatherForecastItem.getTemp() + KELVIN_OFFSET_TO_CELSIUS));
        tvHumidity.setText(ctx.getString(R.string.main_humidity, weatherForecastItem.getHumidity()));
        tvPressure.setText(""+((int)weatherForecastItem.getPressure()));
        //unibserve previous icon observation
        if(previousIconName!=null){
            model.getIconBitmap(previousIconName)
                    .removeObserver(bitmapObserver);
        }
        //add the new observer
        model.getIconBitmap(weatherForecastItem.getWeathers().get(0).getIcon())
                .observe(ctx, bitmapObserver);
        previousIconName=weatherForecastItem.getWeathers().get(0).getIcon();
        txvTime.setText(formatTime(weatherForecastItem.getForecastItem().getDt()));
        //update the today day background
        if(today){
            cdvHeader.setCardBackgroundColor(todayColor);
        }else{
            cdvHeader.setCardBackgroundColor(currentDayColor);
        }
        //update the Ccdv_condition visibility
        if(itemIdToViewState.get(getAdapterPosition(),-1)==-1){
            //in that case not found
            if(today){
                cdvConditions.setVisibility(View.VISIBLE);
            }else{
                cdvConditions.setVisibility(View.GONE);
            }
        }else{
            //in that case, items is found
            cdvConditions.setVisibility(itemIdToViewState.get(getAdapterPosition()));
        }
    }
    /**
     * Start AnimationVectorDrawables
     * @param drawable Start the animation of this VectorDrawable
     */
    @SuppressLint("NewApi")
    private void startAVDAnimation(Drawable drawable) {
        if (drawable instanceof AnimatedVectorDrawableCompat) {
            ((AnimatedVectorDrawableCompat) drawable).start();
        } else {
            ((AnimatedVectorDrawable) drawable).start();
        }
    }

    /**
     * Switch the state of the displayed view
     * Expand or collapse the condition cardView
     */
    private void switchConditionVisibility(){
        if(cdvConditions.getVisibility()==View.GONE){
            cdvConditions.setVisibility(View.VISIBLE);
        }else{
            cdvConditions.setVisibility(View.GONE);
        }
        //keep the position and the state of each changed items
        itemIdToViewState.put(getAdapterPosition(),cdvConditions.getVisibility());
    }
    /***********************************************************
     *  Managing date
     **********************************************************/
    private static final String TIME_FORMAT = "EE dd MMM HH:mm";
    SimpleDateFormat sdf=new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
    Date dateTemp = new Date();
    private String formatTime(long utcTime){
        dateTemp.setTime(utcTime*1000L);
        return sdf.format(dateTemp);
    }
}
