/**<ul>
 * <li>ForecastYahooRest</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.service.city.jsonparser</li>
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
package com.android2ee.formation.restservice.sax.forecastyahoo.service.city.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManaged;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.City;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to parse the following JSON
 *        {"places":
 *        {"place":
 *        [{"woeid":44418,
 *        "placeTypeName":"Town",
 *        "placeTypeName attrs":{"code":7},
 *        "name":"London",
 *        "country":"United Kingdom",
 *        "country attrs":{"type":"Country","code":"GB","woeid":23424975},
 *        "admin1":"England",
 *        "admin1 attrs":{"type":"Country","code":"GB-ENG","woeid":24554868},
 *        "admin2":"Greater London","admin2 attrs":{"type":"County","code":"GB-LND","woeid":23416974
 *        },
 *        "admin3":"",
 *        "locality1":"London",
 *        "locality1 attrs":{"type":"Town","woeid":44418},
 *        "locality2":"",
 *        "postal":"",
 *        "centroid":{"latitude":51.507702,
 *        "longitude":-0.12797},
 *        "boundingBox":
 *       		 {"southWest":{"latitude":51.286839,"longitude":-0.51035},
 *       		 "northEast":{"latitude":51.692322,"longitude":0.33403}},
 *        "areaRank":7,
 *        "popRank":13,
 *        "timezone":"Europe\/London",
 *        "timezone attrs":{"type":"Time Zone","woeid":28350903},"uri":
 *        "http:\/\/where.yahooapis.com\/v1\/place\/44418","lang":"en-US"}],
 *        "start":0,
 *        "count":1,
 *        "total":10}}
 */
public class JsonCitiesParser {
	/**
	 * 
	 */
	String tag = "DirectionsJSONParser";

	/**
	 * Parse the following Json and return the cities associated
	 * 
	 * @param jObject
	 * @return
	 */
	public static List<City> parse(JSONObject jObject, City searchedCity) {
		ArrayList<City> cities = new ArrayList<City>();
		JSONArray jCities;
		JSONObject jCity;
		JSONObject jCentroid;
		JSONObject jPlaces;
		int woeid;
		String placeType,country,name,lat,lon;
		String cityName = searchedCity.getName();
		try {
			jPlaces = jObject.getJSONObject("places");
			jCities = jPlaces.getJSONArray("place");

			for (int i = 0; i < jCities.length(); i++) {
				jCity = jCities.getJSONObject(i);
				woeid = (Integer) jCity.get("woeid");
				placeType=jCity.getString("placeTypeName");
				country=jCity.getString("country");
				name=jCity.getString("name");
				jCentroid=jCity.getJSONObject("centroid");
				lat=jCentroid.getString("latitude");
				lon=jCentroid.getString("longitude");
				Log.e("JsonCitiesParser ", "parsing found new woeid " + woeid);
				cities.add(new City(name,Integer.toString(woeid),placeType,country,lat,lon));
			}
		} catch (JSONException e) {
			ExceptionManager.manage(new ExceptionManaged(JsonCitiesParser.class, R.string.exc_parsing_city, e));
		}
		return cities;
	}
}
