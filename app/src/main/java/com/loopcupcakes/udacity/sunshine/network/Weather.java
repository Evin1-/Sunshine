package com.loopcupcakes.udacity.sunshine.network;

import com.loopcupcakes.udacity.sunshine.entities.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by evin on 2/12/16.
 */
public interface Weather {
    @GET("/data/2.5/forecast/daily?mode=json")
    Call<Result> forecast(@Query("q") int q, @Query("units") String units, @Query("cnt") int cnt, @Query("appid") String appid);
}

