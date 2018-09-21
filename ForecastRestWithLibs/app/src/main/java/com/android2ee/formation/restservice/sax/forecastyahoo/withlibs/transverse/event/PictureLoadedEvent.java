package com.android2ee.formation.restservice.sax.forecastyahoo.withlibs.transverse.event;

/**
 * Created by Created by Mathias Seguy alias Android2ee on 20/06/2018.
 */
public class PictureLoadedEvent {
    private String pictureName;

    public PictureLoadedEvent(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureName() {
        return pictureName;
    }
}
