
package com.assignment.spring.weather.openweather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenWeatherResponse {

    @JsonProperty("main")
    private Main main;
    @JsonProperty("sys")
    private Sys sys;
    @JsonProperty("name")
    private String name;

    public Main getMain() {
        return main;
    }

    public Sys getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }
}
