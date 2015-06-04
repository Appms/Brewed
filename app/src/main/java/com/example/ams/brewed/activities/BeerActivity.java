package com.example.ams.brewed.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ams.brewed.Viewmodel;
import com.example.ams.brewed.data.Beer;
import com.example.ams.brewed.interfaces.IBeerView;
import com.example.ams.brewed.R;

public class BeerActivity extends ActionBarActivity implements IBeerView {

    Viewmodel viewmodel;
    private Beer data;

    private LinearLayout layoutBeer;
    private ScrollView scrollView;

    private TextView beerName;
    private TextView beerAlcohol;
    private TextView beerAvailability;
    private TextView beerStyle;
    private TextView beerDescription;
    private ImageView beerLogo;
    private ProgressBar beerLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);
        viewmodel = Viewmodel.getInstance();
        viewmodel.storeBeerActivity(this);

        layoutBeer = (LinearLayout) findViewById(R.id.layoutBeer);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        beerName = (TextView) findViewById(R.id.beerName);
        beerAlcohol = (TextView) findViewById(R.id.beerAlcohol);
        beerAvailability = (TextView) findViewById(R.id.beerAvailability);
        beerStyle = (TextView) findViewById(R.id.beerStyle);
        beerDescription = (TextView) findViewById(R.id.beerDescription);
        beerLogo = (ImageView) findViewById(R.id.beerLogo);

        beerLoading = (ProgressBar) findViewById(R.id.beerLoading);
        beerLoading.setVisibility(View.INVISIBLE);

        if(viewmodel.currentSearchType == Viewmodel.SearchType.BEER) {
            data = viewmodel.currentBeerData;
            displayInfo(data);
        }
        else viewmodel.onRandomBeerSearchRequested();
    }

    protected void onResume()
    {
        super.onResume();
    }


    private void changeBackgroundColorBySRM(int newColor) {

        layoutBeer.setBackgroundColor(newColor);
    }

    @Override
    public void displayInfo(Beer beer) {

        beerName.setText(beer.getName());
        if(beer.getAlcoholByVolume() > 0) beerAlcohol.setText(beer.getAlcoholByVolume()+"%");
        else beerAlcohol.setText("-");
        beerAvailability.setText(beer.getAvailability());
        beerStyle.setText(beer.getStyle());
        beerDescription.setText(beer.getDescription());
        //beerLogo.setImageBitmap(beer.getLabel_medium());

        int newColor = Color.rgb(255,178,89);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean useSrmColor = preferences.getBoolean("backgroundBeer", true);

        if(useSrmColor && beer.getSrmColor() != -1)
            newColor = beer.getSrmColor();

        changeBackgroundColorBySRM(newColor);
    }

    @Override
    public void startShowSearchInProgress() {
        beerLoading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        beerLogo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopShowSearchInProgress() {
        beerLoading.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        beerLogo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

}
