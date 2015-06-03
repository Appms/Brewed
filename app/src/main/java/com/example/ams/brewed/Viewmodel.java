package com.example.ams.brewed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.ams.brewed.activities.ResultsActivity;
import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.IBeerView;
import com.example.ams.brewed.interfaces.IBreweryView;
import com.example.ams.brewed.interfaces.IMainView;
import com.example.ams.brewed.interfaces.IModel;
import com.example.ams.brewed.interfaces.IResultsView;
import com.example.ams.brewed.interfaces.ResponseReceiver;

/**
 * Created by AMS on 14/05/2015.
 */

public class Viewmodel {

    /*public static enum ActivityType{
        MAIN, SEARCH, BEER, BREWERY
    }*/

    private IMainView mainView;
    private IResultsView resultsView;
    private IBeerView beerView;
    private IBreweryView breweryView;

    private IModel model;

    private static Viewmodel instance;

    private boolean loadingResults;
    private boolean loadingRandomBeer;

    //private ActivityType currentActivity = ActivityType.MAIN;

    private Viewmodel (IModel model){
        this.model = model;
    }

    public static Viewmodel getInstance() {return instance;}

    public static Viewmodel buildInstance(final IModel model){
        instance = new Viewmodel(model);
        return instance;
    }

    //SEARCHING BEERS/BREWERIES

    public void onSearchRequested() {
        resultsView.askForSearchCriteria();
    }

    public void onBeerSearchRequested(final String criteria){
        if(resultsView == null) return;

        resultsView.startShowSearchInProgress();
        loadingResults = true;

        model.getBeerSearch(criteria, new ResponseReceiver<Beer[]>() {
            @Override
            public void onResponseReceived(Beer[] response) {
                resultsView.stopShowSearchInProgress();
                loadingResults = false;
                resultsView.showBeerResults(response);
            }

            @Override
            public void onErrorReceived(String message) {
                resultsView.stopShowSearchInProgress();
                loadingResults = false;
                resultsView.showBeerResults(null);
                resultsView.showError(message);
            }
        });
    }

    public void onRandomBeerSearchRequested(){
        if(beerView == null) return;

        beerView.startShowInProgress();
        loadingRandomBeer = true;

        model.getRandomBeer(new ResponseReceiver<Beer>() {
            @Override
            public void onResponseReceived(Beer response) {
                beerView.stopShowSearchInProgress();
                loadingRandomBeer = false;
                beerView.displayInfo(response);
            }

            @Override
            public void onErrorReceived(String message) {
                beerView.stopShowSearchInProgress();
                loadingRandomBeer = false;
                beerView.displayInfo(null);
                beerView.showError(message);
            }
        });

    }

    public void onBrewerySearchRequested(final String criteria){
        if(resultsView == null) return;

        resultsView.startShowSearchInProgress();
        loadingResults = true;

        model.getBrewerySearch(criteria, new ResponseReceiver<Brewery[]>() {
            @Override
            public void onResponseReceived(Brewery[] response) {
                resultsView.stopShowSearchInProgress();
                loadingResults = false;
                resultsView.showBreweryResults(response);
            }

            @Override
            public void onErrorReceived(String message) {
                resultsView.stopShowSearchInProgress();
                loadingResults = false;
                resultsView.showBreweryResults(null);
                resultsView.showError(message);
            }
        });
    }

    //CHANGING BETWEEN ACTIVITIES

    public void changeToResultsActivity(){
        mainView.changeToResultsActivity();
    }

    public void changeToBeerActivity(Beer beer){
        resultsView.changeToBeerActivity();
        beerView.displayInfo(beer);
    }

    public void changeToRandomBeerActivity(){
        mainView.changeToBeerActivity();
        onRandomBeerSearchRequested();
    }

    public void changeToBreweryActivity(Brewery brewery){
        resultsView.changeToBreweryActivity();
        breweryView.displayInfo(brewery);
    }

}
