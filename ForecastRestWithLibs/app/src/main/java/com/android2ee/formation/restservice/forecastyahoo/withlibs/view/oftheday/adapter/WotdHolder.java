package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.oftheday.adapter;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.calculated.WeatherOfTheDay;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.oftheday.WotdActivityModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 23/06/2018.
 */
public class WotdHolder extends RecyclerView.ViewHolder{
    private static final String TAG = "ForecatsItemHolder";
    /**
     *
     */
    private static final float KELVIN_OFFSET_TO_CELSIUS = -273.15f;
    /***********************************************************
     *  Attributes
     **********************************************************/
    private View item;
    private AppCompatImageView imv_ico,imv_ico_sec;
    private AppCompatTextView txvMain,txvMainSecondary;
    /***********************************************************
     *  UI Attributes of the WeatherCondition
     **********************************************************/
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

    private TextView tvTempMax =null;
    private TextView tvTempMin =null;
    private TextView tvHumidity=null;
    private TextView tvPressure=null;
    private ImageView ivDrop=null;
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
    private AppCompatTextView txvTime;
    AppCompatActivity ctx;
    String previousIconName=null,previousSecondIconName=null;
    WotdActivityModel model;
    Observer<Bitmap> bitmapObserver,bitMapObserverSecond;
    /***********************************************************
    *  Constructors
    **********************************************************/
    public WotdHolder(View itemView, AppCompatActivity lfOwner, WotdActivityModel model) {
        super(itemView);
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
        bitMapObserverSecond= new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                //updating BitMap
                if(bitmap!=null){
                    imv_ico_sec.setImageBitmap(bitmap);
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
        imv_ico_sec=item.findViewById(R.id.imv_ico_secondary);
        txvMain=item.findViewById(R.id.txv_main);
        txvMainSecondary=item.findViewById(R.id.txv_main_secondary);
        tvTempMax = item.findViewById(R.id.tv_temp_max);
        tvTempMin = item.findViewById(R.id.tv_temp_min);
        tvHumidity = item.findViewById(R.id.tv_humidity);
        tvPressure = item.findViewById(R.id.tv_pressure);
        ivDrop = item.findViewById(R.id.iv_drop);
        txvTime=item.findViewById(R.id.txv_time);
        cdvHeader=item.findViewById(R.id.cdv_header);
        todayColor=ctx.getResources().getColor(R.color.colorPrimaryLight);
        currentDayColor=cdvHeader.getCardBackgroundColor().getDefaultColor();
    }
    /**
     * Update the view
     * @param weatherOfTheDay
     */
    public void updateView(WeatherOfTheDay weatherOfTheDay,boolean today){
        MyLog.e(TAG,"holder is updating the item with id="+weatherOfTheDay.get_id_wotd());
        //update the UI
        tvWind.setText(""+((int)weatherOfTheDay.getWindSpeed()));
        tvClouds.setText(""+((int)weatherOfTheDay.getClouds()));
        tvRain.setText(""+weatherOfTheDay.getRain());
        tvSnow.setText(""+weatherOfTheDay.getSnow());
        txvMain.setText(weatherOfTheDay.getMain());
        txvMainSecondary.setText(weatherOfTheDay.getMainSecondary());
        txvTime.setText(formatTime(weatherOfTheDay.getDayHashCalendar()));
        tvTempMax.setText(ctx.getString(R.string.main_temperature, weatherOfTheDay.getTempMax() + KELVIN_OFFSET_TO_CELSIUS));
        tvTempMin.setText(ctx.getString(R.string.main_temperature, weatherOfTheDay.getTempMin() + KELVIN_OFFSET_TO_CELSIUS));
        tvHumidity.setText(""+((int)weatherOfTheDay.getHumidity()));
        tvPressure.setText(""+((int)weatherOfTheDay.getPressure()));

        //update the today day background
        if(today){
            cdvHeader.setCardBackgroundColor(todayColor);
        }else{
            cdvHeader.setCardBackgroundColor(currentDayColor);
        }
        //Manage the first Icon
        if(previousIconName!=null){
            model.getIconBitmap(previousIconName)
                    .removeObserver(bitmapObserver);
        }
        //add the new observer
        model.getIconBitmap(weatherOfTheDay.getIcon())
                .observe(ctx, bitmapObserver);
        previousIconName=weatherOfTheDay.getIcon();
        //Manage the Second Icon
        if(previousSecondIconName!=null){
            model.getIconBitmap(previousSecondIconName)
                    .removeObserver(bitMapObserverSecond);
        }
        model.getIconBitmap(weatherOfTheDay.getIconSecondary())
                .observe(ctx, bitMapObserverSecond);
        previousSecondIconName=weatherOfTheDay.getIconSecondary();

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

    /***********************************************************
     *  Manage the date
     **********************************************************/

    private static final String TIME_FORMAT = "EE dd MMM";
    SimpleDateFormat sdf=new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
    Date dateTemp = new Date();
    private String formatTime(Calendar calAndroidTime){
        dateTemp.setTime(calAndroidTime.getTimeInMillis());
        return sdf.format(dateTemp);
    }

}
