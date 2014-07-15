/**<ul>
 * <li>ForecastYahooRest</li>
 * <li>com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model</li>
 * <li>11 juil. 2014</li>
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
 * This code is free for any usage but can't be distribute.</br>
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

import java.util.Comparator;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to sort the array list of yahooForecast
 */
public class YahooForecastComparator implements Comparator<YahooForcast> {
	@Override
	public int compare(YahooForcast o1, YahooForcast o2) {
		if(o1.getDate().compareTo(o2.getDate())!=0) {
			return o1.getDate().compareTo(o2.getDate());
		}else {
			//date are the same so it depends of forcast or current
			//forecast of the day has temp!=-1000 and min==-1000
			return Integer.compare(o1.getTempMin(), o2.getTempMin());
		}
	}
}
