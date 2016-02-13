package com.loopcupcakes.udacity.sunshine.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.loopcupcakes.udacity.sunshine.entities.Forecast;
import com.loopcupcakes.udacity.sunshine.entities.Result;
import com.loopcupcakes.udacity.sunshine.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evin on 2/12/16.
 */
public class FetchWeatherTask extends AsyncTask<Integer, Void, List<Forecast>> {

    private static final String TAG = "FetchWeatherTaskTAG_";

    @Override
    protected List<Forecast> doInBackground(Integer... params) {

        int postCode = (params != null && params.length > 0) ? params[0] : 30339;

        RetrofitHelper retrofitHelper = new RetrofitHelper();

        Result result = retrofitHelper.getForecast(postCode);

        return (result != null) ? result.getForecast() : new ArrayList<Forecast>();
    }

    @Override
    protected void onPostExecute(List<Forecast> forecastList) {
        super.onPostExecute(forecastList);
        Log.d(TAG, "onPostExecute: ");

        for (Forecast forecast : forecastList) {
            Log.d(TAG, "onResponse: " + forecast.getTemp().getDay());
        }

    }
}
