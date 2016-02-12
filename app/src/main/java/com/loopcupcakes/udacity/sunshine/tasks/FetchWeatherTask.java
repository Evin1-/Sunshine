package com.loopcupcakes.udacity.sunshine.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.loopcupcakes.udacity.sunshine.entities.Forecast;
import com.loopcupcakes.udacity.sunshine.entities.Result;
import com.loopcupcakes.udacity.sunshine.network.Weather;
import com.loopcupcakes.udacity.sunshine.utils.Constants;
import com.loopcupcakes.udacity.sunshine.utils.Keys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by evin on 2/12/16.
 */
public class FetchWeatherTask extends AsyncTask<Void, Void, List<Forecast>> {

    private static final String TAG = "FetchWeatherTaskTAG_";

    @Override
    protected List<Forecast> doInBackground(Void... params) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Weather weather = retrofit.create(Weather.class);

        Call<Result> call = weather.forecast(30339, "metric", 7, Keys.WEATHER_API_KEY);

        Result result = null;
        try {
            result = call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: " + e.toString());
        }

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
