package com.codegym.demo.services;


import com.codegym.demo.dto.response.OpenWeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherService {

    private final RestTemplate restTemplate;

    public OpenWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenWeatherResponse getWeatherCurrent(String nameCity) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+ nameCity + "&appid=c401a010b6f63142bf1e3b514d1d559e";
        return restTemplate.getForObject(url, OpenWeatherResponse.class);
    }
}