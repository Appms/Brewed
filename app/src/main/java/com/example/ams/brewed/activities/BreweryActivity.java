package com.example.ams.brewed.activities;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ams.brewed.IBreweryView;

/**
 * Created by PC on 02/06/2015.
 */
public class BreweryActivity implements IBreweryView {

    private TextView textName;
    private TextView textEstablished;
    private TextView textWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        textName = (TextView) findViewById(R.id.textName);
        textEstablished = (TextView) findViewById(R.id.textEstablished);
        textWebsite = (TextView) findViewById(R.id.textWebsite);

    }

    @Override
    public void displayInfo() {

        textName.setText("New name");
        textEstablished.setText("New established");
        textWebsite.setText("New website");

    }
}
