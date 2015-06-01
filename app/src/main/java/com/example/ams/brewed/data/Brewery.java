package com.example.ams.brewed.data;

import android.media.Image;

/**
 * Created by al264101 on 21/05/15.
 */
public class Brewery {
    private final String name;
    private final int established;
    private final String website;
    private final Image image_icon;
    private final Image image_medium;

    public Brewery(String name, int established, String website, Image label_icon, Image label_medium) {
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

    public Image getLabel_icon() {
        return image_icon;
    }

    public Image getLabel_medium() {
        return image_medium;
    }
}
