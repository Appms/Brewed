package com.example.ams.brewed.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ams.brewed.IBeerView;
import com.example.ams.brewed.R;



public class BeerActivity extends ActionBarActivity implements IBeerView {

    private RelativeLayout layoutBeer;

    private TextView textName;
    private TextView textAlcohol;
    private TextView textAvailability;
    private TextView textStyle;
    private TextView textDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        layoutBeer = (RelativeLayout) findViewById(R.id.layoutBeer);

        textName = (TextView) findViewById(R.id.textName);
        textAlcohol = (TextView) findViewById(R.id.textAlcohol);
        textAvailability = (TextView) findViewById(R.id.textAvailability);
        textStyle = (TextView) findViewById(R.id.textStyle);
        textDescription = (TextView) findViewById(R.id.textDescription);
    }

    @Override
    public void changeBackgroundColorBySRM() {

        int newColor = Color.rgb(0, 120, 255);
        layoutBeer.setBackgroundColor(newColor);

    }

    @Override
    public void displayInfo() {

        textName.setText("New name");
        textAlcohol.setText("New %");
        textAvailability.setText("New availability");
        textStyle.setText("New style");
        textDescription.setText("New description");

    }
}
