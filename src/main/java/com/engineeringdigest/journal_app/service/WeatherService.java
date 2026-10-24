package com.engineeringdigest.journal_app.service;

import com.engineeringdigest.journal_app.domain.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String API_KEY;

    private static final String API_URL = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalUrl = API_URL.replace("CITY", city).replace("API_KEY", API_KEY);

        ResponseEntity<WeatherResponse> weatherResponse = restTemplate.exchange(finalUrl, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = weatherResponse.getBody();
        return body;
    }

    public WeatherResponse postExternalApiExample(String city) {
        String finalUrl = API_URL.replace("CITY", city).replace("API_KEY", API_KEY);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        String jsonBody = "{\n" +
                "    \"userName\": \"Ram\",\n" +
                "    \"password\": \"123\"\n" +
                "}";
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<WeatherResponse> weatherResponse = restTemplate.exchange(finalUrl, HttpMethod.GET, request, WeatherResponse.class);
        WeatherResponse body = weatherResponse.getBody();
        return body;
    }

}
