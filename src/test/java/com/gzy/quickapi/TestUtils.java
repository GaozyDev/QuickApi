package com.gzy.quickapi;

import com.gzy.quickapi.ps5.repository.ProductPriceInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUtils {

    @Autowired
    private ProductPriceInfoRepository repository;

    @Test
    public void saveTest() {
//        ProductPriceInfo productPriceInfo = new ProductPriceInfo();
//        productPriceInfo.setId("123456");
//        productPriceInfo.setAveragePrice(new BigDecimal("3000"));
//        productPriceInfo.setMinAveragePrice(new BigDecimal("3000"));
//        productPriceInfo.setMinPrice(new BigDecimal("3000"));
//        productPriceInfo.setProductId("123");
//
//        ProductPriceInfo result = repository.save(productPriceInfo);

//        List<ProductPriceInfo> list = repository.findAll();
    }
}
