package com.example.ams.brewed.preferences;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by PC on 04/06/2015.
 */
public class WhiteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).addToBackStack("preferenceTag").commit();

    }

}
