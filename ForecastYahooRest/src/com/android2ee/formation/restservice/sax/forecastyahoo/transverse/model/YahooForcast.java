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
package com.android2ee.formation.restservice.sax.forecastyahoo.transverse.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android2ee.formation.restservice.sax.forecastyahoo.MyApplication;
import com.android2ee.formation.restservice.sax.forecastyahoo.R;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManaged;
import com.android2ee.formation.restservice.sax.forecastyahoo.transverse.exceptions.ExceptionManager;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to be the YahooForcast POJO representation
 */
public class YahooForcast implements Parcelable {
	/**
	 * 
	 */
	public static final int EMPTY_TEMP_VALUE = -1000;
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
	private int temp = EMPTY_TEMP_VALUE;
	/**
	 * The temperature in celsius
	 */
	private int tempMin = EMPTY_TEMP_VALUE;
	/**
	 * The temperature in celsius
	 */
	private int tempMax = EMPTY_TEMP_VALUE;
	/**
	 * The date
	 */
	private Calendar date;
	/**
	 * The logCat's tag
	 */
	private final String tag = "ForecastActivity:YahooForcast";

	/**
	 * Constructor used by ForcastSaxHandler for the current day
	 * 
	 * @param tendance
	 * @param codeImage
	 * @param temp
	 */
	public YahooForcast(String tendance, String codeImage, String temp) {
		super();
		// set the attributes
		this.tendance = tendance;
		this.codeImage = codeImage;
		this.image = getPicture(getIconUrl(codeImage), codeImage);
		this.temp = Integer.valueOf(temp);
		this.date = Calendar.getInstance();
	}

	/**
	 * Constructor used by ForcastSaxHandler for the forecast days
	 * 
	 * @param tendance
	 * @param codeImage
	 * @param tempMin
	 * @param tempMax
	 * @param strDate
	 */
	public YahooForcast(String tendance, String codeImage, String tempMin, String tempMax, int dayCount) {
		super();
		// set the attributes
		this.tendance = tendance;
		this.codeImage = codeImage;
		this.image = getPicture(getIconUrl(codeImage), codeImage);
		this.tempMin = Integer.valueOf(tempMin);
		this.tempMax = Integer.valueOf(tempMax);
		this.date = Calendar.getInstance();
		this.date.add(Calendar.DATE, dayCount);

	}

