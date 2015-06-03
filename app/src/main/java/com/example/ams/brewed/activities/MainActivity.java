package com.example.ams.brewed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ams.brewed.R;
import com.example.ams.brewed.interfaces.IMainView;
import com.example.ams.brewed.Viewmodel;

import static com.example.ams.brewed.Viewmodel.*;
import static com.example.ams.brewed.Viewmodel.SearchType.*;

public class MainActivity extends ActionBarActivity implements IMainView {

    private Viewmodel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewmodel = getInstance();
        viewmodel.storeMainActivity(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onResume()
    {
        super.onResume();
    }

    public void onPressedBeerSearch(View view) {
        viewmodel.changeToResultsActivity(BEER);
    }
    public void onPressedRandomBeer(View view) {
        viewmodel.changeToRandomBeerActivity();
    }
    public void onPressedBrewerySearch(View view) {
        viewmodel.changeToResultsActivity(BREWERY);
    }
    public void onPressedGeographicalBreweries(View view) {
        viewmodel.changeToResultsActivity(GEO);
    }

    @Override
    public void changeToResultsActivity() {
        Intent intent = new Intent(getApplicationContext(),ResultsActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeToBeerActivity() {
        Intent intent = new Intent(getApplicationContext(),BeerActivity.class);
        startActivity(intent);
    }
}
