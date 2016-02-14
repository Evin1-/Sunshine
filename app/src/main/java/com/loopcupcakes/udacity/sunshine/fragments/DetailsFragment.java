package com.loopcupcakes.udacity.sunshine.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopcupcakes.udacity.sunshine.R;
import com.loopcupcakes.udacity.sunshine.utils.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragmentTAG_";

    public static DetailsFragment newInstance(String forecast) {

        Bundle args = new Bundle();
        args.putString(Constants.FORECAST_BUNDLE_KEY, forecast);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String forecast = getArguments().getString(Constants.FORECAST_BUNDLE_KEY);

        Log.d(TAG, "onViewCreated: " + forecast);

        TextView textView = (TextView) view.findViewById(R.id.f_details_main_txt);
        textView.setText(forecast);
    }
}
