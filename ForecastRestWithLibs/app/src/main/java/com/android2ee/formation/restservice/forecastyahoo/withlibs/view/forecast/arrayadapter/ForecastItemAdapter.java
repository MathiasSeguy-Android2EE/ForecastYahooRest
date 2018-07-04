package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.arrayadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.forecast.WeatherForecatsItemWithMainAndWeathers;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.ForercastWeatherActivityModel;

import java.util.List;

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
    List<WeatherForecatsItemWithMainAndWeathers> items=null;
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
        view=inflater.inflate(R.layout.activity_forecast_cardview_item, parent, false);
        //create
        viewHolder=new ForecastItemHolder(view,activity,parentModel);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ForecastItemHolder holder, int position) {
        MyLog.e(TAG,"onBindViewHolder called"+position+", item:"+items.get(position));
//        parentModel.bindHolder(holder.hashCode(),position);
        holder.updateView(items.get(position));
    }

    @Override
    public int getItemCount() {
        if(items==null){
            return 0;
        }
        return items.size();
    }
    List<WeatherForecatsItemWithMainAndWeathers> mWeatherForecatsItemWithMainAndWeathers;
    public void updateList(@Nullable List<WeatherForecatsItemWithMainAndWeathers> weatherForecastItems){
        MyLog.e(TAG, "updateList called new size"+(weatherForecastItems==null?"0":weatherForecastItems.size())+" onThread:"+Thread.currentThread());
        items=weatherForecastItems;
        DiffUtil.calculateDiff(diffUtilForecastItemChangesAnlayser(this.mWeatherForecatsItemWithMainAndWeathers, weatherForecastItems)).dispatchUpdatesTo(this);
    }
/***********************************************************
 *  DiffUtilsCallBack and ListUpdateCallback implementation
 **********************************************************/
    /**
     * The goal is to detect if the PagerAdapter has to be be notifyDataSetChanged
     * So the size are the same and the isOver is the same
     * @param oldList The old battle
     * @param newList The new battle
     * @return Nothing, dispatch the updateEntity to this (which implement UpdateCallback)
     */
    @NonNull
    private DiffUtil.Callback diffUtilForecastItemChangesAnlayser(final List<WeatherForecatsItemWithMainAndWeathers> oldList, final List<WeatherForecatsItemWithMainAndWeathers> newList) {
        return new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList==null?0:oldList.size();
            }
            @Override
            public int getNewListSize() {
                return newList==null?0:newList.size();
            }
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).getForecastItem().get_id() == newList.get(oldItemPosition).getForecastItem().get_id() ;
            }
            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                //only detect changes in isOver, you don't care here about tracking title, deb and fin
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }
        };
    }


}
