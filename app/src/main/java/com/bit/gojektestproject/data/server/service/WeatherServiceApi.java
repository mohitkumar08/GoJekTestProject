package com.bit.gojektestproject.data.server.service;

import com.bit.gojektestproject.data.server.model.WeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherServiceApi {

    @GET(".")
    Single<WeatherResponse> getWeather(@Query("key") String apiKey, @Query("q") String query, @Query("days") int days);


}