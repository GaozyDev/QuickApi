package com.gzy.quickapi.weather.bean;

import java.util.List;

class Minutely {
    String status;
    String description;
    List<Double> probability;
    String datasource;
    List<Double> precipitation_2h;
    List<Double> precipitation;

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

    public List<Double> getProbability() {
        return probability;
    }

    public void setProbability(List<Double> probability) {
        this.probability = probability;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public List<Double> getPrecipitation_2h() {
        return precipitation_2h;
    }

    public void setPrecipitation_2h(List<Double> precipitation_2h) {
        this.precipitation_2h = precipitation_2h;
    }

    public List<Double> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(List<Double> precipitation) {
        this.precipitation = precipitation;
    }
}