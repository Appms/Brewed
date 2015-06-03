package com.example.ams.brewed.activities;

import android.os.Bundle;

import com.example.ams.brewed.Viewmodel;
import com.example.ams.brewed.interfaces.IResultsView;
import com.example.ams.brewed.data.Beer;

/**
 * Created by PC on 02/06/2015.
 */
public class ResultsActivity extends ActionBarActivity implements IResultsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeActivity();
        viewmodel = Viewmodel.getInstance();
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
    public void showResults(Beer[] response) {

    }
}
