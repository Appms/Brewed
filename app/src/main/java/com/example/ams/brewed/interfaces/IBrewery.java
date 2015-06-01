package com.example.ams.brewed.interfaces;

import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;

/**
 * Created by al264101 on 21/05/15.
 */
public interface IBrewery {

    void getBeerSearch(String name, final ResponseReceiver<Beer[]> receiver);
    void getRandomBeer(final ResponseReceiver<Beer> receiver);
    void getBrewerySearch(String name, final ResponseReceiver<Brewery[]> receiver);
    void getGeographicalBreweries(Double latitude, Double longitude, final ResponseReceiver<Brewery[]> receiver);

}

