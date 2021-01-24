package com.assignment.spring.weather;

import java.math.BigDecimal;

import javax.persistence.*;


/**
 * Single weather measurement
 */
@Entity
@Table(name = "weather")
public class WeatherMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_sequence")
    @SequenceGenerator(name = "weather_sequence", allocationSize = 1)
    private Integer id;

    private String city;

    private String country;

    private BigDecimal temperature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }
}
