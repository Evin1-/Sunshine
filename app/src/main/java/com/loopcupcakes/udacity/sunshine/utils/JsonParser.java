package com.loopcupcakes.udacity.sunshine.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by evin on 2/12/16.
 */
public class JsonParser {
    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex) throws JSONException {
        // TODO: add parsing code here

        JSONObject obj = new JSONObject(weatherJsonStr);

        return obj.getJSONArray("list").getJSONObject(dayIndex).getJSONObject("temp").getDouble("max");
    }
}
