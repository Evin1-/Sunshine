package com.loopcupcakes.udacity.sunshine.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopcupcakes.udacity.sunshine.R;
import com.loopcupcakes.udacity.sunshine.SettingsActivity;
import com.loopcupcakes.udacity.sunshine.adapters.ForecastAdapter;
import com.loopcupcakes.udacity.sunshine.database.WeatherContract;
import com.loopcupcakes.udacity.sunshine.tasks.FetchWeatherTask;
import com.loopcupcakes.udacity.sunshine.utils.Constants;
import com.loopcupcakes.udacity.sunshine.utils.SharedPreferencesMagic;
import com.loopcupcakes.udacity.sunshine.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment
        implements SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ForecastFragmentTAG_";

    private static final int LOADER_ID = 10;

    private ForecastAdapter mForecastAdapter;
    private ArrayList<String> mArrayList;

    public ForecastFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mArrayList = new ArrayList<>();
//        mForecastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_text, mArrayList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        String locationSetting = Utility.getPreferredLocation(getActivity());
//
//        // Sort order:  Ascending, by date.
//        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
////        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
////                locationSetting, System.currentTimeMillis());
//        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocation(
//                locationSetting);
//
//        Cursor cursor = getActivity().getContentResolver().query(weatherForLocationUri,
//                null, null, null, sortOrder);
//
//        Log.d(TAG, "onCreateView: " + cursor.getCount());
//        Log.d(TAG, "onCreateView: " + weatherForLocationUri);

        mForecastAdapter = new ForecastAdapter(getActivity(), null, 0);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

        listView.setAdapter(mForecastAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String forecast = mForecastAdapter.getItem(position);
//                Intent intent = new Intent(getContext(), DetailsActivity.class);
//                intent.putExtra(Constants.FORECAST_BUNDLE_KEY, forecast);
//                startActivity(intent);
//            }
//        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        return rootView;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        refreshWeather(sharedPreferences);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        refreshWeather(sharedPreferences);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_map) {
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String locationQuery = sharedPreferences.getString(Constants.LOCATION_KEY, getString(R.string.pref_location_default));
            showMap(Uri.parse("geo:0,0").buildUpon().appendQueryParameter("q", locationQuery).build());
        }
        if (id == R.id.action_refresh) {
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            refreshWeather(sharedPreferences);
            return true;
        }

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshWeather(SharedPreferences sharedPreferences) {
        final String[] preferenceOptions = SharedPreferencesMagic.getMainSettings(sharedPreferences, getContext());
        new FetchWeatherTask(this).execute(preferenceOptions);
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void refreshAdapter(String[] resultArray) {
        if (mArrayList != null && mForecastAdapter != null) {
            mArrayList.clear();
            mArrayList.addAll(new ArrayList<String>(Arrays.asList(resultArray)));
            mForecastAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String locationSetting = Utility.getPreferredLocation(getActivity());
        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocation(
                locationSetting);

        return new CursorLoader(getActivity().getApplicationContext(),
                weatherForLocationUri,
                null,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mForecastAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mForecastAdapter.swapCursor(null);
    }
}
