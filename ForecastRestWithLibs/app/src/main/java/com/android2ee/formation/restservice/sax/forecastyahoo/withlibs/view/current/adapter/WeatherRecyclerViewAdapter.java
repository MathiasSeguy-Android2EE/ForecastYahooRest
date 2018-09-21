package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.current.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.Weather;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.current.CurrentWeatherActivityModel;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.current.weather.WeatherCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "WeatherRecyclerViewAdap";
    /***********************************************************
     *  Attributes
     **********************************************************/
    private CurrentWeatherActivityModel parentModel;
    private ArrayList<Weather> weathers;
    private LayoutInflater inflater;
    private MyViewHolder viewHolder;
    private WeatherCardView cv;
    private Weather weatherTemp;
    private LifecycleOwner lfOwner;
    /***********************************************************
    *  Constructors
    **********************************************************/
    public WeatherRecyclerViewAdapter(ArrayList<Weather> weathers, AppCompatActivity ctx, CurrentWeatherActivityModel model) {
        lfOwner=ctx;
        parentModel=model;
        this.weathers = weathers;
        inflater=LayoutInflater.from(ctx);
    }

    public void updateWeatherData(List<Weather> weathers){
        MyLog.e(TAG,"new weather to display in the recycler size="+weathers.size());
        //use DiffUtil
        this.weathers.clear();
        this.weathers.addAll(weathers);
        //need to test several items ?
//        addFakeWeathers();
        notifyDataSetChanged();
    }

    private void addFakeWeathers(){
        Weather weather;
        //add thunderstorm
        weather=new Weather();
        weather.setDescription("Hello wolrd");
        weather.setMain("Thunderstorms");
        weather.setIcon("11d");
        weathers.add(weather);
        //add Mist
        weather=new Weather();
        weather.setDescription("Hello wolrd");
        weather.setMain("Mist");
        weather.setIcon("50d");
        weathers.add(weather);
        //add Scattered clouds
        weather=new Weather();
        weather.setDescription("Hello wolrd");
        weather.setMain("Scattered clouds");
        weather.setIcon("03d");
        weathers.add(weather);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cv = (WeatherCardView) inflater.inflate(R.layout.activity_current_cardview_weather_item, parent, false);
        viewHolder = new MyViewHolder(cv);
        viewHolder.setIconLiveData(parentModel.getIconByHolder(viewHolder.hash),lfOwner);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        weatherTemp = this.weathers.get(position);
//        holder.getImv_ico().setBackgroundResource(R.drawable.btn_earth);
        holder.getTxvMain().setText(weatherTemp.getMain());
        holder.getTxvDescription().setText(weatherTemp.getDescription());
        parentModel.updateIcon(holder.hash,weatherTemp.getIcon());
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private int hash;
        public WeatherCardView weatherCardView;
        private AppCompatImageView imv_ico;
        private AppCompatTextView txvMain;
        private AppCompatTextView txvDescription;
        private MutableLiveData<Bitmap> iconLiveData;
        public MyViewHolder(View view) {
            super(view);
            hash=view.hashCode();
            weatherCardView = view.findViewById(R.id.cv_weather);
            imv_ico=weatherCardView.findViewById(R.id.imv_ico);
            txvMain=weatherCardView.findViewById(R.id.txv_main);
            txvDescription=weatherCardView.findViewById(R.id.txv_description);
        }

        public AppCompatImageView getImv_ico() {
            return imv_ico;
        }

        public AppCompatTextView getTxvMain() {
            return txvMain;
        }

        public AppCompatTextView getTxvDescription() {
            return txvDescription;
        }

        public void setIconLiveData(MutableLiveData<Bitmap> iconLiveData,LifecycleOwner lfOwner) {
            this.iconLiveData = iconLiveData;
            this.iconLiveData.observe(lfOwner, new Observer<Bitmap>() {
                @Override
                public void onChanged(@Nullable Bitmap bitmap) {
                    imv_ico.setImageBitmap(bitmap);
                }
            });
        }
    }
}
