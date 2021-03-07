package com.gzy.quickapi.ps5;

public class ProductInfo {

    private String title;

    private double price;

    private String platform;

    public ProductInfo(String title, double price, String platform) {
        this.title = title;
        this.price = price;
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
