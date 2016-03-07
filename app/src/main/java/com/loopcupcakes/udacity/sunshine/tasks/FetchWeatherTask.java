package com.loopcupcakes.udacity.sunshine.tasks;

import android.content.ContentValues;
import android.os.AsyncTask;

import com.loopcupcakes.udacity.sunshine.R;
import com.loopcupcakes.udacity.sunshine.entities.Forecast;
import com.loopcupcakes.udacity.sunshine.entities.Result;
import com.loopcupcakes.udacity.sunshine.fragments.ForecastFragment;
import com.loopcupcakes.udacity.sunshine.network.RetrofitHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        List<Forecast> forecastList = (result != null) ? result.getForecast() :  null;

        ArrayList<ContentValues> valuesArrayList = createContentValues(forecastList);

        return resultToString(forecastList);
    }

    private ArrayList<ContentValues> createContentValues(List<Forecast> forecastList) {
        ArrayList<ContentValues> forecastValues = new ArrayList<>();

        for (Forecast forecast : forecastList){
            forecastValues.add(parseContentForecast(forecast));
        }

        return forecastValues;
    }

    private ContentValues parseContentForecast(Forecast forecast) {

        ContentValues weatherValues = new ContentValues();

//        weatherValues.put(WeatherEntry.COLUMN_LOC_KEY, forecast.getLo);
//        weatherValues.put(WeatherEntry.COLUMN_DATE, dateTime);
//        weatherValues.put(WeatherEntry.COLUMN_HUMIDITY, humidity);
//        weatherValues.put(WeatherEntry.COLUMN_PRESSURE, pressure);
//        weatherValues.put(WeatherEntry.COLUMN_WIND_SPEED, windSpeed);
//        weatherValues.put(WeatherEntry.COLUMN_DEGREES, windDirection);
//        weatherValues.put(WeatherEntry.COLUMN_MAX_TEMP, high);
//        weatherValues.put(WeatherEntry.COLUMN_MIN_TEMP, low);
//        weatherValues.put(WeatherEntry.COLUMN_SHORT_DESC, description);
//        weatherValues.put(WeatherEntry.COLUMN_WEATHER_ID, weatherId);

        return weatherValues;
    }

    private String[] resultToString(List<Forecast> forecastList) {
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
