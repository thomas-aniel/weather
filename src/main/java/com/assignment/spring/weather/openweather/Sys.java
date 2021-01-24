
package com.assignment.spring.weather.openweather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sys {

    @JsonProperty("country")
    private String country;

    public String getCountry() {
        return country;
    }
}
