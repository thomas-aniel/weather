package com.assignment.spring.weather;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.assignment.spring.weather.openweather.Main;
import com.assignment.spring.weather.openweather.OpenWeatherResponse;
import com.assignment.spring.weather.openweather.Sys;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private WeatherRepository weatherRepository;
	
	@Mock
	private ResponseEntity<OpenWeatherResponse> mockResponseEntity;
	
	@Mock
	private OpenWeatherResponse mockOpenWeatherResponse;
	
	@Mock
	private Main mockMain;

	@Mock
	private Sys mockSys;
	
	@Mock
	private OpenWeatherProperties mockConfiguration;

	@AfterEach
	void tearDown() {
		Mockito.verifyNoMoreInteractions(restTemplate, weatherRepository, mockResponseEntity,
			mockOpenWeatherResponse, mockOpenWeatherResponse, mockMain, mockSys, mockConfiguration);
	}
	
	@Test
	public void weatherService() {

		// setup OpenWeather response
		// average temperature in São Paulo. O samba, o carnaval...
		Mockito.when(mockMain.getTemp()).thenReturn(new BigDecimal("20.10"));
		Mockito.when(mockSys.getCountry()).thenReturn("Brazil");
		Mockito.when(mockOpenWeatherResponse.getName()).thenReturn("São Paulo");
		Mockito.when(mockOpenWeatherResponse.getSys()).thenReturn(mockSys);
		Mockito.when(mockOpenWeatherResponse.getMain()).thenReturn(mockMain);
		
		Mockito.when(mockConfiguration.getUrl()).thenReturn("http://mock/{city})");
		Mockito.when(restTemplate.<OpenWeatherResponse>getForEntity(Mockito.eq("http://mock/saopaulo)"), Mockito.any()))
			.thenReturn(mockResponseEntity);
		Mockito.when(mockResponseEntity.getBody()).thenReturn(mockOpenWeatherResponse);
		
		// repository.save() should return the entity we are trying to save
		Mockito.when(weatherRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
		
		WeatherMeasurement result = new WeatherService(restTemplate, weatherRepository, mockConfiguration).getWeather("saopaulo");

		Assertions.assertEquals(mockMain.getTemp(), result.getTemperature());
		Assertions.assertEquals(mockSys.getCountry(), result.getCountry());
		Assertions.assertEquals(mockOpenWeatherResponse.getName(), result.getCity());
	}
}
