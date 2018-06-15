package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather.WeatherCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
        //use DiffUtil
        this.weathers.clear();
        this.weathers.addAll(weathers);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cv = (WeatherCardView) inflater.inflate(R.layout.weather_cardview_item, parent, false);
        viewHolder = new MyViewHolder(cv);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Weather weather = this.weathers.get(position);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public WeatherCardView weatherCardView;
        public MyViewHolder(View view) {
            super(view);
            weatherCardView = view.findViewById(R.id.cv_weather);
        }
    }
}
