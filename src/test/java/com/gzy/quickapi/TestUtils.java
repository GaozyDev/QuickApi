package com.gzy.quickapi;

import com.gzy.quickapi.ps5.bmob.QueryBmobResults;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class TestUtils {

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
}
