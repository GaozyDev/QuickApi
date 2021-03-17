package com.gzy.quickapi;

import com.gzy.quickapi.dataimport.PriceBmob;
import com.gzy.quickapi.dataimport.QueryBmobResults;
import com.gzy.quickapi.ps5.dataobject.ProductPrice;
import com.gzy.quickapi.ps5.repository.ProductPriceInfoRepository;
import com.gzy.quickapi.ps5.utils.KeyUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestUtils {

    @Autowired
    private ProductPriceInfoRepository repository;

    @Test
    public void importData() {

        String url = "https://api2.bmob.cn/1/classes/PS5Price";
        RestTemplate restTemplate = new RestTemplate();
        String where = "{\"type\":" + 0 + "}&order=createDate&limit=500";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("where", where);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Bmob-Application-Id", "99f509d6b7172a5793738a41f819b98e");
        headers.add("X-Bmob-REST-API-Key", "baa794766cccdec74805c9d81fb60ab3");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<QueryBmobResults> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, QueryBmobResults.class);

        List<ProductPrice> prices = new ArrayList<>();
        for (PriceBmob result : response.getBody().getResults()) {
            ProductPrice productPrice = new ProductPrice();
            productPrice.setId(result.getObjectId());
            productPrice.setAveragePrice(result.getAveragePrice());
            productPrice.setMinAveragePrice(result.getMinAveragePrice());
            productPrice.setMinPrice(result.getMinPrice());
            productPrice.setProductId(String.valueOf(result.getType()));
            productPrice.setCreateTime(result.getCreateDate());
            prices.add(productPrice);
        }

        List<ProductPrice> result = repository.saveAll(prices);
    }

    @Test
    public void saveTest() {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(KeyUtil.genUniqueKey());
        productPrice.setAveragePrice(2000);
        productPrice.setMinAveragePrice(2000);
        productPrice.setMinPrice(2000);
        productPrice.setProductId("456");
        productPrice.setCreateTime(new Date());
        repository.save(productPrice);
    }
}
