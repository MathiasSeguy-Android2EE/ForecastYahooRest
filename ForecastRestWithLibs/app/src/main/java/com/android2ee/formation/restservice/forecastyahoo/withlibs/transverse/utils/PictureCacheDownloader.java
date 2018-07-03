package com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.event.PictureLoadedEvent;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManaged;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.exception.ExceptionManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Mathias Seguy - Android2EE on 22/06/2017.
 * The goal of thei sclass is to download a picture on the internet if you don't
 * have already done it
 * Has to be called from a background threa
 */

public class PictureCacheDownloader {
    private static final String TAG = "PictureCacheDownloader";
    private static OkHttpClient client = null;




    public static Bitmap downloadPicture(String urlGetPicture) {
        MyLog.e(TAG, "downloadPicture picture of " + urlGetPicture);
        try {
            if (!TextUtils.isEmpty(urlGetPicture) && URLUtil.isNetworkUrl(urlGetPicture)) {
                Request request = new Request.Builder()
                        .url(urlGetPicture)
                        .get()
                        .build();
                if (client == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    client = builder.build();
                }
                Call postCall = client.newCall(request);
                Response response = postCall.execute();
                if (response.code() == 200) {
                    ResponseBody in = response.body();
                    InputStream is = in.byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    response.body().close();
                    // Now do a stuff, for example store it
                    return bitmap;
                }
            }
        }catch(IOException e){
            ExceptionManager.manage(new ExceptionManaged(PictureCacheDownloader.class, R.string.datacom_geticon_ioexc,e));
        }
        return null;
    }

