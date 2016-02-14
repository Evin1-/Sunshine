package com.loopcupcakes.udacity.sunshine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.loopcupcakes.udacity.sunshine.fragments.DetailsFragment;
import com.loopcupcakes.udacity.sunshine.utils.Constants;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String forecast = getIntent().getStringExtra(Constants.FORECAST_BUNDLE_KEY);
        DetailsFragment detailsFragment = DetailsFragment.newInstance(forecast);

        getSupportFragmentManager().beginTransaction().replace(R.id.a_details_frame, detailsFragment, Constants.DETAILS_FRAGMENT_TAG).commit();
    }

}
