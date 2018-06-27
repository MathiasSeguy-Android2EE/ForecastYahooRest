package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherCardView;

public class MainCardView extends MotherCardView {
    private static final String TAG = "MainCardView";

    private static final float KELVIN_OFFSET_TO_CELSIUS = -273.15f;

    /***********************************************************
     *  Attributes
     **********************************************************/

    private TextView tvTemperature=null;
    private TextView tvTemperatureMin=null;
    private TextView tvTemperatureMax=null;
    private TextView tvHumidity=null;
    private TextView tvPressure=null;
    private ImageView ivDrop=null;
    private boolean isForecast;
    Observer<Main> mainObserver;
    /***********************************************************
     *  Constructors
     **********************************************************/
    public MainCardView(@NonNull Context context) {
        super(context);
    }

    public MainCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
        mainObserver = new Observer<Main>() {
            @Override
            public void onChanged(@Nullable Main main) {
                MyLog.e(TAG, "MainObserver on changed with id="+contextId+" and main"+main+" and view is "+MainCardView.this.hashCode());
                if (main != null) {
                    updateWith(main);
                }else{
                    //observe again, somethinks is wrong
                    observeAgain();
                }
            }
        };

    }



    /***********************************************************
     *  ModelView management
     **********************************************************/

    @Override
    public Class<MainViewModel> getCardViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public String getCardViewModelKey() {
        return MainCardView.class.getName();
    }

    @Override
    @NonNull
    protected ViewModelProvider.Factory getCardViewFactory() {
        return new MainModelFactory(contextId, isForecast);
    }

    /***********************************************************
     *  Private methods
     **********************************************************/



    private void initViews() {
        if(tvTemperature==null) {
            tvTemperature = findViewById(R.id.tv_temperature);
            tvTemperatureMin = findViewById(R.id.tv_min_temperature);
            tvTemperatureMax = findViewById(R.id.tv_max_temperature);
            tvHumidity = findViewById(R.id.tv_humidity);
            tvPressure = findViewById(R.id.tv_pressure);
            ivDrop = findViewById(R.id.iv_drop);
        }
    }

    @Override
    protected void initObservers() {
        initViews();
        MainViewModel viewModel = (MainViewModel) getViewModel();
        MyLog.e(TAG,"InitObserver called with "+contextId+" bind with the viewModel "+viewModel);
        //An important trick here: if you don't remove the observer,
        //you will be notified from the old stream and the new stream, so:
        //remove the observer
        if(viewModel.getMainLiveData(hashCode())!=null){
            viewModel.getMainLiveData(hashCode()).removeObserver(mainObserver);
        }
        //Observe again
        viewModel.getMainLiveData(hashCode(),contextId).observe(activity, mainObserver);
    }
    public void observeAgain(){
        MainViewModel viewModel = (MainViewModel) getViewModel();
        MyLog.e(TAG,"observeAgain called with "+contextId+" bind with the viewModel "+viewModel);
        //Observe again
        viewModel.getMainLiveData(hashCode()).observe(activity, mainObserver);
    }
    private void updateWith(@NonNull Main main) {
        MyLog.e(TAG,"Data has been updated for "+contextId);
        tvTemperature.setText(getContext().getString(R.string.main_temperature, main.getTemp() + KELVIN_OFFSET_TO_CELSIUS));
        tvTemperatureMin.setText(getContext().getString(R.string.main_temperature_min, main.getTempMin() + KELVIN_OFFSET_TO_CELSIUS));
        tvTemperatureMax.setText(getContext().getString(R.string.main_temperature_max, main.getTempMax() + KELVIN_OFFSET_TO_CELSIUS));
        tvHumidity.setText(getContext().getString(R.string.main_humidity, main.getHumidity()));
//        tvPressure.setText(getContext().getString(R.string.main_pressure, main.getPressure()));
        tvPressure.setText(""+contextId);
//        tvHumidity.setText("id="+main.get_id());
    }

    /**
     * Define if it's a forecats context
     * @param forecast
     */
    public void setIsForecast(boolean forecast) {
        isForecast = forecast;
    }

    /***********************************************************
     *  Accessor
     **********************************************************/
    public TextView getTvTemperature() {
        return tvTemperature;
    }

    public TextView getTvTemperatureMin() {
        return tvTemperatureMin;
    }

    public TextView getTvTemperatureMax() {
        return tvTemperatureMax;
    }

    public ImageView getIvDrop() {
        return ivDrop;
    }
}
