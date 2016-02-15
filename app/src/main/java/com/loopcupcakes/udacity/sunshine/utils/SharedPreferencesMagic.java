package com.loopcupcakes.udacity.sunshine.utils;

import android.content.SharedPreferences;

/**
 * Created by evin on 2/14/16.
 */
public class SharedPreferencesMagic {
    public static String[] getMainSettings(SharedPreferences sharedPreferences){
        String location = sharedPreferences.getString(Constants.LOCATION_KEY, Constants.POST_CODE_DEFAULT);
        String type_measurement = sharedPreferences.getString(Constants.MEASUREMENT_KEY, Constants.TYPE_MEASUREMENT_DEFAULT);
        return new String[]{location, type_measurement};
    }
}
