package com.example.ams.brewed.data;

import android.graphics.Bitmap;

/**
 * Created by al264101 on 21/05/15.
 */
public class Brewery extends SearchElement{
    private final int established;
    private final String website;
    private final Bitmap label_medium;

    public Brewery(String name, int established, String website, Bitmap label_icon, Bitmap label_medium) {
        super(name, label_icon);

        this.established = established;
        this.website = website;
        this.label_medium = label_medium;
    }


    public int getEstablished() {
        return established;
    }

    public String getWebsite() {
        return website;
    }

    public Bitmap getLabel_medium() {
        return label_medium;
    }
}
