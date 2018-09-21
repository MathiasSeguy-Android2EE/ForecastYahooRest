package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.current.weather;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.model.Weather;
import com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.MotherCardView;

/**
 * Created by Marion Aubard on 14/06/2018.
 */
public class WeatherCardView extends MotherCardView {

    /***********************************************************
     *  Attributes
     **********************************************************/

    private ImageView ivIco;
    private TextView tvDescription;
    private TextView tvMain;

    /***********************************************************
     *  Constructors
     **********************************************************/
    public WeatherCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public WeatherCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeatherCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        initObservers();
    }

    private void initViews() {
        ivIco = findViewById(R.id.imv_ico);
        tvDescription = findViewById(R.id.txv_description);
        tvMain = findViewById(R.id.txv_main);
    }

    @Override
    protected void initObservers() {
        //TODO
    }

    public void updateWith(@NonNull Weather weather) {
        // TODO set ico
        if(weather!=null) {
            if(tvMain==null){
                initViews();
            }
            tvMain.setText(weather.getMain()+":"+weather.getIcon());
//            txvDescription.setText(weather.getDescription());
            tvDescription.setText("4: "+contextId);
            //TODO
            //imv_ico.setImageBitmap(PictureCacheDownloader.loadPictureFromDisk(weather.getIcon()));
        }else{

            tvMain.setText("Null");
            tvDescription.setText("4: "+contextId);
        }
    }

}
