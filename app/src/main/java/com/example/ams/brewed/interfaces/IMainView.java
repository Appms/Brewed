package com.example.ams.brewed.interfaces;

/**
 * Created by PC on 02/06/2015.
 */
public interface IMainView {

    void onPressedBeer();
    void onPressedRandomBeer();
    void onPressedBrewery();
    void onPressedGeographicalBreweries();

    void changeToResultsActivity();

    void changeToBeerActivity();
}
