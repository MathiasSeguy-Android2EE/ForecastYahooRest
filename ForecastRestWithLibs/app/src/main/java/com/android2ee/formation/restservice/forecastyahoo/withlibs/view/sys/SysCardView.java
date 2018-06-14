package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.sys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class SysCardView extends CardView {

    private TextView tvCountry;
    private TextView tvSunrise;
    private TextView tvSunset;

    public SysCardView(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SysCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public SysCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        tvCountry = findViewById(R.id.tv_country);
        tvSunrise = findViewById(R.id.tv_sunrise);
        tvSunset = findViewById(R.id.tv_sunset);
    }
}
