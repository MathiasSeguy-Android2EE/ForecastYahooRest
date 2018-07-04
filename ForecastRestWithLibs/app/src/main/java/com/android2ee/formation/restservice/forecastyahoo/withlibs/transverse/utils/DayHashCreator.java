package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 03/07/2018.
 */
public class DayHashCreator {
/***********************************************************
 *  Key representing exactly the date as int YYYYDDD 2018123 (123° days of 2018)
 *  This is our unicity key in fact
 **********************************************************/
private static Calendar calendarTemp=null;
    /**
     * @param unixTime
     * @return YYYYDDD int reprsentation of the date of the day
     */
    public static int getTempKeyFromDay(long unixTime) {
        if(calendarTemp==null){
            calendarTemp=new GregorianCalendar();
        }
        calendarTemp.setTimeInMillis(unixTime*1000);
        return getTempKeyFromDay(calendarTemp);
    }

    /**
     * YYYYDDD int reprsentation of the date
     *
     * @param day
     * @return YYYYDDD int reprsentation of the date
     */
    public static int getTempKeyFromDay(Calendar day) {
        return day.get(Calendar.YEAR) * 1000 + day.get(Calendar.DAY_OF_YEAR);
    }


}
