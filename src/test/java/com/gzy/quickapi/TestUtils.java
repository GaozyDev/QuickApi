package com.gzy.quickapi;

import com.gzy.quickapi.ps5.bmob.QueryBmobResults;
import com.gzy.quickapi.ps5.repository.ProductPriceInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
public class TestUtils {

    @Autowired
    private ProductPriceInfoRepository repository;

    @Test
    public void getData() {
        String url = "https://api2.bmob.cn/1/classes/PS5Price";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Bmob-Application-Id", "86855067edf9cf9d0132c02f2f7aed6e");
        headers.add("X-Bmob-REST-API-Key", "42779deb594bad1d40bf134c5a91dc6c");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String where = "{\"type\":" + 1 + "}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("where", where);

        ResponseEntity<QueryBmobResults> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, QueryBmobResults.class);
        System.out.println(response.getBody());
    }

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
