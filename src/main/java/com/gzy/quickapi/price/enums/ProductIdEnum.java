package com.gzy.quickapi.price.enums;

public enum ProductIdEnum {
    PS5_OPTICAL_DRIVE(0, "PS5光驱版"),
    PS5_DIGITAL_EDITION(1, "PS5数字版");

    private final Integer productId;

    private final String productName;

    ProductIdEnum(Integer productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
