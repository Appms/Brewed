package com.example.ams.brewed.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    private ProgressBar progressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        textName = (TextView) findViewById(R.id.textName);
        textEstablished = (TextView) findViewById(R.id.textEstablished);
        textWebsite = (TextView) findViewById(R.id.textWebsite);
        imageLogo = (ImageView) findViewById(R.id.imageLogo);

        progressLoading = (ProgressBar) findViewById(R.id.progressLoading);

    }

    protected void onResume()
    {
        super.onResume();
        //viewmodel.connectView(this);
    }

    @Override
    public void displayInfo(Brewery brewery) {

        textName.setText(brewery.getName());
        textEstablished.setText(""+brewery.getEstablished());
        textWebsite.setText(brewery.getWebsite());
        imageLogo.setImageBitmap(brewery.getLabel_medium());

    }

    // ESTO NO DEBERIA LLAMARSE, PERO POR SI ACASO
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
