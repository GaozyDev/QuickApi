package com.gzy.quickapi.price.dto;

import java.util.Date;
import java.util.List;

public class AveragePriceInfo {

    private Date updateDate;

    private List<ProductPriceInfo> productPriceInfoList;

    private double averagePrice;

    private double minAveragePrice;

    private double minPrice;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<ProductPriceInfo> getProductDataList() {
        return productPriceInfoList;
    }

    public void setProductDataList(List<ProductPriceInfo> productData) {
        this.productPriceInfoList = productData;
    }

    public double getMinAveragePrice() {
        return minAveragePrice;
    }

    public void setMinAveragePrice(double minAveragePrice) {
        this.minAveragePrice = minAveragePrice;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
}