    /**
     * How to save a Bitmap on the disk
     *
     * @param fileName
     * @param bitmap
     * @throws IOException
     */
    public static void savePicture(String fileName, Bitmap bitmap) {
        MyLog.e(TAG, "savePicture picture of " + fileName);
        //Second save the picture
        //--------------------------
        //Find the external storage directory
        File filesDir = MyApplication.instance.getCacheDir();
        //Retrieve the name of the subfolder where your store your picture
        //(You have set it in your string ressources)
        //then create the subfolder
        File pictureDir = new File(filesDir, FolderName.PICTURE_CACHE);
        //Check if this subfolder exists
        if (!pictureDir.exists()) {
            //if it doesn't create it
            pictureDir.mkdirs();
        }
        //Define the file to store your picture in
        File filePicture = new File(pictureDir, fileName);
        //Open an OutputStream on that file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePicture);
            //Write in that stream your bitmap in png with the max quality (100 is max, 0 is min quality)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            //The close properly your stream
            fos.flush();

            EventBus.getDefault().post(new PictureLoadedEvent(fileName));
        } catch (FileNotFoundException e) {
            ExceptionManager.manage(new ExceptionManaged(PictureCacheDownloader.class, R.string.picdownload_save_fnfexc,e));
        } catch (IOException e) {
            ExceptionManager.manage(new ExceptionManaged(PictureCacheDownloader.class, R.string.picdownload_save_ioexc,e));
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    ExceptionManager.manage(new ExceptionManaged(PictureCacheDownloader.class, R.string.picdownload_save_ioexc,e));
                }
            }
        }
    }





    // /******************************************************************************************/
    /** Method name: loadPictureFromDisk
     /* Description :  **********/
    /* Param: type:String name:fileName
    /* Return: type:MutableLiveData<Bitmap> name: bitmapLvDt
     */
    /******************************************************************************************/

    /**
     * Should only be called from a background thread (So only by another Service's method)
     * Don't ever call this method from the UI Thread
     */
    public static MutableLiveData<Bitmap> loadPictureFromDiskSync(String fileName, MutableLiveData<Bitmap> bitmapLvDt) {
        //return
        MutableLiveData<Bitmap> returned;
        //null case
        if (bitmapLvDt == null) {
            returned = new MutableLiveData<>();
        } else {
            returned = bitmapLvDt;
        }
        Bitmap bitmapLvDt_value=loadPictureFromDisk(fileName);
        savePicture(fileName,bitmapLvDt_value);
        //Assign the result to the MutableLiveData
        bitmapLvDt.postValue(bitmapLvDt_value);
        return bitmapLvDt;

    }
    public static Bitmap loadPictureFromDisk(String fileName) {
        MyLog.e(TAG, "loadPictureFromDisk picture of " + fileName);
        //Second save the picture
        //--------------------------
        //Find the external storage directory
        File filesDir = MyApplication.instance.getCacheDir();
        //Retrieve the name of the subfolder where your store your picture
        //(You have set it in your string ressources)
        String pictureFolderName = FolderName.PICTURE_CACHE;
        //then create the subfolder
        File pictureDir = new File(filesDir, pictureFolderName);
        //Check if this subfolder exists
        if (!pictureDir.exists()) {
            //if it doesn't create it
            pictureDir.mkdirs();
        }
        //Define the file to store your picture in
        File filePicture = new File(pictureDir, fileName);
        if (filePicture.exists()) {
            MyLog.e(TAG, "loadPictureFromDisk picture (" + fileName + ")exist return picture");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeFile(filePicture.getPath(), options);
        } else {
            MyLog.e(TAG, "loadPictureFromDisk picture (" + fileName + ")returns null");
            return null;
        }
    }
    /**
     * Should be called by the View
     */
    public static MutableLiveData<Bitmap> loadPictureFromDiskAsynch(String fileName) {
        //create the LiveData
        MutableLiveData<Bitmap> bitmapLvDt = new MutableLiveData<>();
        MyApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(new RunnableLoadPictureFromDisk(fileName,bitmapLvDt));
        return bitmapLvDt;
    }

    /**
     * This is the runnable that will send the work in a background thread
     */
    private static class RunnableLoadPictureFromDisk implements Runnable {
        //first argument
        String fileName;
        //MutableObject
        MutableLiveData<Bitmap> bitmapLvDt;

        public RunnableLoadPictureFromDisk(String fileName, MutableLiveData<Bitmap> bitmapLvDt) {
            //assign the parameter as class members
            this.fileName = fileName;
            this.bitmapLvDt = bitmapLvDt;
        }

        @Override
        public void run() {
            loadPictureFromDiskSync(fileName, bitmapLvDt);
        }
    }

    /***********************************************************
     *  Managing File existence on Disk
     **********************************************************/


    /**
     * To know if the picture has been cached on the disk
     *
     * @param fileName
     * @return
     */
    public static boolean isPictureSavedOnDisk(String fileName) {
        return isFileOnDisk(FolderName.CACHE_FOLDER,FolderName.PICTURE_CACHE, fileName);
    }
    /**
     * To know if a file has been store in the Cache area
     *
     * @param folderName Name of the folder containing the file
     * @param fileName   Name of the file
     * @return true is the file exists. False if not
     */
    public static boolean isFileCachedOnDisk(String folderName, String fileName) {
        return isFileOnDisk(FolderName.INTERNAL_FOLDER, folderName, fileName);

    }

    /**
     * To know if a file has been store in the specific area
     *
     * @param folderType The type of folder in which you will search the file
     * @param folderName Name of the folder containing the file
     * @param fileName   Name of the file
     * @return true is the file exists. False if not
     */
    public static boolean isFileOnDisk(int folderType, String folderName, String fileName) throws IllegalArgumentException {
        File filesDir;
        if (folderType == FolderName.CACHE_FOLDER) {
            filesDir = MyApplication.instance.getCacheDir();
        } else if (folderType == FolderName.INTERNAL_FOLDER) {
            filesDir = MyApplication.instance.getFilesDir();
        } else if (folderType == FolderName.EXTERNAL_FOLDER) {
            //Not implemented yet, need to add permissions
            throw new IllegalArgumentException("notImplementedYet");
        } else if (folderType == FolderName.PUBLIC_EXTERNAL_FOLDER) {
            //Not implemented yet, need to add permissions
            throw new IllegalArgumentException("notImplementedYet");
        } else {
            throw new IllegalArgumentException("wrong_initialization_FileUtils_isFileOnDisk");
        }
        //Second save the picture
        //--------------------------
        //Find the external storage directory

        //Retrieve the name of the subfolder where your store your picture
        //(You have set it in your string ressources)
        //then create the subfolder
        File pictureDir = new File(filesDir, folderName);
        //Check if this subfolder exists
        if (!pictureDir.exists()) {
            //if it doesn't create it
            MyLog.e(TAG, "isPictureSavedOnDisk (" + fileName + ") returns false");
            return false;
        }
        //Define the file to store your picture in
        File filePicture = new File(pictureDir, fileName);
        if (filePicture.exists()) {
            MyLog.e(TAG, "isPictureSavedOnDisk (" + fileName + ") returns false");
            return true;
        } else {
            MyLog.e(TAG, "isPictureSavedOnDisk (" + fileName + ") returns false");
            return false;
        }
    }


}
