package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.arrayadapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.ForercastWeatherActivityModel;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 22/06/2018.
 */
public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemHolder> {
    private static final String TAG = "ForecastItemAdapter";
    /***********************************************************
     *  Attributes
     **********************************************************/
    private ForercastWeatherActivityModel parentModel;
    private LayoutInflater inflater;
    private ForecastItemHolder viewHolder;
    private View view;
    private AppCompatActivity activity;
    /***********************************************************
    *  Constructors
    **********************************************************/

    public ForecastItemAdapter(AppCompatActivity ctx, ForercastWeatherActivityModel parentModel) {
        activity = ctx;
        this.parentModel=parentModel;
        inflater=LayoutInflater.from(ctx);
    }

    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    @NonNull
    @Override
    public ForecastItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate
        view=inflater.inflate(R.layout.cardview_forecast_main_item, parent, false);
        //create
        viewHolder=new ForecastItemHolder(view,activity);
        //bind with LiveData
        viewHolder.setStreamToListen(parentModel.getLiveDataForHolder(viewHolder.hashCode()));
        viewHolder.setWeatherStreamToObserve(parentModel.getHolderToWeatherLD(viewHolder.hashCode()));
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ForecastItemHolder holder, int position) {
        parentModel.bindHolder(holder.hashCode(),position);
    }
    @Override
    public int getItemCount() {
        if(parentModel==null|| parentModel.getData()==null||parentModel.getData().getValue()==null){
            return 0;
        }
        return parentModel.getData().getValue().size();
    }


}
