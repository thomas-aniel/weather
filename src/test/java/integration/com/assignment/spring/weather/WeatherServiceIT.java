package integration.com.assignment.spring.weather;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.path.json.config.JsonPathConfig.NumberReturnType;
import io.restassured.response.Response;

public class WeatherServiceIT {
	private static WireMockServer wiremockServer = createWiremockServer();
	
	private final static String CITY = "xxx";
	
	@BeforeAll
	public static void setup() {
		wiremockServer.start();
	}

	@AfterAll
	public static void tearDown() {
		wiremockServer.stop();;
	}
	
	@BeforeEach
	public void clearWiremock() {
		wiremockServer.resetAll();
	}
	
	@Test
	public void simpleRequest() throws URISyntaxException, IOException, SQLException {

		wiremockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/weather"))
			.withQueryParam("q", WireMock.equalTo(CITY))
			.withQueryParam("APPID", WireMock.equalTo("whatever"))
			.willReturn(WireMock.okJson(readFileFromClasspath("openWeather/response.json"))));

		Response response = RestAssured.given()
			.param("city", CITY)
			.when()
			.get("http://localhost:8080/weather");
		
		Assertions.assertEquals(200, response.getStatusCode());
		
		JsonPathConfig jsonPathConfig = new JsonPathConfig(NumberReturnType.BIG_DECIMAL);
		JsonPath jsonPath = response.body().jsonPath(jsonPathConfig);

		BigDecimal temperature = jsonPath.get("temperature");
		String city = jsonPath.get("city");
		String country = jsonPath.get("country");
		
		Assertions.assertEquals(new BigDecimal("282.55"), temperature);
		Assertions.assertEquals("xxx", city);
		Assertions.assertEquals("US", country);
	}

	@Test
	public void openWeatherError() throws URISyntaxException, IOException, SQLException {

		wiremockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/weather"))
			.withQueryParam("q", WireMock.equalTo(CITY))
			.withQueryParam("APPID", WireMock.equalTo("whatever"))
			.willReturn(WireMock.serverError()));

		Response response = RestAssured.given()
			.param("city", CITY)
			.when()
			.get("http://localhost:8080/weather");
		
		Assertions.assertEquals(500, response.getStatusCode());
	}
	
	private static String readFileFromClasspath(String relativePath) throws URISyntaxException, IOException {
		Path path = Paths.get(WeatherServiceIT.class.getClassLoader().getResource(relativePath).toURI());
		
		return Files.readAllLines(path).stream().collect(Collectors.joining("\n"));
	}
    
    private static WireMockServer createWiremockServer() {
		WireMockServer server = new WireMockServer(WireMockConfiguration.options().port(9000));
		
		return server;
	}
}
