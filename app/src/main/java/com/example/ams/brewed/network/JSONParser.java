package com.example.ams.brewed.network;

import android.media.Image;

import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.ResponseReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by AMS on 14/05/2015.
 */
public class JSONParser {

    private static final String SEARCH_STATUS = "status";
    private static final String FAILURE = "failure";

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
            Image label_icon, label_medium;

            Beer beer;

            try{
                JSONObject beerData = results.getJSONObject(i);
                name = beerData.getString(BEER_NAME_ID);
                abv = beerData.getDouble(BEER_ALCOHOL_BY_VOLUME_ID);
                JSONObject srmData = beerData.getJSONObject(BEER_STANDARD_REFERENCE_METHOD_ID);
                srmColor = Integer.parseInt(srmData.getString(BEER_STANDARD_REFERENCE_METHOD_HEX_ID).substring(2), 16);
                JSONObject styleData = beerData.getJSONObject(BEER_STYLE_ID);
                style = styleData.getString(BEER_STYLE_NAME_ID);
                JSONObject availabilityData = beerData.optJSONObject(BEER_AVAILABILITY_ID);
                availability = availabilityData.getString(BEER_AVAILABILITY_NAME_ID);
                description = beerData.getString(BEER_DESCRIPTION_ID);
                /*URL url = new URL("http://www.yahoo.com/image_to_read.jpg");
                image = ImageIO.read(url);*/
            } catch (JSONException e){
                setDefaultBeerValues(ref name, ref style, ref availability, ref description, ref abv, ref srmColor, ref label_icon, ref label_medium);
            }

            beer = new Beer(name,abv,srmColor,style,availability,description,label_icon,label_medium);
            beers[i] = beer;
        }
        receiver.onResponseReceived(beers);
    }

    public static Beer getBeerFromJSON(JSONObject JSONPage, ResponseReceiver<Beer> receiver) throws JSONException{
    }

    public static void getBrewerySearchFromJSON(JSONObject JSONPage, ResponseReceiver<Brewery[]> receiver) throws JSONException{

    }

    public static void getGeographicalBreweryFromJSON(JSONObject JSONPage, ResponseReceiver<Brewery[]> receiver) throws JSONException{

    }
}

