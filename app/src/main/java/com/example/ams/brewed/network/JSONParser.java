package com.example.ams.brewed.network;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import com.example.ams.brewed.R;
import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.ResponseReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AMS on 14/05/2015.
 */
public class JSONParser{

    private static final String SEARCH_STATUS = "status";
    private static final String FAILURE = "failure";
    private static final String UNKNOWN_STRING = "unknown";
    private static final Double UNKNOWN_DOUBLE = -1D;
    private static final Integer UNKNOWN_INTEGER = -1;
    //private static final Bitmap UNKNOWN_BITMAP =  BitmapFactory.decodeResource(getResources(),R.drawable.icon_splash);

    //BEER SEARCH

    private static final String BEER_SEARCH_RESULTS_ID = "data";
    private static final String BEER_NAME_ID = "name";
    private static final String BEER_ALCOHOL_BY_VOLUME_ID = "abv";
    private static final String BEER_STANDARD_REFERENCE_METHOD_ID = "srm";
    private static final String BEER_STANDARD_REFERENCE_METHOD_HEX_ID = "hex";
    private static final String BEER_STYLE_ID = "style";
    private static final String BEER_STYLE_NAME_ID = "shortName";
    private static final String BEER_AVAILABILITY_ID = "available";
    private static final String BEER_AVAILABILITY_NAME_ID = "name";
    private static final String BEER_DESCRIPTION_ID = "description";
    private static final String BEER_LABEL_ID = "labels";
    private static final String BEER_LABEL_ICON_ID = "icon";
    private static final String BEER_LABEL_MEDIUM_ID = "medium";

    //BREWERY SEARCH

