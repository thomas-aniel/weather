package com.assignment.spring.weather;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class WeatherConfiguration {
    @Bean
    RestTemplate restTemplate() {
    	return new RestTemplate();
    }
}
