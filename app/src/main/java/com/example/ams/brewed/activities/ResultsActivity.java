package com.example.ams.brewed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ams.brewed.MessageFragment;
import com.example.ams.brewed.R;
import com.example.ams.brewed.SearchAdapter;
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

    private TextView searchCriteria;
    private ListView listView;
    private ProgressBar searching;

    private Beer[] currentBeerData;
    private Brewery[] currentBreweryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initializeActivity();

        viewmodel = Viewmodel.getInstance();
        viewmodel.storeResultsActivity(this);

        searchType = viewmodel.currentSearchType;
        changeSearchType(searchType);
    }

    private void initializeActivity() {
        searchCriteria = (TextView) findViewById(R.id.searchCriteria);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if (searchType == Viewmodel.SearchType.BEER) viewmodel.changeToBeerActivity(currentBeerData[position]);
                else viewmodel.changeToBreweryActivity(currentBreweryData[position]);
            }
        });


        searching = (ProgressBar) findViewById(R.id.searching);
        searching.setVisibility(View.INVISIBLE);
    }

    protected void onResume()
    {
        super.onResume();
        searchType = viewmodel.currentSearchType;
        changeSearchType(searchType);
    }

    //ASKING FOR A BEER // BREWERY

    public void onSearchCriteriaPressed(View view){
        viewmodel.onSearchRequested();
    }

    @Override
    public void askForSearchCriteria() {
        new MessageFragment().show(getFragmentManager(), "Enter Search Criteria: ");
    }

    //START-STOP SEARCH

    @Override
    public void startShowSearchInProgress() {
        listView.setVisibility(View.INVISIBLE);
        searching.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopShowSearchInProgress() {
        listView.setVisibility(View.VISIBLE);
        searching.setVisibility(View.INVISIBLE);
    }

    //DISPLAYING THE INFO - ERROR

    @Override
    public void showBeerResults(Beer[] response) {
        currentBeerData = response;
        listView.setAdapter(new SearchAdapter(getApplicationContext(), R.layout.search_element, currentBeerData));
    }

    @Override
    public void showBreweryResults(Brewery[] response) {
        currentBreweryData = response;
        listView.setAdapter(new SearchAdapter(getApplicationContext(), R.layout.search_element, currentBreweryData));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    //CHANGING BEETWEEN ACTIVITIES

    @Override
    public void changeToBeerActivity() {
        Intent intent = new Intent(getApplicationContext(),BeerActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeToBreweryActivity() {
        Intent intent = new Intent(getApplicationContext(),BreweryActivity.class);
        startActivity(intent);
    }

    //CHANGING PARAMETERS ACCORDING TO SEARCH STYLE

    @Override
    public void changeSearchType(Viewmodel.SearchType searchType) {
        if(searchType == Viewmodel.SearchType.GEO){
            viewmodel.onGeographicalBreweriesSerchRequested();
            searchCriteria.setClickable(false);
            searchCriteria.setEnabled(false);
            searchCriteria.setText("Geo-Breweries");
        } else {
            searchCriteria.setClickable(true);
            searchCriteria.setEnabled(true);
            searchCriteria.setText("Enter Search Field");
        }
    }

}
