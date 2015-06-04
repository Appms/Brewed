package com.example.ams.brewed.preferences;

import android.app.Activity;
import android.os.Bundle;

import com.example.ams.brewed.R;

/**
 * Created by PC on 04/06/2015.
 */
public class WhiteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

    }

}
