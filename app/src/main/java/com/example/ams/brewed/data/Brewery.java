package com.example.ams.brewed.data;

import android.graphics.Bitmap;

/**
 * Created by al264101 on 21/05/15.
 */
public class Brewery {
    private final String name;
    private final int established;
    private final String website;
    private final Bitmap image_icon;
    private final Bitmap image_medium;

    public Brewery(String name, int established, String website, Bitmap label_icon, Bitmap label_medium) {
        this.name = name;
        this.established = established;
        this.website = website;
        this.image_icon = label_icon;
        this.image_medium = label_medium;
    }

    public String getName() {
        return name;
    }

    public int getEstablished() {
        return established;
    }

    public String getWebsite() {
        return website;
    }

    public Bitmap getLabel_icon() {
        return image_icon;
    }

    public Bitmap getLabel_medium() {
        return image_medium;
    }
}
