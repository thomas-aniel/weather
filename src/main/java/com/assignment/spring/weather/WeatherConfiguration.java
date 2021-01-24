package com.assignment.spring.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class WeatherConfiguration {
	
    @Bean
    @Autowired
    WeatherService weatherService(RestTemplate restTemplate, WeatherRepository repository, OpenWeatherConfiguration configuration) {
        return new WeatherService(restTemplate, repository, configuration);
    }
    
    @Bean
    RestTemplate restTemplate() {
    	return new RestTemplate();
    }
}
