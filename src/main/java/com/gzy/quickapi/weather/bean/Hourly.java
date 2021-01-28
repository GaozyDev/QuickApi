package com.gzy.quickapi.weather.bean;

import java.util.List;

class Hourly {
    String status;
    String description;
    List<StringValue> skycon;
    List<DoubleValue> cloudrate;
    List<IntValue> aqi;
    List<DoubleValue> dswrf;
    List<DoubleValue> visibility;
    List<DoubleValue> humidity;
    List<DoubleValue> pres;
    List<IntValue> pm25;
    List<DoubleValue> precipitation;
    List<Wind> wind;
    List<DoubleValue> temperature;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StringValue> getSkycon() {
        return skycon;
    }

    public void setSkycon(List<StringValue> skycon) {
        this.skycon = skycon;
    }

    public List<DoubleValue> getCloudrate() {
        return cloudrate;
    }

    public void setCloudrate(List<DoubleValue> cloudrate) {
        this.cloudrate = cloudrate;
    }

    public List<IntValue> getAqi() {
        return aqi;
    }

    public void setAqi(List<IntValue> aqi) {
        this.aqi = aqi;
    }

    public List<DoubleValue> getDswrf() {
        return dswrf;
    }

    public void setDswrf(List<DoubleValue> dswrf) {
        this.dswrf = dswrf;
    }

    public List<DoubleValue> getVisibility() {
        return visibility;
    }

    public void setVisibility(List<DoubleValue> visibility) {
        this.visibility = visibility;
    }

    public List<DoubleValue> getHumidity() {
        return humidity;
    }

    public void setHumidity(List<DoubleValue> humidity) {
        this.humidity = humidity;
    }

    public List<DoubleValue> getPres() {
        return pres;
    }

    public void setPres(List<DoubleValue> pres) {
        this.pres = pres;
    }

    public List<IntValue> getPm25() {
        return pm25;
    }

    public void setPm25(List<IntValue> pm25) {
        this.pm25 = pm25;
    }

    public List<DoubleValue> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(List<DoubleValue> precipitation) {
        this.precipitation = precipitation;
    }

    public List<Wind> getWind() {
        return wind;
    }

    public void setWind(List<Wind> wind) {
        this.wind = wind;
    }

    public List<DoubleValue> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<DoubleValue> temperature) {
        this.temperature = temperature;
    }

    static class StringValue {
        String value;
        String datetime;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

    static class IntValue {
        int value;
        String datetime;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

    static class DoubleValue {
        double value;
        String datetime;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

    static class Wind {
        double direction;
        double speed;
        String datetime;

        public double getDirection() {
            return direction;
        }

        public void setDirection(double direction) {
            this.direction = direction;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }
}


