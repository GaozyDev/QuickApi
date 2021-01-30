package com.gzy.quickapi.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        WeatherBean weatherBean = restTemplate.getForObject(urlPath, WeatherBean.class);

        WeatherBean.ResultBean resultBean = weatherBean.getResult();
        WeatherBean.ResultBean.RealtimeBean realtimeBean = resultBean.getRealtime();
        WeatherBean.ResultBean.MinutelyBean minutelyBean = resultBean.getMinutely();
        WeatherBean.ResultBean.HourlyBean hourlyBean = resultBean.getHourly();
        WeatherBean.ResultBean.DailyBean dailyBean = resultBean.getDaily();

        // 今日天气
        buildSkyconDesc(stringBuilder, dailyBean);
        // 今日气温
        buildTempDesc(stringBuilder, dailyBean);
        // 当前空气质量
        buildAirDesc(stringBuilder, realtimeBean);
        // 当前空气湿度
        buildHumidityDesc(stringBuilder, realtimeBean);
        // 未来一周天气
        boolean hasBadSkycon = buildWeekSkyconDesc(stringBuilder, dailyBean);
        // 未来一周气温
        boolean hasBadTemp = buildWeekTempDesc(stringBuilder, dailyBean);

        stringBuilder.append("友情提醒你一句：").append(resultBean.getForecast_keypoint()).append("。");

        return stringBuilder.toString();
    }

    private void buildSkyconDesc(StringBuilder stringBuilder, WeatherBean.ResultBean.DailyBean dailyBean) {
        String weather = dailyBean.getSkycon().get(0).getValue();
        switch (weather) {
            case "CLEAR_DAY":
            case "CLEAR_NIGHT":
                stringBuilder.append("晴朗");
                break;
            case "PARTLY_CLOUDY_DAY":
            case "PARTLY_CLOUDY_NIGHT":
                stringBuilder.append("多云");
                break;
            case "CLOUDY":
                stringBuilder.append("阴");
                break;
            case "LIGHT_HAZE":
                stringBuilder.append("轻度雾霾");
                break;
            case "MODERATE_HAZE":
                stringBuilder.append("中度雾霾");
                break;
            case "HEAVY_HAZE":
                stringBuilder.append("重度雾霾");
                break;
            case "LIGHT_RAIN":
                stringBuilder.append("小雨");
                break;
            case "MODERATE_RAIN":
                stringBuilder.append("中雨");
                break;
            case "HEAVY_RAIN":
                stringBuilder.append("大雨");
                break;
            case "STORM_RAIN":
                stringBuilder.append("暴雨");
                break;
            case "FOG":
                stringBuilder.append("雾");
                break;
            case "LIGHT_SNOW":
                stringBuilder.append("小雪");
                break;
            case "MODERATE_SNOW":
                stringBuilder.append("中雪");
                break;
            case "HEAVY_SNOW":
                stringBuilder.append("大雪");
                break;
            case "STORM_SNOW":
                stringBuilder.append("暴雪");
                break;
            case "DUST":
                stringBuilder.append("浮尘");
                break;
            case "SAND":
                stringBuilder.append("沙尘");
                break;
            case "WIND":
                stringBuilder.append("大风");
                break;
            default:
                stringBuilder.append("未知");
                break;
        }
        stringBuilder.append("，");
    }

    private void buildTempDesc(StringBuilder stringBuilder, WeatherBean.ResultBean.DailyBean dailyBean) {
        WeatherBean.ResultBean.DailyBean.TemperatureBeanX temperatureBeanX = dailyBean.getTemperature().get(0);
        int tempMax = temperatureBeanX.getMax();
        int tempMin = temperatureBeanX.getMin();
        // 今日最高温最低温
        stringBuilder.append("最高温").append(tempMax).append("度，")
                .append("最低温").append(tempMin).append("度。");
    }

    private void buildAirDesc(StringBuilder stringBuilder, WeatherBean.ResultBean.RealtimeBean realtimeBean) {
        String airQualityDesc = realtimeBean.getAir_quality().getDescription().getChn();
        stringBuilder.append("当前空气质量").append(airQualityDesc).append("，");
    }

    private void buildHumidityDesc(StringBuilder stringBuilder, WeatherBean.ResultBean.RealtimeBean realtimeBean) {
        double humidity = realtimeBean.getHumidity();
        stringBuilder.append("空气相对湿度").append(humidity).append("。");
    }

    private boolean buildWeekSkyconDesc(StringBuilder stringBuilder, WeatherBean.ResultBean.DailyBean dailyBean) {
        List<WeatherBean.ResultBean.DailyBean.SkyconBeanX> skyconBeanXs = dailyBean.getSkycon();
        int clearDayCount = 0;
        int partlyCloudyDayCount = 0;
        int cloudyDayCount = 0;
        boolean hasBadWeather = false;
        for (int i = 1; i < skyconBeanXs.size(); i++) {
            WeatherBean.ResultBean.DailyBean.SkyconBeanX skyconBeanX = skyconBeanXs.get(i);
            String weather = skyconBeanX.getValue();
            if (weather.equals("LIGHT_RAIN") || weather.equals("MODERATE_RAIN")
                    || weather.equals("HEAVY_RAIN") || weather.equals("STORM_RAIN")) {
                stringBuilder.append("未来一周内有降雨，");
                hasBadWeather = true;
                break;
            }

            if (weather.equals("LIGHT_SNOW") || weather.equals("MODERATE_SNOW")
                    || weather.equals("HEAVY_SNOW") || weather.equals("STORM_SNOW")) {
                stringBuilder.append("未来一周内有降雪，");
                hasBadWeather = true;
                break;
            }

            if (weather.equals("LIGHT_HAZE") || weather.equals("MODERATE_HAZE")
                    || weather.equals("HEAVY_HAZE")) {
                stringBuilder.append("未来一周内有雾霾天气，");
                hasBadWeather = true;
                break;
            }

            if (weather.equals("CLEAR_DAY") || weather.equals("CLEAR_NIGHT")) {
                clearDayCount++;
            }

            if (weather.equals("PARTLY_CLOUDY_DAY") || weather.equals("PARTLY_CLOUDY_NIGHT")) {
                partlyCloudyDayCount++;
            }

            if (weather.equals("CLOUDY")) {
                cloudyDayCount++;
            }
        }

        if (!hasBadWeather) {
            if (clearDayCount > partlyCloudyDayCount / 2 + cloudyDayCount) {
                stringBuilder.append("未来一周大部分天气不错");
            } else {
                stringBuilder.append("未来一周大部分阴天");
            }
        }

        return hasBadWeather;
    }

    private boolean buildWeekTempDesc(StringBuilder stringBuilder, WeatherBean.ResultBean.DailyBean dailyBean) {
        List<WeatherBean.ResultBean.DailyBean.TemperatureBeanX> temperatureBeanXs = dailyBean.getTemperature();
        boolean hasBadWeather = false;
        double todayTemp = temperatureBeanXs.get(0).getAvg();
        double maxTempRise = 0;
        double maxTempDrop = 0;
        for (int i = 1; i < temperatureBeanXs.size(); i++) {
            WeatherBean.ResultBean.DailyBean.TemperatureBeanX temperatureBeanX = temperatureBeanXs.get(i);
            if (temperatureBeanX.getAvg() >= todayTemp) {
                maxTempRise = Math.max(maxTempRise, temperatureBeanX.getAvg() - todayTemp);
            } else {
                maxTempDrop = Math.min(maxTempDrop, temperatureBeanX.getAvg() - todayTemp);
            }
        }

        if (Math.abs(maxTempDrop) <= 5 && maxTempRise <= 5) {
            stringBuilder.append("温度变化不大。");
        } else if (Math.abs(maxTempDrop) >= maxTempRise) {
            stringBuilder.append("温度大幅度下降。");
            hasBadWeather = true;
        } else {
            stringBuilder.append("温度大幅度上升。");
        }
        return hasBadWeather;
    }
}