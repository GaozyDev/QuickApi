package com.gzy.quickapi.price.dto;

import java.util.List;

public class HistoryPriceInfo {

    private List<Double> averagePriceList;
    private List<Double> minAveragePriceList;
    private List<Double> minPriceList;
    private List<String> labelList;
    private double priceChange;

    public List<Double> getAveragePriceList() {
        return averagePriceList;
    }

    public void setAveragePriceList(List<Double> averagePriceList) {
        this.averagePriceList = averagePriceList;
    }

    public List<Double> getMinAveragePriceList() {
        return minAveragePriceList;
    }

    public void setMinAveragePriceList(List<Double> minAveragePriceList) {
        this.minAveragePriceList = minAveragePriceList;
    }

    public List<Double> getMinPriceList() {
        return minPriceList;
    }

    public void setMinPriceList(List<Double> minPriceList) {
        this.minPriceList = minPriceList;
    }

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }
}
