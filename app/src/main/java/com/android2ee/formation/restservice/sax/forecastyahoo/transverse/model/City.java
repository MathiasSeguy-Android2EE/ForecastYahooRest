/**<ul>
 * <li>ForecastYahooRest</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model</li>
 * <li>24 oct. 2014</li>
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
package com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to:
 * <ul><li></li></ul>
 */
public class City implements Parcelable {
	/**
	 * The name of the city
	 */
	String name=null;
	/**
	 * The id of the city for yahoo as an int 
	 */
	String woeid=null;
	/**
	 * The place type (Town,...)
	 */
	String placeType=null;
	/**
	 * The country
	 */
	String country=null;
	/**
	 * The latitude  of the city
	 */
	String latitude=null;
	/**
	 * The  longitude of the city
	 */
	String longitude=null;
	
	/**
	 * @param name
	 * @param woeid
	 */
	public City(String name, String woeid) {
		super();
		this.name = name;
		this.woeid = woeid;
	}
	/**
	 * @param name
	 * @param woeid
	 * @param placeType
	 * @param country
	 */
	public City(String name, String woeid, String placeType, String country,String latitude,String longitude) {
		super();
		this.name = name;
		this.woeid = woeid;
		this.placeType = placeType;
		this.country = country;
		this.latitude=latitude;
		this.longitude=longitude;
	}
	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the woeid
	 */
	public final String getWoeid() {
		return woeid;
	}
	/**
	 * @param woeid the woeid to set
	 */
	public final void setWoeid(String woeid) {
		this.woeid = woeid;
	}
	
	/**
	 * @return the placeType
	 */
	public final String getPlaceType() {
		return placeType;
	}
	/**
	 * @param placeType the placeType to set
	 */
	public final void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	/**
	 * @return the country
	 */
	public final String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public final void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return the latitude
	 */
	public final String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public final void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public final String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public final void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name+" ("+placeType+") "+country+" ["+woeid+"]";
	}


    protected City(Parcel in) {
        name = in.readString();
        woeid = in.readString();
        placeType = in.readString();
        country = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(woeid);
        dest.writeString(placeType);
        dest.writeString(country);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
