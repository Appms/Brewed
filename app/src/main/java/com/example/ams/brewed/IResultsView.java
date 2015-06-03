package com.example.ams.brewed;

import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.data.SearchElement;

/**
 * Created by PC on 02/06/2015.
 */
public interface IResultsView {

    void searchBeer(String input);
    void searchRandomBeer();
    void searchBrewery(String input);
    void searchGeographicalBreweries();
    void showBeerResults(Beer[] response);
    void showBreweryResults(Brewery[] response);

    void askForSearchCriteria();

    void startShowSearchInProgress();

    void stopShowSearchInProgress();

    void warnBadNetwork();

    void showError(String message);
}