	/**
	 * The constructor used by the ForecastDao
	 * 
	 * @param strDate
	 * @param tendance
	 * @param codeImage
	 * @param tempMin
	 * @param tempMax
	 * @param temp
	 */
	public YahooForcast(Calendar date, String tendance, String codeImage, int tempMin, int tempMax, int temp) {
		super();
		// set the attributes
		this.tendance = tendance;
		this.codeImage = codeImage;
		this.image = getPicture(getIconUrl(codeImage), codeImage);
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.temp = temp;
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder("Forcast : ");
		strb.append("tendance = " + tendance + " ,");
		strb.append("codeImage = " + codeImage + " ,");
		strb.append("tempMin = " + tempMin + " ,");
		strb.append("tempMax = " + tempMax + " ,");
		strb.append("temp = " + temp + " ,");
		strb.append("date = " + date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.MONTH) + 1 + "");
		return strb.toString();
	}

	/******************************************************************************************/
	/** Image management **************************************************************************/
	/******************************************************************************************/

	/**
	 * This method retrieve the drawable associated to the URL or load it from the disk
	 * Why this method is not in a Thread ?
	 * The answer is clear:
	 * if you go to the internet to retrieve the picture then you are creating
	 * the picture for the first time, so you are already in a background thread (the one launched
	 * by ServiceUpdater)
	 * Else, the picture is already loaded on the disk, so you read it and more you also belongs to
	 * a background thread (the one launched by ServiceData).
	 * In both case, in fact, this methods is executing in a background thread.
	 * So i just avoid the Thread{ Thread {do the work}} absurd pattern
	 * 
	 * @param urlPath
	 *            the url of the picture
	 * @return the drawable downloaded from the url
	 */
	private Drawable getPicture(String urlPath, String name) {
		// the drawable to return
		Drawable drawable = null;
		// first check if the picture is not already stored
		drawable = loadPicture(name);
		// if null the picture is not already stored
		if (null == drawable) {
			// load it from the network
			try {
				// retrieve the URL
				URL url = new URL(urlPath);
				// Open an input stream on it
				InputStream is = (InputStream) url.getContent();
				// save the bitmap from that input stream and return the associated drawable
				drawable = savePicture(is, name);
			} catch (IOException e) {
				ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_picture_not_found, e));
			}
		} else {
			Log.v("YahooForcast", "loadPicture done with success");
		}
		return drawable;
	}

	/**
	 * Save the picture to avoid reload it each time
	 * And also to be able to unparcel when not network
	 * 
	 * @param is
	 *            The inputStream to save
	 * @param name
	 *            The name of the file
	 */
	private Drawable savePicture(InputStream is, String fileName) {
		try {
			Context ctx = MyApplication.instance;
			// Find the internal storage directory
			File filesDir = ctx.getFilesDir();
			// Retrieve the name of the subfolder where your store your picture
			// (You have set it in your string ressources)
			String pictureFolderName = ctx.getString(R.string.picture_folder_name);
			// then create the subfolder
			File pictureDir = new File(filesDir, pictureFolderName);
			// Check if this subfolder exists
			if (!pictureDir.exists()) {
				// if it doesn't create it
				pictureDir.mkdirs();
			}

			// Define the file to store your picture in
			File filePicture = new File(pictureDir, fileName);
			// Open an OutputStream on that file
			FileOutputStream fos = new FileOutputStream(filePicture);
			// and store the inputStream inside:
			final byte[] buffer = new byte[1024];
			int read;
			while ((read = is.read(buffer)) != -1) {
				fos.write(buffer, 0, read);
			}
			// The close properly your stream
			fos.flush();
			fos.close();
			// then read the file and return the drawable
			Bitmap bitmap = BitmapFactory.decodeFile(filePicture.getAbsolutePath());
			return new BitmapDrawable(ctx.getResources(), bitmap);
		} catch (FileNotFoundException e) {
			ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_picture_not_found_save, e));

		} catch (IOException e) {
			ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_picture_not_found_save, e));

		}
		return null;
	}

	/**
	 * Reload the picture associated with the file name
	 * 
	 * @param fileName
	 *            The name of the picture
	 * @return the Drawable representing that picture
	 */
	private Drawable loadPicture(String fileName) {
		Log.e("YahooForcast", "loadPicture called");
		Drawable drawable = null;
		try {
			Context ctx = MyApplication.instance;
			// Find the external storage directory
			File filesDir = ctx.getFilesDir();
			// Retrieve the name of the subfolder where your store your picture
			// (You have set it in your string ressources)
			String pictureFolderName = ctx.getString(R.string.picture_folder_name);
			// then create the subfolder
			File pictureDir = new File(filesDir, pictureFolderName);
			// Check if this subfolder exists
			if (!pictureDir.exists()) {
				// if it doesn't create it
				pictureDir.mkdirs();
			}
			// Define the file to store your picture in
			File filePicture = new File(pictureDir, fileName);

			Log.e("YahooForcast", "loadPicture fileName " + filePicture.getAbsolutePath());
			if (filePicture.exists()) {
				// Open an InputStream on that file
				// FileInputStream fis = new FileInputStream(filePicture);
				Bitmap bitmap = BitmapFactory.decodeFile(filePicture.getAbsolutePath());
				drawable = new BitmapDrawable(ctx.getResources(), bitmap);
				// return Drawable.createFromStream(fis, "src");
			}
		} catch (Exception e) {
			ExceptionManager.manage(new ExceptionManaged(this.getClass(), R.string.exc_picture_not_found, e));
		}
		return drawable;
	}

	/**
	 * From Drawable To Bitmap
	 * Not used but nice code so I keep it
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitmap(Drawable drawable) {
		// define the bitmap to return
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
				Config.ARGB_8888);
		// create the canvas based on it
		Canvas canvas = new Canvas(bitmap);
		// define the derawable size
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		// then try to draw a rect on the canvas for the background
		Paint paint = new Paint();
		paint.setColor(0xFF00FFFF);
		canvas.drawRect(new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), paint);
		// draw the drawable on the canvas and so on the bitmap
		drawable.draw(canvas);
		return bitmap;
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
	 * @param tempMin
	 *            the tempMin to set
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
	 * @param tempMax
	 *            the tempMax to set
	 */
	public final void setTempMax(int tempMax) {
		this.tempMax = tempMax;
	}

	/**
	 * @return the image
	 */
	public final Drawable getImage() {
		return image;
	}

	/******************************************************************************************/
	/** Parcel code part **************************************************************************/
	/******************************************************************************************/

	protected YahooForcast(Parcel in) {
		tendance = in.readString();
		codeImage = in.readString();
		image = loadPicture(codeImage);
		temp = in.readInt();
		tempMin = in.readInt();
		tempMax = in.readInt();
		date = (Calendar) in.readValue(Calendar.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(tendance);
		dest.writeString(codeImage);
		dest.writeInt(temp);
		dest.writeInt(tempMin);
		dest.writeInt(tempMax);
		dest.writeValue(date);
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
