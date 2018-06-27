package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.arrayadapter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.forecast.WeatherForecastItem;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.main.MainCardView;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather.WeatherCardView;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 23/06/2018.
 */
public class ForecastItemHolder extends RecyclerView.ViewHolder{
    private static final String TAG = "ForecatsItemHolder";
    /***********************************************************
     *  Attributes
     **********************************************************/
    private View item;
    /**
     * The CardView to use when displaying the main inforamtion of the weather (min/max/temp/humidity/pressure)
     * Needs a
     */
    private MainCardView mainCardView;
    /**
     * The cardView used to display the Weather icon
     */
    public WeatherCardView weatherCardView;
    private AppCompatImageView imv_ico;
    private AppCompatTextView txvMain;
    private AppCompatTextView txvDescription;
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



    /**
     * The Id of the displayed forecast
     * Mandatory for Main
     */
    Long weatherForecastItemId;
    /**
     * LifeCycleOwner
     */
    AppCompatActivity lfOwner;
    LiveData<WeatherForecastItem> streamToListen;
    Observer<WeatherForecastItem> forecastItemObserver;
    LiveData<Weather> weatherStream;
    Observer<Weather> weatherObserver;
    /***********************************************************
    *  Constructors
    **********************************************************/
    public ForecastItemHolder(View itemView,  AppCompatActivity lfOwner) {
        super(itemView);
        item=itemView;
        this.lfOwner=lfOwner;
        //The condition elements:
        tvWind = item.findViewById(R.id.txv_winds);
        tvSnow = item.findViewById(R.id.txv_snow);
        tvRain = item.findViewById(R.id.txv_rain);
        tvClouds = item.findViewById(R.id.txv_clouds);
        ivWind= item.findViewById(R.id.iv_winds);
        ivSnow=item.findViewById(R.id.iv_snow);
        ivRain=item.findViewById(R.id.iv_rain);
        ivClouds=item.findViewById(R.id.iv_clouds);


        weatherCardView = item.findViewById(R.id.cdv_weather);
        imv_ico=item.findViewById(R.id.imv_ico);
        txvMain=item.findViewById(R.id.txv_main);
        txvDescription=item.findViewById(R.id.txv_description);
        //use its own model
        mainCardView = item.findViewById(R.id.cdv_main);
        mainCardView.setIsForecast(true);
        //using the parent model
        weatherCardView=item.findViewById(R.id.cdv_weather);
        forecastItemObserver = new Observer<WeatherForecastItem>() {
            @Override
            public void onChanged(@Nullable WeatherForecastItem weatherForecastItem) {
                updateView(weatherForecastItem);
            }
        };
        weatherObserver=new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {
                updateWeather(weather);
            }
        };
    }

    /**
     * Define which stream the Holder is linked to
     * @param streamToListen
     */
    public void setStreamToListen(LiveData<WeatherForecastItem> streamToListen) {
        if(this.streamToListen!=null){
            this.streamToListen.removeObserver(forecastItemObserver);
        }
        this.streamToListen=streamToListen;
        streamToListen.observe(lfOwner, forecastItemObserver);
    }
    /**
     * Define which stream the Holder is linked to
     * @param weatherStream
     */
    public void setWeatherStreamToObserve(LiveData<Weather> weatherStream) {
        if(this.weatherStream!=null){
            this.weatherStream.removeObserver(weatherObserver);
        }
        this.weatherStream=weatherStream;
        weatherStream.observe(lfOwner, weatherObserver);
    }

    public void updateWeather(Weather weather){
        if(weather!=null) {
            txvMain.setText(weather.getMain()+":"+weather.getIcon());
//            txvDescription.setText(weather.getDescription());
            txvDescription.setText("4: "+weatherForecastItemId);
            //TODO
            //imv_ico.setImageBitmap(PictureCacheDownloader.loadPictureFromDisk(weather.getIcon()));
        }else{

            txvMain.setText("Null");
            txvDescription.setText("4: "+weatherForecastItemId);
        }

    }
    /**
     * Update the view
     * @param weatherForecastItem
     */
    private void updateView(WeatherForecastItem weatherForecastItem){
        weatherForecastItemId=weatherForecastItem.get_id();
        MyLog.e(TAG,"holder is updating the item with id="+weatherForecastItem.get_id());
        //update the UI
        tvWind.setText(""+(weatherForecastItem.getWind()!=null?weatherForecastItem.getWind().getSpeed():" 0 "));
        tvClouds.setText(""+(weatherForecastItem.getClouds()!=null?weatherForecastItem.getClouds().getAll():" 0 "));
        tvRain.setText(""+(weatherForecastItem.getRain()!=null?weatherForecastItem.getRain().get3h():" 0 "));
        tvSnow.setText(""+(weatherForecastItem.getSnow()!=null?weatherForecastItem.getSnow().get3h():" 0 "));
        tvSnow.setText(""+weatherForecastItem.get_id());
        startAVDAnimation(ivClouds.getDrawable());
        startAVDAnimation(ivWind.getDrawable());
        startAVDAnimation(ivRain.getDrawable());
        startAVDAnimation(ivSnow.getDrawable());

        //Update stream
        mainCardView.setLifecycleOwner(lfOwner, weatherForecastItem.get_id());
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
}
