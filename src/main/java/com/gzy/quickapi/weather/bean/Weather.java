package com.gzy.quickapi.weather.bean;

import java.util.List;

public class Weather {
    String status;
    String lang;
    int server_time;
    String api_status;
    int tzshift;
    String api_version;
    String unit;
    Result result;
    List<Double> location;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getServer_time() {
        return server_time;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }

    public String getApi_status() {
        return api_status;
    }

    public void setApi_status(String api_status) {
        this.api_status = api_status;
    }

    public int getTzshift() {
        return tzshift;
    }

    public void setTzshift(int tzshift) {
        this.tzshift = tzshift;
    }

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public static class Result {
        String forecast_keypoint;
        int primary;
        Realtime realtime;
        Minutely minutely;
        Hourly hourly;
        Daily daily;

        public String getForecast_keypoint() {
            return forecast_keypoint;
        }

        public void setForecast_keypoint(String forecast_keypoint) {
            this.forecast_keypoint = forecast_keypoint;
        }

        public int getPrimary() {
            return primary;
        }

        public void setPrimary(int primary) {
            this.primary = primary;
        }

        public Realtime getRealtime() {
            return realtime;
        }

        public void setRealtime(Realtime realtime) {
            this.realtime = realtime;
        }

        public Minutely getMinutely() {
            return minutely;
        }

        public void setMinutely(Minutely minutely) {
            this.minutely = minutely;
        }

        public Hourly getHourly() {
            return hourly;
        }

        public void setHourly(Hourly hourly) {
            this.hourly = hourly;
        }

        public Daily getDaily() {
            return daily;
        }

        public void setDaily(Daily daily) {
            this.daily = daily;
        }
    }
}
