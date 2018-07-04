package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.current.main;

import android.arch.lifecycle.LiveData;
import android.util.SparseArray;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.ForecastDatabase;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherViewModel;

public class MainViewModel extends MotherViewModel {
    private static final String TAG = "MainViewModel";
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * ContextType:
     * Can be either current to retrieve Main object associated with WeatherData
     * Or can be Main object associated with WeatherForecastItem
     */
    public final static int CURRENT_CONTEXT=110274,FORECAST_CONTEXT=4112008;

    /**
     * The id in DB of the context (context is WeatherData (current) or WeatherForecastItem (forecast)
     */
    protected Long contextId;
    /**
     * The context Type: CURRENT_CONTEXT or FORECAST_CONTEXT
     */
    protected int contextType;

    /**
     * HashMap to map viewId with LiveData they observe
     */
    SparseArray<LiveData<Main>> viewToLiveData = new SparseArray<>();
    /**
     * HashMap to link viewId and contextId displayed by it
     */
    SparseArray<Long> viewToContextId = new SparseArray<>();
    /***********************************************************
    *  Constructors
    **********************************************************/

    /**
     * Constructor
     * @param contextId
     * @param contextType
     */
    MainViewModel(long contextId,int contextType) {
        MyLog.e(TAG,"MainViewModel for id="+contextId+" contextType="+(contextType==CURRENT_CONTEXT?"CURRENT_CONTEXT":"FORECAST_CONTEXT")+" and cst is "+contextType);
        this.contextId = contextId;
        this.contextType=contextType;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        viewToLiveData=null;
        viewToContextId=null;
    }
    /***********************************************************
     *  Business Methods
     **********************************************************/
    public LiveData<Main> getMainLiveData(int viewId) {
        return viewToLiveData.get(viewId);
    }
    /**
     * Find the main Data you need
     * @return the main observer
     */
    public LiveData<Main> getMainLiveData(int viewId,long requestedContextId) {
        LiveData<Main> mainLiveData=viewToLiveData.get(viewId);
        Long contextId=viewToContextId.get(viewId,null);
        MyLog.e(TAG,"getMainLiveData for id="+contextId+" viewId="+viewId+", requestedContextId"+requestedContextId);
        //initialization case && switch contextId case
        if(contextId==null||mainLiveData==null||contextId!=requestedContextId){
            MyLog.e(TAG,"in the if with contextId="+contextId+", mainLiveData="+mainLiveData+", requestedContextId"+requestedContextId);
            //initialization case: mainLiveData==null
            contextId=requestedContextId;
            MyLog.e(TAG,"getMainLiveData for id="+contextId+" contextType="+(contextType==CURRENT_CONTEXT?"CURRENT_CONTEXT":"FORECAST_CONTEXT")+" and cst is "+contextType);
            if(contextType==CURRENT_CONTEXT) {
                mainLiveData = ForecastDatabase.getInstance()
                        .getMainDao()
                        .loadLiveDataMainForWeatherData(contextId);
            }else if(contextType==FORECAST_CONTEXT){
                mainLiveData = ForecastDatabase.getInstance()
                        .getMainDao()
                        .loadLiveDataMainForWeatherForecastItem(contextId);
            }
            viewToContextId.put(viewId,contextId);
            viewToLiveData.put(viewId,mainLiveData);
        }

        MyLog.e(TAG,"getMainLiveData return mainLiveData="+mainLiveData+"");
        return mainLiveData;
    }



}