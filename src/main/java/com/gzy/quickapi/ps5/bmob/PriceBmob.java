package com.gzy.quickapi.ps5.bmob;

import java.util.Date;

public class PriceBmob {

    private double averagePrice;

    private double minAveragePrice;

    private double minPrice;

    private int type;

    private Date createDate;

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public double getMinAveragePrice() {
        return minAveragePrice;
    }

    public void setMinAveragePrice(double minAveragePrice) {
        this.minAveragePrice = minAveragePrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
