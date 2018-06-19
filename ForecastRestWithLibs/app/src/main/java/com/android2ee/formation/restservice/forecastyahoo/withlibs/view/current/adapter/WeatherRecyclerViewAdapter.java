package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather.WeatherCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "WeatherRecyclerViewAdap";

    private ArrayList<Weather> weathers;
    private LayoutInflater inflater;
    private MyViewHolder viewHolder;
    private WeatherCardView cv;
    private Weather weatherTemp;
    public WeatherRecyclerViewAdapter(ArrayList<Weather> weathers, Context ctx) {
        this.weathers = weathers;
        inflater=LayoutInflater.from(ctx);
    }

    public void updateWeatherData(List<Weather> weathers){
        MyLog.e(TAG,"new weather to display in the recycler size="+weathers.size());
        //use DiffUtil
        this.weathers.clear();
        this.weathers.addAll(weathers);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cv = (WeatherCardView) inflater.inflate(R.layout.cardview_weather_item, parent, false);
        viewHolder = new MyViewHolder(cv);
        return viewHolder;
    }
    Weather weather;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        weather = this.weathers.get(position);
        holder.getImv_ico().setBackgroundResource(R.drawable.btn_earth);
        holder.getTxvMain().setText(weather.getMain());
        holder.getTxvDescription().setText(weather.getDescription());
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public WeatherCardView weatherCardView;
        private AppCompatImageView imv_ico;
        private AppCompatTextView txvMain;
        private AppCompatTextView txvDescription;
        public MyViewHolder(View view) {
            super(view);
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
    }
}
