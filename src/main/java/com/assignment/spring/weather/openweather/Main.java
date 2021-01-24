
package com.assignment.spring.weather.openweather;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Main {

    @JsonProperty("temp")
    private BigDecimal temp;

    public BigDecimal getTemp() {
        return temp;
    }
}
