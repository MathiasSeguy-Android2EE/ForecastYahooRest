package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.sys;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.serverside.Main;
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

    /***********************************************************
     *  Constructors
     **********************************************************/
    public SysCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public SysCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SysCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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

    private void init() {
        initViews();
    }

    private void initViews() {
        tvCountry = findViewById(R.id.tv_country);
        tvSunrise = findViewById(R.id.tv_sunrise);
        tvSunset = findViewById(R.id.tv_sunset);
    }

    @Override
    protected void initObservers() {
        //TODO
    }

    @Override
    protected void removeObservers() {
        //TODO
    }

    private void updateWith(@NonNull Main main) {
        // TODO
    }

}