    private static final String BREWERY_SEARCH_RESULTS_ID = "data";
    private static final String BREWERY_GEOSEARCH_ID = "brewery";
    private static final String BREWERY_NAME_ID = "name";
    private static final String BREWERY_YEAR_ESTABLISHED_ID = "established";
    private static final String BREWERY_WEBSITE_ID = "website";
    private static final String BREWERY_IMAGES_ID = "images";
    private static final String BREWERY_IMAGES_ICON_ID = "icon";
    private static final String BREWERY_IMAGES_MEDIUM_ID = "medium";

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /*miliseconds*/);
            conn.setConnectTimeout(15000 /*miliseconds*/);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            InputStream input = conn.getInputStream();

            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static void getBeerSearchFromJSON(JSONObject JSONPage, ResponseReceiver<Beer[]> receiver){
        String searchStatus;
        JSONArray results;

        try{
            searchStatus = JSONPage.getString(SEARCH_STATUS);
            if (searchStatus == FAILURE){
                receiver.onErrorReceived(FAILURE);
                return;
            }
            results= JSONPage.getJSONArray(BEER_SEARCH_RESULTS_ID);
        } catch (JSONException e){
            return;
        }

        Beer[] beers = new Beer[results.length()];
        for(int i = 0; i < results.length(); i++){
            String name, style, availability, description;
            Double abv;
            Integer srmColor;
            Bitmap label_icon, label_medium;

            Beer beer;

            try {
                JSONObject beerData = results.getJSONObject(i);

                try{name = beerData.getString(BEER_NAME_ID);} catch (JSONException e) { name = UNKNOWN_STRING;}

                try{abv = beerData.getDouble(BEER_ALCOHOL_BY_VOLUME_ID);} catch (JSONException e) { abv = UNKNOWN_DOUBLE;}

                try {
                    JSONObject srmData = beerData.getJSONObject(BEER_STANDARD_REFERENCE_METHOD_ID);
                    srmColor = Integer.parseInt(srmData.getString(BEER_STANDARD_REFERENCE_METHOD_HEX_ID).substring(2), 16);
                } catch (JSONException e) {srmColor = UNKNOWN_INTEGER;}

                try {
                    JSONObject styleData = beerData.getJSONObject(BEER_STYLE_ID);
                    style = styleData.getString(BEER_STYLE_NAME_ID);
                } catch (JSONException e) {style = UNKNOWN_STRING;}

                try {
                    JSONObject availabilityData = beerData.getJSONObject(BEER_AVAILABILITY_ID);
                    availability = availabilityData.getString(BEER_AVAILABILITY_NAME_ID);
                } catch (JSONException e) {availability = UNKNOWN_STRING;}

                try{description = beerData.getString(BEER_DESCRIPTION_ID);} catch (JSONException e) { description = UNKNOWN_STRING;}

                try{
                    JSONObject labelData = beerData.getJSONObject(BEER_LABEL_ID);
                    label_icon = null;
                    label_medium = null;
                    //label_icon = getBitmapFromURL(labelData.getString(BEER_LABEL_ICON_ID));
                    //label_medium = getBitmapFromURL(labelData.getString(BEER_LABEL_MEDIUM_ID));
                } catch (JSONException e){
                    label_icon = null;
                    label_medium = null;
                }

            } catch (JSONException e){
                name = UNKNOWN_STRING;
                abv = UNKNOWN_DOUBLE;
                srmColor = UNKNOWN_INTEGER;
                style = UNKNOWN_STRING;
                availability = UNKNOWN_STRING;
                description = UNKNOWN_STRING;
                label_icon = null;
                label_medium = null;
            }

            beer = new Beer(name,abv,srmColor,style,availability,description,label_icon,label_medium);
            beers[i] = beer;
        }
        receiver.onResponseReceived(beers);
    }

    public static void getBeerFromJSON(JSONObject JSONPage, ResponseReceiver<Beer> receiver){
        String searchStatus;
        JSONObject beerData;
        Beer beer;

        String name, style, availability, description;
        Double abv;
        Integer srmColor;
        Bitmap label_icon, label_medium;

        try{
            searchStatus = JSONPage.getString(SEARCH_STATUS);
            if (searchStatus == FAILURE){
                receiver.onErrorReceived(FAILURE);
                return;
            }
            beerData= JSONPage.getJSONObject(BEER_SEARCH_RESULTS_ID);
        } catch (JSONException e){
            name = UNKNOWN_STRING;
            abv = UNKNOWN_DOUBLE;
            srmColor = UNKNOWN_INTEGER;
            style = UNKNOWN_STRING;
            availability = UNKNOWN_STRING;
            description = UNKNOWN_STRING;
            label_icon = null;
            label_medium = null;
            beer = new Beer(name,abv,srmColor,style,availability,description,label_icon,label_medium);
            receiver.onResponseReceived(beer);
            return;
        }

        try{name = beerData.getString(BEER_NAME_ID);} catch (JSONException e) { name = UNKNOWN_STRING;}

        try{abv = beerData.getDouble(BEER_ALCOHOL_BY_VOLUME_ID);} catch (JSONException e) { abv = UNKNOWN_DOUBLE;}

        try {
            JSONObject srmData = beerData.getJSONObject(BEER_STANDARD_REFERENCE_METHOD_ID);
            srmColor = Integer.parseInt(srmData.getString(BEER_STANDARD_REFERENCE_METHOD_HEX_ID).substring(2), 16);
        } catch (JSONException e) {srmColor = UNKNOWN_INTEGER;}

        try {
            JSONObject styleData = beerData.getJSONObject(BEER_STYLE_ID);
            style = styleData.getString(BEER_STYLE_NAME_ID);
        } catch (JSONException e) {style = UNKNOWN_STRING;}

        try {
            JSONObject availabilityData = beerData.getJSONObject(BEER_AVAILABILITY_ID);
            availability = availabilityData.getString(BEER_AVAILABILITY_NAME_ID);
        } catch (JSONException e) {availability = UNKNOWN_STRING;}

        try{description = beerData.getString(BEER_DESCRIPTION_ID);} catch (JSONException e) { description = UNKNOWN_STRING;}

        try{
            JSONObject labelData = beerData.getJSONObject(BEER_LABEL_ID);
            label_icon = null;
            label_medium = null;
            //label_icon = getBitmapFromURL(labelData.getString(BEER_LABEL_ICON_ID));
            //label_medium = getBitmapFromURL(labelData.getString(BEER_LABEL_MEDIUM_ID));
        } catch (JSONException e){
            label_icon = null;
            label_medium = null;
        }

        beer = new Beer(name,abv,srmColor,style,availability,description,label_icon,label_medium);
        receiver.onResponseReceived(beer);
    }

    public static void getBrewerySearchFromJSON(JSONObject JSONPage, ResponseReceiver<Brewery[]> receiver){
        String searchStatus;
        JSONArray results;

        try{
            searchStatus = JSONPage.getString(SEARCH_STATUS);
            if (searchStatus == FAILURE){
                receiver.onErrorReceived(FAILURE);
                return;
            }
            results= JSONPage.getJSONArray(BREWERY_SEARCH_RESULTS_ID);
        } catch (JSONException e){
            return;
        }

        Brewery[] breweries = new Brewery[results.length()];
        for(int i = 0; i < results.length(); i++){
            String name, website;
            Integer established;
            Bitmap image_icon, image_medium;

            Brewery brewery;

            try {
                JSONObject breweryData = results.getJSONObject(i);

                try{name = breweryData.getString(BREWERY_NAME_ID);} catch (JSONException e) { name = UNKNOWN_STRING;}

                try{website = breweryData.getString(BREWERY_WEBSITE_ID);} catch (JSONException e) { website = UNKNOWN_STRING;}

                try{established = breweryData.getInt(BREWERY_YEAR_ESTABLISHED_ID);} catch (JSONException e) { established = UNKNOWN_INTEGER;}

                try{
                    JSONObject imageData = breweryData.getJSONObject(BREWERY_IMAGES_ID);
                    image_icon = null;
                    image_medium = null;
                    //image_icon = getBitmapFromURL(imageData.getString(BREWERY_IMAGES_ICON_ID));
                    //image_medium = getBitmapFromURL(imageData.getString(BREWERY_IMAGES_MEDIUM_ID));
                } catch (JSONException e){
                    image_icon = null;
                    image_medium = null;
                }

            } catch (JSONException e){
                name = UNKNOWN_STRING;
                website = UNKNOWN_STRING;
                established = UNKNOWN_INTEGER;
                image_icon = null;
                image_medium = null;
            }

            brewery = new Brewery(name, established, website, image_icon, image_medium);
            breweries[i] = brewery;
        }
        receiver.onResponseReceived(breweries);
    }

    public static void getGeographicalBreweryFromJSON(JSONObject JSONPage, ResponseReceiver<Brewery[]> receiver){
        String searchStatus;
        JSONArray results;

        try{
            searchStatus = JSONPage.getString(SEARCH_STATUS);
            if (searchStatus == FAILURE){
                receiver.onErrorReceived(FAILURE);
                return;
            }
            results= JSONPage.getJSONArray(BREWERY_SEARCH_RESULTS_ID);
        } catch (JSONException e){
            return;
        }

        Brewery[] breweries = new Brewery[results.length()];
        for(int i = 0; i < results.length(); i++){
            String name, website;
            Integer established;
            Bitmap image_icon, image_medium;

            Brewery brewery;

            try {
                JSONObject breweryUpperData = results.getJSONObject(i);
                JSONObject breweryData = breweryUpperData.getJSONObject(BREWERY_GEOSEARCH_ID);

                try{name = breweryData.getString(BREWERY_NAME_ID);} catch (JSONException e) { name = UNKNOWN_STRING;}

                try{website = breweryData.getString(BREWERY_WEBSITE_ID);} catch (JSONException e) { website = UNKNOWN_STRING;}

                try{established = breweryData.getInt(BREWERY_YEAR_ESTABLISHED_ID);} catch (JSONException e) { established = UNKNOWN_INTEGER;}

                try{
                    JSONObject imageData = breweryData.getJSONObject(BREWERY_IMAGES_ID);
                    image_icon = null;
                    image_medium = null;
                    //image_icon = getBitmapFromURL(imageData.getString(BREWERY_IMAGES_ICON_ID));
                    //image_medium = getBitmapFromURL(imageData.getString(BREWERY_IMAGES_MEDIUM_ID));
                } catch (JSONException e){
                    image_icon = null;
                    image_medium = null;
                }

            } catch (JSONException e){
                name = UNKNOWN_STRING;
                website = UNKNOWN_STRING;
                established = UNKNOWN_INTEGER;
                image_icon = null;
                image_medium = null;
            }

            brewery = new Brewery(name, established, website, image_icon, image_medium);
            breweries[i] = brewery;
        }
        receiver.onResponseReceived(breweries);
    }
}

