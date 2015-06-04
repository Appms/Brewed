package com.example.ams.brewed.interfaces;

import com.example.ams.brewed.Viewmodel;
import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.data.SearchElement;

/**
 * Created by PC on 02/06/2015.
 */
public interface IResultsView {

    void showBeerResults(Beer[] response);
    void showBreweryResults(Brewery[] response);

    void askForSearchCriteria();

    void startShowSearchInProgress();

    void stopShowSearchInProgress();


    void showError(String message);

    void changeToBeerActivity();

    void changeToBreweryActivity();

    void changeSearchType(Viewmodel.SearchType searchType);
}
