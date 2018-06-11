/**
 * <ul>
 * <li>RecyclerViewAdapter</li>
 * <li>com.android2ee.recyclerview.fragments</li>
 * <li>12/10/2015</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.fragment.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.forecast.WeatherForecast;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.fragment.WeatherCityPresenterIntf;

/**
 * Created by Mathias Seguy - Android2EE on 12/10/2015.
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder>{
    /***********************************************************
     * Attributes
     **********************************************************/
    private WeatherCityPresenterIntf presenter;
    private LayoutInflater inflater;
    /***********************************************************
     *  TezmpVariables
     *********************************************************/
    private View tNewView;
    private ViewHolder tViewHolder;
    private WeatherForecast weather;
    /***********************************************************
     * Constructor
     **********************************************************/
    public WeatherRecyclerViewAdapter(WeatherCityPresenterIntf presenter, Context ctx){
        this.presenter=presenter;
        inflater=LayoutInflater.from(ctx);
    }


    /**    Create the new View and clue it with the return ViewHolder *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public WeatherRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // Log.e("RecyclerViewAdapter"," onCreateViewHolder viewtype="+viewType);
        tNewView=inflater.inflate(R.layout.weather_cardview_item,parent,false);
        tViewHolder=new ViewHolder(tNewView);
        return tViewHolder;
    }

    /**    Update the View holds by the ViewHolder gave *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(WeatherRecyclerViewAdapter.ViewHolder holder, int position) {
        weather=presenter.getCityForecast().getWeatherForecasts().get(position);
        holder.getTxvName().setText(Float.toString(weather.getWeatherDetails().getTemp())+"°C");
        holder.getTxvFirstName().setText(Float.toString(weather.getWeatherDetails().getHumidity()));
        holder.getTxvMessage().setText(weather.getDateTimeUtc());
        holder.position=position;
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return presenter.getCityForecast()==null?0:presenter.getCityForecast().getWeatherForecasts().size();
    }
    /***********************************************************
     * ViewHolder pattern
     **********************************************************/
    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txvName=null;
        TextView txvFirstName=null;
        TextView txvMessage=null;
        //TODO do something delete or use
        View.OnClickListener clickListener;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            txvName= (TextView) itemView.findViewById(R.id.txvName);
            txvFirstName= (TextView) itemView.findViewById(R.id.txvFirstName);
            txvMessage= (TextView) itemView.findViewById(R.id.txvMessage);
            clickListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTxvMessageVisibilityState();
                }
            };
            itemView.setOnClickListener(clickListener);

        }

        public TextView getTxvFirstName() {
            return txvFirstName;
        }

        public TextView getTxvMessage() {
            return txvMessage;
        }

        public TextView getTxvName() {
            return txvName;
        }
        public void changeTxvMessageVisibilityState(){
            if(txvMessage.getVisibility()==View.VISIBLE){
                txvMessage.setVisibility(View.GONE);
            }else{
                txvMessage.setVisibility(View.VISIBLE);
            }
        }
    }
}
