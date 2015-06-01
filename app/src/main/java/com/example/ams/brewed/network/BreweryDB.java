package com.example.ams.brewed.network;

import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.IBrewery;
import com.example.ams.brewed.interfaces.ResponseReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by AMS on 14/05/2015.
 */
public class BreweryDB implements IBrewery {
    private final static String BEER_SEARCH_URL = "http://api.brewerydb.com/v2/search?type=beer&key=813c42beda1341be01f1147489c82904&q=";
    private final static String RANDOM_BEER_URL = "http://api.brewerydb.com/v2/beer/random?key=813c42beda1341be01f1147489c82904";
    private final static String BREWERY_SEARCH_URL = "http://api.brewerydb.com/v2/search?type=brewery&key=813c42beda1341be01f1147489c82904&q=";
    private final static String GEOGRAPHICAL_BREWERY_URL = "http://api.brewerydb.com/v2/search/geo/point?key=813c42beda1341be01f1147489c82904&";

    private NetworkHelper networkHelper = null;

    private String url = null;

    public BreweryDB(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    @Override
    public void getBeerSearch(String name, final ResponseReceiver<Beer[]> receiver) {
        try {
            url = BEER_SEARCH_URL + URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        networkHelper.fetchWebPage(url, new ResponseReceiver<String>() {
            @Override
            public void onResponseReceived(String response) {
                try{
                    JSONParser.getBeerSearchFromJSON(new JSONObject(response), receiver);
                } catch (JSONException e) {
                    receiver.onErrorReceived("Bad format in JSON(BEER SEARCH)");
                }
            }

            @Override
            public void onErrorReceived(String message) {
                receiver.onErrorReceived(message);
            }
        });
    }

    @Override
    public void getRandomBeer(final ResponseReceiver<Beer> receiver) {

        url = RANDOM_BEER_URL;

        networkHelper.fetchWebPage(url, new ResponseReceiver<String>() {
            @Override
            public void onResponseReceived(String response) {
                try {
                    JSONParser.getBeerFromJSON(new JSONObject(response), receiver);
                } catch (JSONException e) {
                    receiver.onErrorReceived("Bad format in JSON(RANDOM BEER)");
                }
            }

            @Override
            public void onErrorReceived(String message) {receiver.onErrorReceived(message);}
        });

    }

    @Override
    public void getBrewerySearch(String name, final ResponseReceiver<Brewery[]> receiver) {

        try {
            url = BREWERY_SEARCH_URL + URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        networkHelper.fetchWebPage(url, new ResponseReceiver<String>() {
            @Override
            public void onResponseReceived(String response) {
                try{
                    JSONParser.getBrewerySearchFromJSON(new JSONObject(response), receiver);
                } catch (JSONException e) {
                    receiver.onErrorReceived("Bad format in JSON(BREWERY SEARCH)");
                }
            }

            @Override
            public void onErrorReceived(String message) {
                receiver.onErrorReceived(message);
            }
        });

    }

    @Override
    public void getGeographicalBreweries(Double latitude, Double longitude, final ResponseReceiver<Brewery[]> receiver) {
        try {
            url = GEOGRAPHICAL_BREWERY_URL + URLEncoder.encode("lat=" + String.valueOf(latitude) + "&lng=" + String.valueOf(longitude), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        networkHelper.fetchWebPage(url, new ResponseReceiver<String>() {
            @Override
            public void onResponseReceived(String response) {
                try{
                    JSONParser.getGeographicalBreweryFromJSON(new JSONObject(response), receiver);
                } catch (JSONException e) {
                    receiver.onErrorReceived("Bad format in JSON(GEOGRAPHICAL BREWERY SEARCH)");
                }
            }

            @Override
            public void onErrorReceived(String message) {
                receiver.onErrorReceived(message);
            }
        });
    }
}
