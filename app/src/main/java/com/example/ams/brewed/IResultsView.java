package com.example.ams.brewed;

/**
 * Created by PC on 02/06/2015.
 */
public interface IResultsView {

    void searchBeer(String input);
    void searchRandomBeer();
    void searchBrewery(String input);
    void searchGeographicalBreweries();
    void showResults();

}
