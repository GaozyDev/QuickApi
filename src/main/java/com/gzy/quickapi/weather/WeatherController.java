package com.gzy.quickapi.weather;

import com.gzy.quickapi.weather.bean.Weather;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class WeatherController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/weather")
    public String itHome(@RequestParam(name = "longitude", defaultValue = "118.78") double longitude,
                         @RequestParam(name = "latitude", defaultValue = "32.07") double latitude) {
        String urlPath = "https://api.caiyunapp.com/v2/TwsDo9aQUYewFhV8/"
                + longitude + "," + latitude
                + "/weather.json?dailysteps=15&unit=metric:v1";
        Weather weather = restTemplate.getForObject(urlPath, Weather.class);
        return weather.getResult().getForecast_keypoint();
    }
}