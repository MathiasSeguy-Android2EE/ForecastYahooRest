package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.application;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 04/09/2018.
 * This class is responsible for tracking updates of the data
 * There should be always only one instance in the database of this class
 */
@Entity(tableName="weather_update")
public class WeatherUpdate {
    /***********************************************************
    *  Attributes
    **********************************************************/
    @PrimaryKey(autoGenerate = true)
    private long _id_update;
    /**
     * The date in long (millis android system)
     * of the last time an update was tried in the database
     */
    @ColumnInfo(name = "last_time_update_tried_millis")
    private long timeInMillis;
    /**
     * To know if the last update succeed
     */
    @ColumnInfo(name = "succeed")
    private boolean succeed;

    /***********************************************************
    *  Getters/Setters
    **********************************************************/

    public long get_id_update() {
        return _id_update;
    }

    public void set_id_update(long _id_update) {
        this._id_update = _id_update;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherUpdate)) return false;

        WeatherUpdate that = (WeatherUpdate) o;

        if (get_id_update() != that.get_id_update()) return false;
        if (getTimeInMillis() != that.getTimeInMillis()) return false;
        return isSucceed() == that.isSucceed();
    }

    @Override
    public int hashCode() {
        int result = (int) (get_id_update() ^ (get_id_update() >>> 32));
        result = 31 * result + (int) (getTimeInMillis() ^ (getTimeInMillis() >>> 32));
        result = 31 * result + (isSucceed() ? 1 : 0);
        return result;
    }
}
