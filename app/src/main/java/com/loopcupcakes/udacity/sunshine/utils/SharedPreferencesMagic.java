package com.loopcupcakes.udacity.sunshine.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.loopcupcakes.udacity.sunshine.R;

/**
 * Created by evin on 2/14/16.
 */
public class SharedPreferencesMagic {
    public static String[] getMainSettings(SharedPreferences sharedPreferences, Context context){
        String location = sharedPreferences.getString(Constants.LOCATION_KEY,
                context.getString(R.string.pref_location_default));
        String type_measurement = sharedPreferences.getString(Constants.MEASUREMENT_KEY,
                context.getString(R.string.pref_location_default));
        return new String[]{location, type_measurement};
    }
}
