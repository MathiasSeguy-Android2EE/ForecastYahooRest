package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.forecast.arrayadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.forecast.WeatherForecatsItemWithMainAndWeathers;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.forecast.ForercastWeatherActivityModel;

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
    private List<WeatherForecatsItemWithMainAndWeathers> items=null;
    /**
     * To know the position of today's items
     * As they are several, we store the min and max (included)
     * We know they are following each others so min max is good,
     * else a list would have been necessary
     */
    private int itemOfTodayMinPosition=0,itemOfTodayMaxPosition=0;
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
        holder.updateView(items.get(position), isToday(position));
    }



    @Override
    public int getItemCount() {
        if(items==null){
            return 0;
        }
        return items.size();
    }

    /**
     * Updating the list because a new set of data has been send to us
     * @param weatherForecastItems
     */
    public void updateList(@Nullable List<WeatherForecatsItemWithMainAndWeathers> weatherForecastItems){
        MyLog.e(TAG, "updateList called new size"+(weatherForecastItems==null?"0":weatherForecastItems.size())+" onThread:"+Thread.currentThread());
        DiffUtil.calculateDiff(diffUtilForecastItemChangesAnlayser(items, weatherForecastItems)).dispatchUpdatesTo(this);
        items=weatherForecastItems;
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

    /***********************************************************
     *  Public methods called by activity
     **********************************************************/
    /**
     * Has o be called by the activty
     * Define the position of today in the list
     * @param itemOfTodayMinPosition First item position of today
     * @param itemOfTodayMaxPosition Last item position of today
     */
    public void setItemsOfTodayPosition(int itemOfTodayMinPosition, int itemOfTodayMaxPosition){
        this.itemOfTodayMinPosition=itemOfTodayMinPosition;
        this.itemOfTodayMaxPosition=itemOfTodayMaxPosition;
    }
    /**
     * To know if item at position position is displaying today
     * @param position
     * @return
     */
    private boolean isToday(int position) {
        return itemOfTodayMinPosition<=position
                &&position<=itemOfTodayMaxPosition;
    }
}
