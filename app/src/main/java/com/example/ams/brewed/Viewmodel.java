package com.example.ams.brewed;

import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.IModel;
import com.example.ams.brewed.interfaces.ResponseReceiver;

/**
 * Created by AMS on 14/05/2015.
 */

public class Viewmodel {

    private IMainView mainView;
    private IResultsView resultsView;
    private IBeerView beerView;
    private IBreweryView breweryView;

    private IModel model;

    private static Viewmodel instance;

    private boolean loadingResults;
    private boolean loadingRandomBeer;

    //VARIABLE QUE ME DICE QUE ACTIVITY HE DE VER

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

}
