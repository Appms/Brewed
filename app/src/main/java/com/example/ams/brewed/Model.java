package com.example.ams.brewed;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.IBrewery;
import com.example.ams.brewed.interfaces.IModel;
import com.example.ams.brewed.interfaces.ResponseReceiver;

import java.util.Calendar;

/**
 * Created by AMS on 14/05/2015.
 */
public class Model implements IModel {

    //CÓMO ACTUALIZO LA LASTKNOWNLOCATION?

    private final static int LOCATION_UPDATE_INTERVAL = 10 * 60 * 1000;

    private IBrewery breweryDB = null;
    private Brewery[] lastGeoData = null;
    private Calendar lastTimeUpdate = null;

    //LOCATION PROVIDER ATTRIBUTES
    private LocationManager locationManager;
    private Criteria criteria;
    String bestProvider;
    private LocationListener locationListener;
    private Double[] lastKnownLocation;

    public Model (IBrewery breweryDB) {
        this.breweryDB = breweryDB;
        setupLocationTracking();
    }

    @Override
    public void setupLocationTracking() {
        locationManager = (LocationManager) breweryDB.getNetworkContext().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        bestProvider = locationManager.getBestProvider(criteria, false);
        lastKnownLocation = getLastKnownLocation();
        locationListener = new LocationListener() {

            public void onLocationChanged(Location l) {}

            public void onProviderEnabled(String p) {}

            public void onProviderDisabled(String p) {}

            public void onStatusChanged(String p, int status, Bundle extras) {}
        };
        locationManager.requestLocationUpdates(bestProvider, 60 * 1000, 1000, locationListener);
    }

    @Override
    public Double[] getLastKnownLocation() {
        Double[] lastKnownLocation = new Double[2];

        Location location = locationManager.getLastKnownLocation(bestProvider);
        try {
            lastKnownLocation[0] = location.getLatitude();
            lastKnownLocation[1] = location.getLongitude();
        } catch (NullPointerException e) {
            lastKnownLocation[0] = -1.0;
            lastKnownLocation[1] = -1.0;
        }

        lastTimeUpdate = Calendar.getInstance();

        return lastKnownLocation;
    }

    @Override
    public void getBeerSearch(String name, final ResponseReceiver<Beer[]> receiver) {
        breweryDB.getBeerSearch(name, new ResponseReceiver<Beer[]>() {
            @Override
            public void onResponseReceived(Beer[] response) {
                receiver.onResponseReceived(response);
            }

            @Override
            public void onErrorReceived(String message) {
                receiver.onErrorReceived(message);
            }
        });
    }

    @Override
    public void getRandomBeer(final ResponseReceiver<Beer> receiver) {
        breweryDB.getRandomBeer(new ResponseReceiver<Beer>() {
            @Override
            public void onResponseReceived(Beer response) {
                receiver.onResponseReceived(response);
            }

            @Override
            public void onErrorReceived(String message) {
                receiver.onErrorReceived(message);
            }
        });
    }

    @Override
    public void getBrewerySearch(String name, final ResponseReceiver<Brewery[]> receiver) {
        breweryDB.getBrewerySearch(name, new ResponseReceiver<Brewery[]>() {
            @Override
            public void onResponseReceived(Brewery[] response) {
                receiver.onResponseReceived(response);
            }

            @Override
            public void onErrorReceived(String message) {
                receiver.onErrorReceived(message);
            }
        });
    }

    @Override
    public void getGeographicalBreweries(final ResponseReceiver<Brewery[]> receiver) {

        if(lastGeoData != null && Calendar.getInstance().getTimeInMillis() - lastTimeUpdate.getTimeInMillis() <= LOCATION_UPDATE_INTERVAL){
            receiver.onResponseReceived(lastGeoData);
        }
        else {
            lastKnownLocation = getLastKnownLocation();
            breweryDB.getGeographicalBreweries(lastKnownLocation[0], lastKnownLocation[1], new ResponseReceiver<Brewery[]>() {
                @Override
                public void onResponseReceived(Brewery[] response) {
                    receiver.onResponseReceived(response);
                }

                @Override
                public void onErrorReceived(String message) {
                    receiver.onErrorReceived(message);
                }
            });
        }
    }

    public void getLabel(String url, final ResponseReceiver<Bitmap> receiver){
        breweryDB.getLabel(url, new ResponseReceiver<Bitmap>() {
            @Override
            public void onResponseReceived(Bitmap response) {
                receiver.onResponseReceived(response);
            }

            @Override
            public void onErrorReceived(String message) {
                receiver.onErrorReceived(message);
            }
        });
    }
}
