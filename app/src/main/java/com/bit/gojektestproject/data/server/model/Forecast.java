
package com.bit.gojektestproject.data.server.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "forecastday"
})
public class Forecast {

    @JsonProperty("forecastday")
    private List<ForecastDay> forecastday = null;

    @JsonProperty("forecastday")
    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    @JsonProperty("forecastday")
    public void setForecastday(List<ForecastDay> forecastday) {
        this.forecastday = forecastday;
    }

}
