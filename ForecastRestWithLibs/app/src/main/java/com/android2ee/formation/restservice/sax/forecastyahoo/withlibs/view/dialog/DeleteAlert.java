package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;

import java.lang.ref.WeakReference;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 20/06/2018.
 */
public class DeleteAlert  extends DialogFragment {
    WeakReference<DeletionCallBack> deletionCallBack;

    public DeleteAlert(){
        //empty constructor is mandatory
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("");
        builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(deletionCallBack!=null){
                    DeletionCallBack callBack=deletionCallBack.get();
                    if(callBack!=null){
                        callBack.deleteCurrentCity();
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.btn_no, null);
        return builder.create();
    }

    public void setDeletionCallBack(DeletionCallBack deletionCallBack) {
        this.deletionCallBack = new WeakReference<>(deletionCallBack);
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link android.app.Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        String cityName="Unknown";
        if(deletionCallBack!=null){
            DeletionCallBack callBack=deletionCallBack.get();
            if(callBack!=null){
                cityName=callBack.getCurrentCityName();
            }
        }
        ((AlertDialog)getDialog()).setMessage(getString(R.string.alertdialog_message,cityName));
    }

    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * Interface to be implemented by those who wants to display the DialogFragment
     */
    public interface DeletionCallBack{
        /**
         * Delete the current city
         */
        void deleteCurrentCity();

        /**
         * @return Current city name
         */
        String getCurrentCityName();
    }
}
