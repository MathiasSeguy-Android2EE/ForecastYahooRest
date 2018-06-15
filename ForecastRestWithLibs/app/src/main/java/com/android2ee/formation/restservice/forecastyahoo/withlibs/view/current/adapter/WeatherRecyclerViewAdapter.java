package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Weather;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.weather.WeatherCardView;

import java.util.ArrayList;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Weather> weathers;


    public WeatherRecyclerViewAdapter(ArrayList<Weather> weathers) {
        this.weathers = weathers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WeatherCardView cv = (WeatherCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_cardview_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(cv);
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
