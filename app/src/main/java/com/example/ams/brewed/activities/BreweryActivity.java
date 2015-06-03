package com.example.ams.brewed.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ams.brewed.R;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.IBreweryView;

/**
 * Created by PC on 02/06/2015.
 */
public class BreweryActivity extends ActionBarActivity implements IBreweryView {

    private TextView textName;
    private TextView textEstablished;
    private TextView textWebsite;
    private ImageView imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        textName = (TextView) findViewById(R.id.textName);
        textEstablished = (TextView) findViewById(R.id.textEstablished);
        textWebsite = (TextView) findViewById(R.id.textWebsite);
        imageLogo = (ImageView) findViewById(R.id.imageLogo);

    }

    @Override
    public void displayInfo(Brewery brewery) {

        textName.setText(brewery.getName());
        textEstablished.setText("New established");
        textWebsite.setText("New website");
        imageLogo.setImageBitmap(brewery.getLabel_medium());

    }
}
