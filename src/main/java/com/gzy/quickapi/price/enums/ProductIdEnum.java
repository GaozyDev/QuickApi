package com.gzy.quickapi.price.enums;

public enum ProductIdEnum {
    PS5_OPTICAL_DRIVE(0, "PS5光驱版", "https://search.smzdm.com/?c=home&s=ps5%E5%85%89%E9%A9%B1%E7%89%88&brand_id=249&min_price=3500&max_price=5500&v=b&p=1"),
    PS5_DIGITAL_EDITION(1, "PS5数字版", "https://search.smzdm.com/?c=home&s=ps5%E6%95%B0%E5%AD%97%E7%89%88&brand_id=249&min_price=3000&max_price=5000&v=b&p=1");

    private final Integer productId;

    private final String productName;

    private final String url;

    ProductIdEnum(Integer productId, String productName, String url) {
        this.productId = productId;
        this.productName = productName;
        this.url = url;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getUrl() {
        return url;
    }
}
