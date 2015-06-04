package com.example.ams.brewed.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ams.brewed.R;
import com.example.ams.brewed.Viewmodel;
import com.example.ams.brewed.data.Brewery;
import com.example.ams.brewed.interfaces.IBreweryView;

/**
 * Created by PC on 02/06/2015.
 */
public class BreweryActivity extends ActionBarActivity implements IBreweryView{

    private Viewmodel viewmodel;
    private Brewery data;

    private TextView breweryName;
    private TextView breweryEstablished;
    private TextView breweryWebsite;
    private ImageView breweryLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery);
        viewmodel = Viewmodel.getInstance();
        viewmodel.storeBreweryActivity(this);

        breweryName = (TextView) findViewById(R.id.breweryName);
        breweryEstablished = (TextView) findViewById(R.id.breweryEstablished);
        breweryWebsite = (TextView) findViewById(R.id.breweryWebsite);
        breweryLogo = (ImageView) findViewById(R.id.breweryLogo);

        data = viewmodel.currentBreweryData;
        displayInfo(data);
    }

    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void displayInfo(Brewery brewery) {

        breweryName.setText(brewery.getName());
        breweryEstablished.setText(""+brewery.getEstablished());
        breweryWebsite.setText(brewery.getWebsite());
        breweryLogo.setImageBitmap(brewery.getLabel_medium());

    }
}
