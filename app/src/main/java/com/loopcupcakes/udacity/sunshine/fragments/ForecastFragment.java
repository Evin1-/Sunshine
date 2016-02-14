package com.loopcupcakes.udacity.sunshine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopcupcakes.udacity.sunshine.DetailsActivity;
import com.loopcupcakes.udacity.sunshine.R;
import com.loopcupcakes.udacity.sunshine.tasks.FetchWeatherTask;
import com.loopcupcakes.udacity.sunshine.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private static final String TAG = "MainFragmentTAG_";
    private ArrayAdapter<String> mArrayAdapter;
    private ArrayList<String> mArrayList;

    public ForecastFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mArrayList = new ArrayList<>();

        mArrayList.add("Today - Sunny - 88/34");
        mArrayList.add("Tomorrow - Sunny - 88/34");
        mArrayList.add("Friday - Sunny - 88/34");
        mArrayList.add("Saturday - Sunny - 88/34");

        mArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_text, mArrayList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

        listView.setAdapter(mArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = mArrayAdapter.getItem(position);
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra(Constants.FORECAST_BUNDLE_KEY, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new FetchWeatherTask(this).execute(30339);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshAdapter(String[] resultArray){
        if (mArrayList != null && mArrayAdapter != null){
            mArrayList.clear();
            mArrayList.addAll(new ArrayList<String>(Arrays.asList(resultArray)));
            mArrayAdapter.notifyDataSetChanged();
        }
    }
}
