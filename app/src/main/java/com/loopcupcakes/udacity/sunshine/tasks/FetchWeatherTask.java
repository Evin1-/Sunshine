package com.loopcupcakes.udacity.sunshine.tasks;

import android.os.AsyncTask;

import com.loopcupcakes.udacity.sunshine.R;
import com.loopcupcakes.udacity.sunshine.entities.Forecast;
import com.loopcupcakes.udacity.sunshine.entities.Result;
import com.loopcupcakes.udacity.sunshine.fragments.ForecastFragment;
import com.loopcupcakes.udacity.sunshine.network.RetrofitHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by evin on 2/12/16.
 */
public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

    private static final String TAG = "FetchWeatherTaskTAG_";

    private ForecastFragment mForecastFragment;

    public FetchWeatherTask(ForecastFragment forecastFragment) {
        mForecastFragment = forecastFragment;
    }

    @Override
    protected String[] doInBackground(String... params) {

        String postCode = (params != null && params.length > 0)
                ? params[0]
                : mForecastFragment.getString(R.string.pref_default_location);
        String typeMeasurement = (params != null && params.length > 1)
                ? params[1]
                : mForecastFragment.getString(R.string.pref_default_unit_type);

        RetrofitHelper retrofitHelper = new RetrofitHelper();

        Result result = retrofitHelper.getForecast(postCode, typeMeasurement);

        return resultToString(result);
    }

    private String[] resultToString(Result result) {

        if (result == null){
            return null;
        }

        final List<Forecast> forecastList = result.getForecast();
        if (forecastList == null) {
            return null;
        }

        final int size = forecastList.size();
        String[] strings = new String[size];

        for (int i = 0; i < size; i++) {
            strings[i] = buildStringForecast(forecastList.get(i));
        }

        return strings;
    }

    private String buildStringForecast(Forecast forecast) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(getDate(forecast.getDt()));
        stringBuilder.append(" - ");
        stringBuilder.append(forecast.getWeather().get(0).getMain());
        stringBuilder.append(" - ");
        stringBuilder.append(forecast.getTemp().getMax().intValue());
        stringBuilder.append("/");
        stringBuilder.append(forecast.getTemp().getMin().intValue());

        return stringBuilder.toString();
    }

    private String getDate(long time) {
        DateFormat simpleDateFormat = new SimpleDateFormat("c, MMM d", Locale.US);
        Date netDate = (new Date(time * 1000));
        return simpleDateFormat.format(netDate);
    }

    @Override
    protected void onPostExecute(String[] forecastArray) {
        super.onPostExecute(forecastArray);

        if (forecastArray == null || mForecastFragment == null) {
            return;
        }

        mForecastFragment.refreshAdapter(forecastArray);
    }
}
