package com.gzy.quickapi.price.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ProductPrice {

    @Id
    private String id;

    private String productId;

    private double averagePrice;

    private double minAveragePrice;

    private double minPrice;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
