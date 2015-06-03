package com.example.ams.brewed.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
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

    private RelativeLayout layoutBeer;
    private ScrollView scrollView;

    private TextView textName;
    private TextView textAlcohol;
    private TextView textAvailability;
    private TextView textStyle;
    private TextView textDescription;
    private ImageView imageLogo;
    private ProgressBar progressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);
        viewmodel = Viewmodel.getInstance();
        viewmodel.storeBeerActivity(this);

        layoutBeer = (RelativeLayout) findViewById(R.id.layoutBeer);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        textName = (TextView) findViewById(R.id.textName);
        textAlcohol = (TextView) findViewById(R.id.textAlcohol);
        textAvailability = (TextView) findViewById(R.id.textAvailability);
        textStyle = (TextView) findViewById(R.id.textStyle);
        textDescription = (TextView) findViewById(R.id.textDescription);
        imageLogo = (ImageView) findViewById(R.id.beerLogo);

        progressLoading = (ProgressBar) findViewById(R.id.progressLoading);
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

        textName.setText(beer.getName());
        textAlcohol.setText(beer.getAlcoholByVolume()+"%");
        textAvailability.setText(beer.getAvailability());
        textStyle.setText(beer.getStyle());
        textDescription.setText(beer.getDescription());
        imageLogo.setImageBitmap(beer.getLabel_medium());

        //int newColor = Color.rgb(0, 120, 255);
        int newColor = beer.getSrmColor();
        changeBackgroundColorBySRM(newColor);

    }

    @Override
    public void startSearchingProgress() {

        // PONER COSAS VISIBLES o INVISIBLES
        progressLoading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        imageLogo.setVisibility(View.INVISIBLE);

    }

    @Override
    public void stopSearchingProgress() {

        // PONER COSAS VISIBLES o INVISIBLES
        progressLoading.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        imageLogo.setVisibility(View.VISIBLE);

    }

    @Override
    public void changeBackgroundColorBySRM() {

    }

    @Override
    public void startShowInProgress() {

    }

    @Override
    public void stopShowSearchInProgress() {

    }

    public void showError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

}
