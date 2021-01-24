package com.assignment.spring.weather;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.assignment.spring.weather.openweather.OpenWeatherResponse;

class WeatherService {
    private RestTemplate restTemplate;

    private WeatherRepository weatherRepository;
    
    private OpenWeatherConfiguration configuration;

	WeatherService(RestTemplate restTemplate, WeatherRepository weatherRepository, OpenWeatherConfiguration configuration) {
		this.restTemplate = restTemplate;
		this.weatherRepository = weatherRepository;
		this.configuration = configuration;
	}

    // TODO: choose a better name, as this not only gets the weather, it also saves it
    WeatherMeasurement getWeather(String city) {
        String url = configuration.getUrl().replace("{city}", city);
        ResponseEntity<OpenWeatherResponse> response = restTemplate.getForEntity(url, OpenWeatherResponse.class);

        WeatherMeasurement entity = new WeatherMeasurement();
        entity.setCity(response.getBody().getName());
        entity.setCountry(response.getBody().getSys().getCountry());
        entity.setTemperature(response.getBody().getMain().getTemp());

        entity = weatherRepository.save(entity);
        
        return entity;
    }
}
