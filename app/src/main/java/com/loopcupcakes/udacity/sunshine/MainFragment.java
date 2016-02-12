package com.loopcupcakes.udacity.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopcupcakes.udacity.sunshine.entities.Forecast;
import com.loopcupcakes.udacity.sunshine.entities.Result;
import com.loopcupcakes.udacity.sunshine.network.Weather;
import com.loopcupcakes.udacity.sunshine.network.utils.Constants;
import com.loopcupcakes.udacity.sunshine.network.utils.Keys;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragmentTAG_";

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Today - Sunny - 88/34");
        arrayList.add("Tomorrow - Sunny - 88/34");
        arrayList.add("Friday - Sunny - 88/34");
        arrayList.add("Saturday - Sunny - 88/34");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_text, arrayList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

        listView.setAdapter(arrayAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Weather weather = retrofit.create(Weather.class);

        Call<Result> call = weather.forecast(30339, "metric", 7, Keys.WEATHER_API_KEY);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                for (Forecast forecast : result.getForecast()){
                    Log.d(TAG, "onResponse: " + forecast.getTemp().getDay());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}
