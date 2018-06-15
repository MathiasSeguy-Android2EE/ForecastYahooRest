package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.sys;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.current.Sys;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.MotherCardView;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class SysCardView extends MotherCardView {

    /***********************************************************
     *  Attributes
     **********************************************************/

    private TextView tvCountry;
    private TextView tvSunrise;
    private TextView tvSunset;

    SysViewModel model;

    /***********************************************************
     *  Constructors
     **********************************************************/
    public SysCardView(@NonNull Context context) {
        super(context);
    }

    public SysCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SysCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    /***********************************************************
     *  ViewModel management
     *********************************************************/

    @Override
    public Class getCardViewModelClass() {
        return null;
    }

    @Override
    public String getCardViewModelKey() {
        return null;
    }

    @NonNull
    @Override
    protected ViewModelProvider.Factory getCardViewFactory() {
        return null;
    }

    /***********************************************************
     *  Private methods
     **********************************************************/

    private void onChangedLiveData(@Nullable Sys sys) {
        if (sys != null) {
            tvCountry.setText(sys.getCountry());
            tvSunrise.setText(sys.getSunrise());
            tvSunset.setText(sys.getSunset());
        }
    }

    private void initViews() {
        tvCountry = findViewById(R.id.tv_country);
        tvSunrise = findViewById(R.id.tv_sunrise);
        tvSunset = findViewById(R.id.tv_sunset);
    }

    @Override
    protected void initObservers() {
        model = ViewModelProviders.of(activity, new SysModelFactory(contextId)).get(SysViewModel.class);

        model.getLiveData().observe(activity, new Observer<Sys>() {
            @Override
            public void onChanged(@Nullable Sys sys) {
                onChangedLiveData(sys);
            }
        });

    }

    @Override
    protected void removeObservers() {
        //TODO
    }

    private void updateWith(@NonNull Main main) {
        // TODO
    }

}
