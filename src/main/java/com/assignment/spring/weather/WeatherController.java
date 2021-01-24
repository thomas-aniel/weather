package com.assignment.spring.weather;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private WeatherService service;

    @Autowired
    public WeatherController(WeatherService service) {
    	this.service = service;
    }

    @RequestMapping("/weather")
    public WeatherMeasurement weather(HttpServletRequest request) {
        String city = request.getParameter("city");
        return service.getWeather(city);
    }
}
