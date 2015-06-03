package com.example.ams.brewed.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.ams.brewed.R;
import com.example.ams.brewed.Viewmodel;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.IResultsView;
import com.example.ams.brewed.data.Beer;

/**
 * Created by PC on 02/06/2015.
 */
public class ResultsActivity extends ActionBarActivity implements IResultsView {

    Viewmodel viewmodel;
    private Viewmodel.SearchType searchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //initializeActivity();
        viewmodel = Viewmodel.getInstance();
        viewmodel.storeResultsActivity(this);
        searchType = viewmodel.currentSearchType;
        changeSearchType(searchType);
    }

    protected void onResume()
    {
        super.onResume();
        //viewmodel.connectView(this);
    }

    @Override
    public void searchBeer(String input) {

    }

    @Override
    public void searchRandomBeer() {

    }

    @Override
    public void searchBrewery(String input) {

    }

    @Override
    public void searchGeographicalBreweries() {

    }

    @Override
    public void showBeerResults(Beer[] response) {

    }

    @Override
    public void showBreweryResults(Brewery[] response) {

    }

    @Override
    public void askForSearchCriteria() {

    }

    @Override
    public void startShowSearchInProgress() {

    }

    @Override
    public void stopShowSearchInProgress() {

    }

    @Override
    public void warnBadNetwork() {

    }

    @Override
    public void showError(String message) {
        
    }

    @Override
    public void changeToBeerActivity() {

    }

    @Override
    public void changeToBreweryActivity() {

    }

    @Override
    public void changeSearchType(Viewmodel.SearchType searchType) {

    }

}
