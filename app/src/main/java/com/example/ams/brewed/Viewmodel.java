package com.example.ams.brewed;

import android.location.Location;

import com.example.ams.brewed.interfaces.IModel;
import com.example.ams.brewed.interfaces.IView;
import com.example.ams.brewed.interfaces.ResponseReceiver;

/**
 * Created by AMS on 14/05/2015.
 */
public class Viewmodel {

    private IView view;
    private IModel model;
    private DisplayedInformation currentDisplay;
    private static Viewmodel instance;

    private boolean loadingWeather;
    private boolean loadingLocation;

    private Viewmodel(IModel model){
        this.model = model;
        currentDisplay = DisplayedInformation.CURRENT;
    }

    public static Viewmodel getInstance(){ return instance; }

    public static Viewmodel buildInstance(final IModel model, final String locationName, final ResponseReceiver<String> receiver){
        instance = new Viewmodel(model);
        model.findLocationByName(locationName, new ResponseReceiver<Location>() {
            @Override
            public void onResponseReceived(Location location) {
                if (location != null){
                    model.setCurrentLocation(location);
                    receiver.onResponseReceived(location.getName());
                }
                else
                    receiver.onErrorReceived(locationName + "not found");
            }

            @Override
            public void onErrorReceived(String message) {
                receiver.onErrorReceived(message);
            }
        });
        return instance;
    }

    //CHANGE THE LOCATION

    public void onChangeLocationRequested(){view.askForLocation();}

    public void onLocationNameEntered(final String location){
        if (view == null)
            return;

        view.startShowLocationSearchInProgress();
        view.startShowWeatherSearchInProgress();
        loadingLocation = true;
        loadingWeather = true;

        model.findLocationByName(location, new ResponseReceiver<Location>() {

            @Override
            public void onResponseReceived(Location response) {
                view.stopShowLocationSearchInProgress();
                view.stopShowWeatherSearchInProgress();
                loadingLocation = false;
                loadingWeather = false;
                if(response == null)
                    view.warnWrongLocation(location);
                else {
                    model.setCurrentLocation(response);
                    view.showLocation(response);
                }
            }

            @Override
            public void onErrorReceived(String message) {
                view.stopShowLocationSearchInProgress();
                view.stopShowWeatherSearchInProgress();
                loadingLocation = false;
                loadingWeather = false;
            }
        });}

    //CHANGE THE TYPE OF DATA DISPLAYED

    public void onShowCurrentWeatherRequested() {
        currentDisplay = DisplayedInformation.CURRENT;
        view.switchWeatherInfo(currentDisplay);
    }

    public void onShowWeatherForecastRequested() {
        currentDisplay = DisplayedInformation.FORECAST;
        view.switchWeatherInfo(currentDisplay);
    }

    public void onShowHourlyForecastRequested() {
        currentDisplay = DisplayedInformation.HOURLY;
        view.switchWeatherInfo(currentDisplay);
    }

    public void requestCurrentWeatherData(){
        if (view == null)
            return;
        view.startShowWeatherSearchInProgress();
        loadingWeather = true;
        model.getCurrentWeatherData( new ResponseReceiver<CurrentWeatherData>() {
            @Override
            public void onResponseReceived(CurrentWeatherData response) {
                view.stopShowWeatherSearchInProgress();
                loadingWeather = false;
                view.showCurrentWeatherData(response);
            }

            @Override
            public void onErrorReceived(String message) {
                view.stopShowWeatherSearchInProgress();
                loadingWeather = false;
                view.showCurrentWeatherData(null);
                view.showError(message);
            }
        });

    }

    public void requestWeatherForecastData(){
        if (view == null)
            return;
        view.startShowWeatherSearchInProgress();
        loadingWeather = true;
        model.getWeatherForecast( new ResponseReceiver<WeatherPrediction[]>() {
            @Override
            public void onResponseReceived(WeatherPrediction[] response) {
                view.stopShowWeatherSearchInProgress();
                loadingWeather = false;
                view.showWeatherForecast(response);
            }

            @Override
            public void onErrorReceived(String message) {
                view.stopShowWeatherSearchInProgress();
                loadingWeather = false;
                view.showWeatherForecast(null);
                view.showError(message);
            }
        });
    }

    public void requestHourlyForecastData(){
        if (view == null)
            return;
        view.startShowWeatherSearchInProgress();
        loadingWeather = true;
        model.getHourlyForecast( new ResponseReceiver<HourlyPrediction[]>() {
            @Override
            public void onResponseReceived(HourlyPrediction[] response) {
                view.stopShowWeatherSearchInProgress();
                loadingWeather = false;
                view.showHourlyForecast(response);
            }

            @Override
            public void onErrorReceived(String message) {
                view.stopShowWeatherSearchInProgress();
                loadingWeather = false;
                view.showHourlyForecast(null);
                view.showError(message);
            }
        });
    }

    //VIEW METHODS
    public void connectView(final IView view){
        this.view = view;
        this.view.switchWeatherInfo(currentDisplay);

        if(loadingWeather)
            this.view.startShowWeatherSearchInProgress();
        else
            this.view.stopShowWeatherSearchInProgress();
        if(loadingLocation)
            this.view.startShowLocationSearchInProgress();
        else
            this.view.stopShowLocationSearchInProgress();

        this.view.showLocation(model.getCurrentLocation());

        switch (currentDisplay){
            case CURRENT:
                requestCurrentWeatherData();
                break;
            case FORECAST:
                requestWeatherForecastData();
                break;
            case HOURLY:
                requestHourlyForecastData();
                break;
            default:
                break;
        }
    }

    public DisplayedInformation getCurrentDisplay(){return currentDisplay;}

    public Location getCurrentLocation(){return model.getCurrentLocation();}

}
