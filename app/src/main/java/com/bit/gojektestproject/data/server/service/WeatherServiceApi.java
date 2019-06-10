package com.bit.gojektestproject.data.server.service;

import com.bit.gojektestproject.data.server.model.WeatherResponseModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherServiceApi {

    @GET("pokemon")
    Single<WeatherResponseModel> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);


}