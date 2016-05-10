package com.loopcupcakes.udacity.sunshine.tasks;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.loopcupcakes.udacity.sunshine.R;
import com.loopcupcakes.udacity.sunshine.database.WeatherContract;
import com.loopcupcakes.udacity.sunshine.database.WeatherContract.WeatherEntry;
import com.loopcupcakes.udacity.sunshine.entities.City;
import com.loopcupcakes.udacity.sunshine.entities.Coord;
import com.loopcupcakes.udacity.sunshine.entities.Forecast;
import com.loopcupcakes.udacity.sunshine.entities.Result;
import com.loopcupcakes.udacity.sunshine.entities.Weather;
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
                : mForecastFragment.getString(R.string.pref_location_default);
        String typeMeasurement = (params != null && params.length > 1)
                ? params[1]
                : mForecastFragment.getString(R.string.pref_units_metric);

        RetrofitHelper retrofitHelper = new RetrofitHelper();

        Result result = retrofitHelper.getForecast(postCode, typeMeasurement);

        int locationId = getLocationId(postCode, result);

        List<Forecast> forecastList = (result != null) ? result.getForecast() : null;

        ArrayList<ContentValues> valuesArrayList = createContentValues(forecastList, locationId);
        insertInBulk(valuesArrayList);


        return resultToString(forecastList);
    }

    private int insertInBulk(ArrayList<ContentValues> valuesArrayList) {
        Uri weatherUri = WeatherEntry.CONTENT_URI;
        return getContentResolver().bulkInsert(weatherUri, valuesArrayList.toArray(new ContentValues[0]));
    }

    private int getLocationId(String postCode, Result result) {
        int locationId = -1;

        if (result != null) {
            City city = result.getCity();
            if (city != null) {
                Coord coord = city.getCoord();
                locationId = addLocation(postCode, city.getName(), coord.getLat(), coord.getLon());
            }
        }

        return locationId;
    }

    private ArrayList<ContentValues> createContentValues(List<Forecast> forecastList, int locationId) {
        ArrayList<ContentValues> forecastValues = new ArrayList<>();

        for (Forecast forecast : forecastList) {
            forecastValues.add(parseContentForecast(forecast, locationId));
        }

        return forecastValues;
    }

    private ContentValues parseContentForecast(Forecast forecast, int locationId) {

        ContentValues weatherValues = new ContentValues();

        weatherValues.put(WeatherEntry.COLUMN_LOC_KEY, locationId);
        weatherValues.put(WeatherEntry.COLUMN_DATE, forecast.getDt());
        weatherValues.put(WeatherEntry.COLUMN_HUMIDITY, forecast.getHumidity());
        weatherValues.put(WeatherEntry.COLUMN_PRESSURE, forecast.getPressure());
        weatherValues.put(WeatherEntry.COLUMN_WIND_SPEED, forecast.getSpeed());
        weatherValues.put(WeatherEntry.COLUMN_DEGREES, forecast.getDeg());
        weatherValues.put(WeatherEntry.COLUMN_MAX_TEMP, forecast.getTemp().getMax());
        weatherValues.put(WeatherEntry.COLUMN_MIN_TEMP, forecast.getTemp().getMin());

        List<Weather> weatherList = forecast.getWeather();

        if (weatherList.size() > 0) {
            weatherValues.put(WeatherEntry.COLUMN_SHORT_DESC, weatherList.get(0).getMain());
            weatherValues.put(WeatherEntry.COLUMN_WEATHER_ID, weatherList.get(0).getId());
        }

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

    int addLocation(String locationSetting, String cityName, double lat, double lon) {
        if (mForecastFragment == null) {
            return -1;
        }

        int locationId = -1;

        Uri locationUri = WeatherContract.LocationEntry.CONTENT_URI;

        Cursor cursor = getContentResolver().query(
                locationUri,
                new String[]{WeatherContract.LocationEntry._ID},
                WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + " = ?",
                new String[]{locationSetting},
                null
        );

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                locationId = cursor.getInt(cursor.getColumnIndex(WeatherContract.LocationEntry._ID));
                cursor.close();
            } else {
                locationId = insertLocation(locationSetting, cityName, lat, lon);
            }
        }

        return locationId;
    }

    private int insertLocation(String locationSetting, String cityName, double lat, double lon) {
        int locationId;
        ContentValues contentValues = new ContentValues();
        contentValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, cityName);
        contentValues.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, locationSetting);
        contentValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, lat);
        contentValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, lon);

        Uri createLocationUri = WeatherContract.LocationEntry.CONTENT_URI;
        Uri insertedLocationUri = getContentResolver().insert(createLocationUri, contentValues);

        locationId = Integer.parseInt(WeatherContract.LocationEntry.getLocationSettingFromUri(insertedLocationUri));
        return locationId;
    }

    private ContentResolver getContentResolver() {
        return mForecastFragment.getContext().getContentResolver();
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
