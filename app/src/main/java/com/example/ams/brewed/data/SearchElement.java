package com.example.ams.brewed.data;

import android.graphics.Bitmap;

/**
 * Created by AMS on 02/06/2015.
 */
public class SearchElement {
    private final String name;
    private final Bitmap label_icon;

    public SearchElement(String name, Bitmap label_icon) {
        this.name = name;
        this.label_icon = label_icon;
    }

    public String getName() {
        return name;
    }

    public Bitmap getLabel_icon() {
        return label_icon;
    }
}
