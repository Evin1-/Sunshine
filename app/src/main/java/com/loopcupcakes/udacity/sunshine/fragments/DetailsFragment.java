package com.loopcupcakes.udacity.sunshine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopcupcakes.udacity.sunshine.R;
import com.loopcupcakes.udacity.sunshine.SettingsActivity;
import com.loopcupcakes.udacity.sunshine.utils.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragmentTAG_";
    ShareActionProvider mShareActionProvider;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share_details);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        if (mShareActionProvider != null){
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getArguments().getString(Constants.FORECAST_BUNDLE_KEY) + " #SunshineApp");
            shareIntent.setType("text/plain");
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        TextView textView = (TextView) view.findViewById(R.id.f_details_main_txt);
        textView.setText(forecast);
    }
}
