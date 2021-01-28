package com.gzy.quickapi.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// 接口文档：https://open.caiyunapp.com/%E9%80%9A%E7%94%A8%E9%A2%84%E6%8A%A5%E6%8E%A5%E5%8F%A3/v2.5

@RestController
public class WeatherController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/weather")
    public String itHome(@RequestParam(name = "longitude", defaultValue = "118.78") double longitude,
                         @RequestParam(name = "latitude", defaultValue = "32.07") double latitude) {
        StringBuilder stringBuilder = new StringBuilder();
        String urlPath = "https://api.caiyunapp.com/v2.5/TwsDo9aQUYewFhV8/"
                + longitude + "," + latitude
                + "/weather.json?dailysteps=7&alert=true";
        WeatherBean weather = restTemplate.getForObject(urlPath, WeatherBean.class);
        // 今天南京市天气晴朗，最高温10摄氏度，最低温3摄氏度。
        // 空气质量不错，湿度较低，质量等级为优，湿度指数为45。未来三天天气晴朗，预计周末会下点小雨，伴随些许降温，请提前防范。
        // 今天空气不太好，在室内休息休息吧
        return weather.getResult().getForecast_keypoint();
    }
}