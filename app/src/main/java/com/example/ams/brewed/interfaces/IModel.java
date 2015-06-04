package com.example.ams.brewed.interfaces;

import android.graphics.Bitmap;

import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;

/**
 * Created by AMS on 14/05/2015.
 */
public interface IModel {
    void setupLocationTracking();
    Double[] getLastKnownLocation();
    void getBeerSearch(String name, ResponseReceiver<Beer[]> receiver);
    void getRandomBeer(ResponseReceiver<Beer> receiver);
    void getBrewerySearch(String name, ResponseReceiver<Brewery[]> receiver);
    void getGeographicalBreweries(ResponseReceiver<Brewery[]> receiver);
    void getLabel(String url, final ResponseReceiver<Bitmap> receiver);
}
