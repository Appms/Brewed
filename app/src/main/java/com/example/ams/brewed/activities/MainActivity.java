package com.example.ams.brewed.activities;

import android.app.FragmentManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ams.brewed.IMainView;
import com.example.ams.brewed.Viewmodel;
import com.example.ams.brewed.interfaces.IView;

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

    //Fragments
    private CurrentWeatherFragment currentWeatherFragment;
    private WeatherForecastFragment weatherForecastFragment;
    private HourlyForecastFragment hourlyForecastFragment;

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

    protected void onResume()
    {
        super.onResume();
        viewmodel.connectView(this);
    }

    protected void onSaveInstanceState(Location location)
    {
        //super.onSaveInstanceState(location);
        location = viewmodel.getCurrentLocation();
        if (location != null)
        {
            getSharedPreferences(PREFERENCES_ID, MODE_PRIVATE).edit().putString(LOCATION_ID, location.getNameCountry()).apply();
        }
    }

    private void initializeActivity(){
        locationProgress = (ProgressBar) findViewById(R.id.locationProgress);
        locationInformation = (LinearLayout) findViewById(R.id.locationInformation);
        locationName = (TextView) findViewById(R.id.locationName);
        locationCoordinates = (TextView) findViewById(R.id.locationCoordinates);
        locationTemperature = (TextView) findViewById(R.id.locationTemperature);
        locationIcon = (ImageView) findViewById(R.id.locationIcon);

        weatherProgress = (ProgressBar) findViewById(R.id.weatherProgress);
        weatherFragmentContainer = (FrameLayout) findViewById(R.id.weatherFragmentContainer);
        currentButton = (ToggleButton) findViewById(R.id.currentButton);
        forecastButton = (ToggleButton) findViewById(R.id.forecastButton);
        hourlyButton = (ToggleButton) findViewById(R.id.hourlyButton);
        warningLabel = (TextView) findViewById(R.id.warningLabel);

        currentWeatherFragment = new CurrentWeatherFragment();
        weatherForecastFragment = new WeatherForecastFragment();
        hourlyForecastFragment = new HourlyForecastFragment();
    }

    @Override
    public void askForLocation() {
        new MessageFragment().show(getFragmentManager(), "Enter a location: ");
    }

    public void onChangeLocationRequested(View view) {
        viewmodel.onChangeLocationRequested();
    }

    @Override
    public void showLocation(Location location) {
        if (location!=null) {
            locationName.setText(location.getName() + "," + location.getCountry());
            locationCoordinates.setText(location.getCoordinateString());
        }else {
            locationName.setText("UNKNOWN");
            locationCoordinates.setText("UNKNOWN");
        }
    }

    @Override
    public void warnWrongLocation(String locationName) {
        showError("Location not found: " + locationName);
    }

    @Override
    public void showError(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorMessage(String message) {
        warningLabel.setText(message);
        warningLabel.setVisibility(View.VISIBLE);
        weatherProgress.setVisibility(View.INVISIBLE);
        weatherFragmentContainer.setVisibility(View.INVISIBLE);
    }

    private void hideErrorMessage()
    {
        weatherFragmentContainer.setVisibility(View.VISIBLE);
        warningLabel.setVisibility(View.INVISIBLE);
        weatherProgress.setVisibility(View.INVISIBLE);
    }

    public void onShowCurrentWeatherRequested(View view){
        viewmodel.onShowCurrentWeatherRequested();
    }

    public void onShowWeatherForecastRequested(View view){
        viewmodel.onShowWeatherForecastRequested();
    }

    public void onShowHourlyForecastRequested(View view){
        viewmodel.onShowHourlyForecastRequested();
    }

    @Override
    public void showCurrentWeatherData(CurrentWeatherData currentWeatherData) {

        locationTemperature = (TextView) findViewById(R.id.locationTemperature);
        locationIcon = (ImageView) findViewById(R.id.locationIcon);

        if(currentWeatherData != null) {
            locationIcon.setImageResource(getResources().getIdentifier("icon"+currentWeatherData.getCondition().getIcon(), "drawable", getPackageName()));
            locationTemperature.setText(String.valueOf(currentWeatherData.getTemperature()));

            if (viewmodel.getCurrentDisplay() == DisplayedInformation.CURRENT && currentWeatherFragment!=null) {
                hideErrorMessage();
                currentWeatherFragment.showCurrentWeatherData(currentWeatherData);
            } else {
                showErrorMessage("Wrong current display");
            }
        }
        else {
            showErrorMessage("Null data(CURRENT)");
        }
    }

    @Override
    public void showWeatherForecast(WeatherPrediction[] forecast) {
        if(forecast != null){
            if (viewmodel.getCurrentDisplay() == DisplayedInformation.FORECAST && weatherForecastFragment!=null) {
                hideErrorMessage();
                weatherForecastFragment.showWeatherForecast(forecast);
            } else {
                showErrorMessage("Wrong current display");
            }
        }else {
            showErrorMessage("Null data(FORECAST)");
        }
    }

    @Override
    public void showHourlyForecast(HourlyPrediction[] forecast) {
        if(forecast != null){
            if (viewmodel.getCurrentDisplay() == DisplayedInformation.HOURLY && hourlyForecastFragment!=null) {
                hideErrorMessage();
                hourlyForecastFragment.showHourlyForecast(forecast);
            } else {
                showErrorMessage("Wrong current display");
            }
        }else {
            showErrorMessage("Null data(HOURLY)");
        }
    }

    @Override
    public void startShowLocationSearchInProgress() {
        locationProgress.setVisibility(View.VISIBLE);
        locationInformation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopShowLocationSearchInProgress() {
        locationProgress.setVisibility(View.INVISIBLE);
        locationInformation.setVisibility(View.VISIBLE);
    }

    @Override
    public void startShowWeatherSearchInProgress() {
        weatherProgress.setVisibility(View.VISIBLE);
        warningLabel.setVisibility(View.INVISIBLE);
        weatherFragmentContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopShowWeatherSearchInProgress() {
        weatherProgress.setVisibility(View.INVISIBLE);
        warningLabel.setVisibility(View.VISIBLE);
        weatherFragmentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void switchWeatherInfo(DisplayedInformation displayedInformation) {
        FragmentManager fragmentManager = getFragmentManager();

        switch (displayedInformation){
            case CURRENT:
                fragmentManager.beginTransaction().replace(R.id.weatherFragmentContainer, currentWeatherFragment).commit();

                currentButton.setChecked(true);
                forecastButton.setChecked(false);
                hourlyButton.setChecked(false);
                break;

            case FORECAST:
                fragmentManager.beginTransaction().replace(R.id.weatherFragmentContainer, weatherForecastFragment).commit();

                currentButton.setChecked(false);
                forecastButton.setChecked(true);
                hourlyButton.setChecked(false);
                break;

            case HOURLY:
                fragmentManager.beginTransaction().replace(R.id.weatherFragmentContainer, hourlyForecastFragment).commit();

                currentButton.setChecked(false);
                forecastButton.setChecked(false);
                hourlyButton.setChecked(true);
                break;
        }
    }
    */

    @Override
    public void onPressedBeer() {

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
