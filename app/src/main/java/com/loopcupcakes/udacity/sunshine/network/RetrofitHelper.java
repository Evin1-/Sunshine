package com.loopcupcakes.udacity.sunshine.network;

import android.util.Log;

import com.loopcupcakes.udacity.sunshine.entities.Result;
import com.loopcupcakes.udacity.sunshine.utils.Constants;
import com.loopcupcakes.udacity.sunshine.utils.Keys;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by evin on 2/12/16.
 */
public class RetrofitHelper {
    private static final String TAG = "RetrofitHelperTAG_";

    public Result getForecast(String postCode, String typeMeasurement) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Weather weather = retrofit.create(Weather.class);


        Call<Result> call = weather.forecast(postCode, typeMeasurement, 7, Keys.WEATHER_API_KEY);

        Result result = null;
        try {
            result = call.execute().body();
        } catch (Exception e) {
            Log.e(TAG, "getForecast: ");
        }
        return result;
    }
}
