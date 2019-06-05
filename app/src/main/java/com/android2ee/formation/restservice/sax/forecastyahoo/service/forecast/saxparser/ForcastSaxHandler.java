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
package com.android2ee.formation.restservice.sax.forecastyahoo.service.forecast.saxparser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model.YahooForcast;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        This class aims to parse the following xml schema:
 *        <rss><channel><item>
 *        <yweather:condition text="Cloudy" code="26" temp="55"
 *        date="Wed, 26 Sep 2012 11:00 am CEST" />
 *        <yweather:forecast day="Wed" date="26 Sep 2012" low="49" high="67" text="AM Showers"
 *        code="39" />
 *        </item></channel></rss><
 */
public class ForcastSaxHandler extends DefaultHandler {
	/**
	 * The boolean to know if we are in a Forecast block
	 */
	Boolean inFCBlock = false;
	/**
	 * The list of Forecast
	 */
	List<YahooForcast> forecasts;
	/**
	 * The current forecast being parse and built
	 */
	YahooForcast currentforecast;
	/**
	 * The current value of the element
	 */
	String date, temp, templ, temph, text, code;
	/**
	 * The tag to use for the LogCat
	 */
	String tag = "ForcastSaxHandler";
	/******************************************************************************************/
	/** Constants **************************************************************************/
	/******************************************************************************************/

	/**
	 * The string constant that are the name of the xml elements of the document
	 */
	private final String FOR = "yweather:condition", DATE = "date", TEMP = "temp", ICO = "code", TENDANCE = "text";
	// <yweather:forecast day="Wed" date="26 Sep 2012" low="49" high="67" text="AM Showers"
	// code="39" />
	private final String FORECAST = "yweather:forecast", LOW = "low", HIGH = "high";

	/******************************************************************************************/
	/** Managing The Document parsing **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		// instanciate the list of forecast you want to parse
		forecasts = new ArrayList<YahooForcast>();
	}

	/******************************************************************************************/
	/** Managing the begin and the end of a block **************************************************************************/
	/******************************************************************************************/

	/**
	 * This is a trick because I don't understand how to parse the date return by yahoo
	 * So first element is day 0 and then I increment
	 * In the YahooForcast constructor 0 is today, 1 tomorrow and so on
	 */
	int dayCount=0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String,
	 * java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		Log.v(tag,
				"[startElement] uri :" + uri + ", localName :" + localName + ", qName: " +
						qName + ", Attributes: " + attributes.getLength() + ", attributes.getValue(qName) :"
						+ attributes.getValue(qName));
		// Log.e(tag, "[startElement] uri :" + uri );

		if (qName.equals(FOR)) {
			// then we begin a new forecast block, so instanciate the new forcastElement
			temp = attributes.getValue(TEMP);
			text = attributes.getValue(TENDANCE);
			code = attributes.getValue(ICO);
			currentforecast = new YahooForcast(text, code, temp);
			forecasts.add(currentforecast);
		} else if (qName.equals(FORECAST)) {

			// <yweather:forecast day="Wed" date="26 Sep 2012" low="49" high="67" text="AM Showers"
			// code="39" />
			// then we begin a new forecast block, so instanciate the new forcastElement
			date = attributes.getValue(DATE);
			templ = attributes.getValue(LOW);
			temph = attributes.getValue(HIGH);
			text = attributes.getValue(TENDANCE);
			code = attributes.getValue(ICO);
			currentforecast = new YahooForcast(text, code, templ, temph, dayCount);
			dayCount++;
			forecasts.add(currentforecast);
		}

	}

	/**
	 * @return the list of forecasts
	 */
	public List<YahooForcast> getForecasts() {
		return forecasts;
	}
}
