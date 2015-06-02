package com.example.ams.brewed.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.example.ams.brewed.Model;
import com.example.ams.brewed.R;
import com.example.ams.brewed.Viewmodel;
import com.example.ams.brewed.interfaces.ResponseReceiver;
import com.example.ams.brewed.network.NetworkHelper;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends Activity {

    private static final int TIMEOUT = 3000;
    private static final String PREFERENCES_ID = "Preferences";
    private static final String DEF_LOCATION = "Castellon";

    private boolean timeoutEnded = false;
    private boolean weatherReceived = false;
    private Viewmodel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES_ID, MODE_PRIVATE);

        viewmodel = viewmodel.buildInstance(new Model(new OpenWeather(new NetworkHelper(getApplicationContext()))), preferences.getString("Location",DEF_LOCATION), new ResponseReceiver<String>() {
            @Override
            public void onResponseReceived(String response) {
                weatherReceived = true;
                startWeatherActivity();
            }

            @Override
            public void onErrorReceived(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                weatherReceived = true;
                startWeatherActivity();
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeoutEnded = true;
                startWeatherActivity();
            }
        }, TIMEOUT);
    }

    private void startWeatherActivity() {
        if (!timeoutEnded || !weatherReceived)
            return;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
}
