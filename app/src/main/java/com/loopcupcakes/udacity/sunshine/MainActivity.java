package com.loopcupcakes.udacity.sunshine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2/14/16 Settings validation
        // TODO: 2/14/16 Settings autocomplete
        // TODO: 3/7/16 Normalize date
        // TODO: 3/7/16 Normalize measurement type db
        // TODO: 3/8/16 Fix normalize date in WeatherContract
        // TODO: 3/8/16 Fix measurement type after first install
        // TODO: 5/10/16 Fix dates formatting after Loader
        // TODO: 5/10/16 Check the weatherForLocationUri

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
