
package com.bit.gojektestproject.data.server.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bit.gojektestproject.util.Constants;
import com.bit.gojektestproject.util.Utils;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "date",
        "date_epoch",
        "day",
        "astro"
})
public class ForecastDay {

    @JsonProperty("date")
    private String date;
    @JsonProperty("date_epoch")
    private Integer dateEpoch;
    @JsonProperty("day")
    private Day day;
    @JsonProperty("astro")
    private Astro astro;
    @JsonIgnore
    private Date todayDate;
    @JsonIgnore
    private String dayOfWeek;

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
        setTodayDate(Utils.convertStringToDate(Constants.YYYY_MM_DD_FORMAT, date));
        setDayOfWeek(date);
    }

    @JsonProperty("date_epoch")
    public Integer getDateEpoch() {
        return dateEpoch;
    }

    @JsonProperty("date_epoch")
    public void setDateEpoch(Integer dateEpoch) {
        this.dateEpoch = dateEpoch;
    }

    @JsonProperty("day")
    public Day getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(Day day) {
        this.day = day;
    }

    @JsonProperty("astro")
    public Astro getAstro() {
        return astro;
    }

    @JsonProperty("astro")
    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    @JsonIgnore
    public Date getTodayDate() {
        return todayDate;
    }

    @JsonIgnore
    public void setTodayDate(final Date todayDate) {
        this.todayDate = todayDate;
    }

    @JsonIgnore
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @JsonIgnore
    public void setDayOfWeek(final String dayOfWeek) {
        this.dayOfWeek = new SimpleDateFormat("EEEE").format(getTodayDate());;
    }
}
