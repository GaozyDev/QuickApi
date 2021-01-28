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
        // 接口文档：https://open.caiyunapp.com/%E9%80%9A%E7%94%A8%E9%A2%84%E6%8A%A5%E6%8E%A5%E5%8F%A3/v2.5
        String urlPath = "https://api.caiyunapp.com/v2/TwsDo9aQUYewFhV8/"
                + longitude + "," + latitude
                + "/weather.json?dailysteps=15&unit=metric:v1";
        Weather weather = restTemplate.getForObject(urlPath, Weather.class);
        return weather.getResult().getForecast_keypoint();
    }
}