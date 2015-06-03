package com.example.ams.brewed.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ams.brewed.R;
import com.example.ams.brewed.interfaces.IMainView;
import com.example.ams.brewed.Viewmodel;

public class MainActivity extends ActionBarActivity implements IMainView {


    private static final String PREFERENCES_ID = "Preferences";
    private static final String LOCATION_ID = "Location";

    private Viewmodel viewmodel;

    //Location views
    private ProgressBar locationProgress;
    private LinearLayout locationInformation;
    private TextView locationName;
    private TextView locationCoordinates;
    private TextView locationTemperature;
    private ImageView locationIcon;

    //Weather views
    private ProgressBar weatherProgress;
    private FrameLayout weatherFragmentContainer;
    private ToggleButton currentButton;
    private ToggleButton forecastButton;
    private ToggleButton hourlyButton;
    private TextView warningLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeActivity();
        viewmodel = Viewmodel.getInstance();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
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
    */
    protected void onResume()
    {
        super.onResume();
        viewmodel.connectView(this);
    }

    @Override
    public void onPressedBeer() {

        viewmodel.changeToResultsActivity();

    }

    @Override
    public void onPressedRandomBeer() {

    }

    @Override
    public void onPressedBrewery() {

    }

    @Override
    public void onPressedGeographicalBreweries() {

    }
}
