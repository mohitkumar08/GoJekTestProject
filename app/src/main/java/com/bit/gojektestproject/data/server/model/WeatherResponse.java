package com.bit.gojektestproject.data.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "location",
        "current",
        "forecast"
})
public class WeatherResponse {

    @JsonProperty("location")
    private CurrentLocation currentLocation;
    @JsonProperty("current")
    private CurrentLocationWeather currentLocationWeather;
    @JsonProperty("forecast")
    private Forecast forecast;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("location")
    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    @JsonProperty("location")
    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    @JsonProperty("current")
    public CurrentLocationWeather getCurrentLocationWeather() {
        return currentLocationWeather;
    }

    @JsonProperty("current")
    public void setCurrentLocationWeather(CurrentLocationWeather currentLocationWeather) {
        this.currentLocationWeather = currentLocationWeather;
    }

    @JsonProperty("forecast")
    public Forecast getForecast() {
        return forecast;
    }

    @JsonProperty("forecast")
    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

}
