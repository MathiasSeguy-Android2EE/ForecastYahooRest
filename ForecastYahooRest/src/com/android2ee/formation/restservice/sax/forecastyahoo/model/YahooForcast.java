/**<ul>
 * <li>YahooForecastWebServiceSax</li>
 * <li>com.android2ee.tuto.restservice.sax.yahooforecast</li>
 * <li>26 sept. 2012</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.formation.restservice.sax.forecastyahoo.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to be the YahooForcast POJO representation
 */
public class YahooForcast implements Parcelable {
	/**
	 * The tendance cloudy, sunny
	 */
	private String tendance;
	/**
	 * The code image to be added to the url.http://l.yimg.com/a/i/us/we/52/codeImage.gif
	 */
	private String codeImage;
	/**
	 * The real image
	 */
	private Drawable image;
	/**
	 * The temperature in celsius
	 */
	private int temp=-1000;
	/**
	 * The temperature in celsius
	 */
	private int tempMin=-1000;
	/**
	 * The temperature in celsius
	 */
	private int tempMax=-1000;
	/**
	 * The date
	 */
	private Calendar date;
	/**
	 * The date
	 */
	private String strDate;
	/**
	 * The logCat's tag
	 */
	private final String tag = "ForecastActivity:YahooForcast";
	/**
	 * Image url
	 */
	private final String forcast_image_url="http://l.yimg.com/a/i/us/we/52/%1$s.gif";
	
	/**
	 * @param tendance
	 * @param codeImage
	 * @param temp
	 * @param date
	 */
	public YahooForcast(String tendance, String codeImage, String temp, String date) {
		super();
		//set the attributes
		this.tendance = tendance;
		this.codeImage = codeImage;
		this.image=getPicture(getIconUrl(codeImage));
		this.temp = Integer.valueOf(temp);
		//try to parse the date
		// Wed, 26 Sep 2012 11:00 am CEST
		// 26 Sep 2012
		DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm a");
		Log.e("YahooForcast", "date to set " + date.substring(0, 25));
		try {
			Date parsed = df.parse(date.substring(0, 25));
			Calendar newCalendar = Calendar.getInstance();
			newCalendar.setTime(parsed);
			this.date = newCalendar;
			Log.e("YahooForcast", "date set");
		} catch (ParseException e) {
			strDate = date.substring(0, 13);
			Log.e("YahooForcast", "date not set");
			e.printStackTrace();
		}

	}

	/**
	 * @param tendance
	 * @param codeImage
	 * @param tempMin
	 * @param tempMax
	 * @param strDate
	 */
	public YahooForcast(String tendance, String codeImage, String tempMin, String tempMax, String strDate) {
		super();
		//set the attributes
		this.tendance = tendance;
		this.codeImage = codeImage;
		this.image=getPicture(getIconUrl(codeImage));
		this.tempMin = Integer.valueOf(tempMin);
		this.tempMax = Integer.valueOf(tempMax);
		this.strDate = strDate;
		//try to parse the date
		DateFormat df = new SimpleDateFormat("dd MMM yyyy");
		try {
			Date parsed = df.parse(strDate);
			Calendar newCalendar = Calendar.getInstance();
			newCalendar.setTime(parsed);
			this.date = newCalendar;
			Log.e("YahooForcast", "date set");
		} catch (ParseException exc) {
			this.strDate = strDate;
			Log.e("YahooForcast", "date not set",exc);
		}
	}
	

	/**
	 * @param tendance
	 * @param codeImage
	 * @param temp
	 * @param date
	 */
	public YahooForcast(String tendance, String codeImage, int temp, Calendar date) {
		super();
		//set the attributes
		this.tendance = tendance;
		this.codeImage = codeImage;
		this.image=getPicture(getIconUrl(codeImage));
		this.temp = temp;
		this.date = date;
	}
	/******************************************************************************************/
	/** Image management **************************************************************************/
	/******************************************************************************************/

	
	/**
	 * This method retrieve the drawable associated to the URL
	 * 
	 * @param urlPath
	 *            the url of the picture
	 * @return the drawable downloaded from the url
	 */
	private Drawable getPicture(String urlPath) {
		// the drawable to return
		Drawable drawable = null;
		try {
			// retrieve the URL
			URL url = new URL(urlPath);
			// Open an input stream on it
			InputStream is = (InputStream) url.getContent();
			// build the drawable from that input stream
			drawable = Drawable.createFromStream(is, "src");
		} catch (IOException e) {
			Log.e(tag, e.toString());
			e.printStackTrace();
		}
		return drawable;
	}

	/**
	 * @param code
	 * @return
	 */
	public String getIconUrl(String code) {
		return String.format("http://l.yimg.com/a/i/us/we/52/%1$s.gif", code);
	}
	
	/******************************************************************************************/
	/** Getters/setters **************************************************************************/
	/******************************************************************************************/


	/**
	 * Build the object using the Xml
	 * 
	 * @param xml
	 */
	public YahooForcast(String xml) {
		// Todo

	}

	/**
	 * @return the tendance
	 */
	public final String getTendance() {
		return tendance;
	}

	/**
	 * @param tendance
	 *            the tendance to set
	 */
	public final void setTendance(String tendance) {
		this.tendance = tendance;
	}

	/**
	 * @return the codeImage
	 */
	public final String getCodeImage() {
		return codeImage;
	}

	/**
	 * @param codeImage
	 *            the codeImage to set
	 */
	public final void setCodeImage(String codeImage) {
		this.codeImage = codeImage;
	}

	/**
	 * @return the temp
	 */
	public final int getTemp() {
		return temp;
	}

	/**
	 * @param temp
	 *            the temp to set
	 */
	public final void setTemp(int temp) {
		this.temp = temp;
	}

	/**
	 * @return the date
	 */
	public final Calendar getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public final void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * @return the tempMin
	 */
	public final int getTempMin() {
		return tempMin;
	}

	/**
	 * @param tempMin the tempMin to set
	 */
	public final void setTempMin(int tempMin) {
		this.tempMin = tempMin;
	}

	/**
	 * @return the tempMax
	 */
	public final int getTempMax() {
		return tempMax;
	}

	/**
	 * @param tempMax the tempMax to set
	 */
	public final void setTempMax(int tempMax) {
		this.tempMax = tempMax;
	}

	/**
	 * @return the strDate
	 */
	public final String getStrDate() {
		return strDate;
	}

	/**
	 * @param strDate the strDate to set
	 */
	public final void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	/**
	 * @return the image
	 */
	public final Drawable getImage() {
		return image;
	}
	

    protected YahooForcast(Parcel in) {
        tendance = in.readString();
        codeImage = in.readString();
        image = (Drawable) in.readValue(Drawable.class.getClassLoader());
        temp = in.readInt();
        tempMin = in.readInt();
        tempMax = in.readInt();
        date = (Calendar) in.readValue(Calendar.class.getClassLoader());
        strDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tendance);
        dest.writeString(codeImage);
        dest.writeValue(image);
        dest.writeInt(temp);
        dest.writeInt(tempMin);
        dest.writeInt(tempMax);
        dest.writeValue(date);
        dest.writeString(strDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<YahooForcast> CREATOR = new Parcelable.Creator<YahooForcast>() {
        @Override
        public YahooForcast createFromParcel(Parcel in) {
            return new YahooForcast(in);
        }

        @Override
        public YahooForcast[] newArray(int size) {
            return new YahooForcast[size];
        }
    };
}
