package com.gzy.quickapi.ps5.service;

import com.gzy.quickapi.Constant;
import com.gzy.quickapi.ps5.bmob.PriceBmob;
import com.gzy.quickapi.ps5.bmob.QueryBmobResults;
import com.gzy.quickapi.ps5.data.PriceData;
import com.gzy.quickapi.ps5.data.ProductData;
import com.gzy.quickapi.ps5.dataobject.ProductPriceInfo;
import com.gzy.quickapi.ps5.enums.PS5TypeEnum;
import com.gzy.quickapi.ps5.repository.ProductPriceInfoRepository;
import com.gzy.quickapi.ps5.utils.KeyUtil;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class PS5Service {

    @Autowired
    private Constant constant;

    @Autowired
    private ProductPriceInfoRepository productPriceInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(PS5Service.class.getName());

    // 光驱版
    public static PriceData opticalDrivePriceData;

    // 数字版
    public static PriceData digitalEditionPriceData;

    public PriceData getPS5ProductData(PS5TypeEnum ps5TypeEnum) {
        String url;
        if (ps5TypeEnum == PS5TypeEnum.OPTICAL_DRIVE) {
            url = "https://search.smzdm.com/?c=home&s=ps5%E5%85%89%E9%A9%B1%E7%89%88&brand_id=249&min_price=3500&max_price=5500&v=b&p=1";
        } else {
            url = "https://search.smzdm.com/?c=home&s=ps5%E6%95%B0%E5%AD%97%E7%89%88&brand_id=249&min_price=3000&max_price=5000&v=b&p=1";
        }
        PriceData priceData = startWebCrawler(url);
        PriceBmob priceBmob = new PriceBmob();
        priceBmob.setAveragePrice(priceData.getAveragePrice());
        priceBmob.setMinAveragePrice(priceData.getMinAveragePrice());
        priceBmob.setMinPrice(priceData.getMinPrice());
        priceBmob.setType(ps5TypeEnum.getTypeCode());
        priceBmob.setCreateDate(new Date());
        savePS5Price(priceBmob);
        return priceData;
    }

    public PriceData startWebCrawler(String url) {
        PriceData priceData = new PriceData();
        priceData.setUpdateDate(new Date());
        List<ProductData> productDataList = new ArrayList<>();
        priceData.setProductDataList(productDataList);

        try {
            Browser browser = launchBrowser();
            Page page = browser.newPage();
            page.goTo(url);

            parsePage(page, productDataList);

            boolean hasNextPage;
            List<ElementHandle> pages = page.$$("#J_feed_pagenation li");
            ElementHandle elementHandle = pages.get(pages.size() - 1);
            String text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
            hasNextPage = "下一页".equals(text);

            while (hasNextPage) {
                elementHandle.click();
                parsePage(page, productDataList);
                pages = page.$$("#J_feed_pagenation li");
                elementHandle = pages.get(pages.size() - 1);
                text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
                hasNextPage = "下一页".equals(text);
            }

            page.close();
            browser.close();

            productDataList.sort(Comparator.comparing(ProductData::getPrice));

            double averagePrice = calAveragePrice(productDataList);
            double minAveragePrice = calAveragePrice(productDataList.subList(0, productDataList.size() / 5));
            double minPrice = calMinPrice(productDataList);

            priceData.setAveragePrice(averagePrice);
            priceData.setMinAveragePrice(minAveragePrice);
            priceData.setMinPrice(minPrice);
            return priceData;
        } catch (InterruptedException | IOException | ExecutionException e) {
            e.printStackTrace();
            logger.error("[浏览器错误]" + e);
        }

        return priceData;
    }

    private Browser launchBrowser() throws InterruptedException, ExecutionException, IOException {
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> argList = new ArrayList<>();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(true)
                .withExecutablePath(constant.getChromePath()).build();
        return Puppeteer.launch(options);
    }

    private void parsePage(Page page, List<ProductData> productDataList) {
        try {
            page.waitForSelector("#feed-main-list");
            List<ElementHandle> elementHandles = page.$$("#feed-main-list .feed-row-wide");
            for (ElementHandle elementHandle : elementHandles) {
                try {
                    ElementHandle tag = elementHandle.$(".feed-block span.search-pastdue-mark");
                    if (tag != null) {
                        // 商品过期，售罄
                        continue;
                    }

                    String title = (String) elementHandle.$eval(".feed-block h5.feed-block-title a.feed-nowrap", "node => node.innerText", new ArrayList<>());
                    String priceText = (String) elementHandle.$eval(".feed-block h5.feed-block-title div.z-highlight", "node => node.innerText", new ArrayList<>());
                    String platform = (String) elementHandle.$eval(".feed-block div.z-feed-foot span.feed-block-extras span", "node => node.innerText", new ArrayList<>());
                    String[] text = priceText.split("元");
                    if (text.length >= 1) {
                        double price = Double.parseDouble(text[0]);
                        ProductData productData = new ProductData(title, price, platform);
                        productDataList.add(productData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("[HTML解析错误]" + e);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("[抓包错误]" + e);
        }
    }

    private double calAveragePrice(List<ProductData> productDataList) {
        double averagePrice = Double.MAX_VALUE;
        OptionalDouble average = productDataList.stream().mapToDouble(ProductData::getPrice).average();
        if (average.isPresent()) {
            averagePrice = average.getAsDouble();
        }
        return averagePrice;
    }

    private double calMinPrice(List<ProductData> productDataList) {
        double minPrice = Double.MAX_VALUE;
        OptionalDouble min = productDataList.stream().mapToDouble(ProductData::getPrice).min();
        if (min.isPresent()) {
            minPrice = min.getAsDouble();
        }
        return minPrice;
    }

    public void savePS5Price(PriceBmob priceBmob) {
        String url = "https://api2.bmob.cn/1/classes/PS5Price";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Bmob-Application-Id", constant.getBmobAppId());
        headers.add("X-Bmob-REST-API-Key", constant.getBmobAppKey());
        HttpEntity<PriceBmob> httpEntity = new HttpEntity<>(priceBmob, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println(stringResponseEntity.getBody());

        ProductPriceInfo productPriceInfo = new ProductPriceInfo();
        productPriceInfo.setId(KeyUtil.genUniqueKey());
        productPriceInfo.setAveragePrice(BigDecimal.valueOf(priceBmob.getAveragePrice()));
        productPriceInfo.setMinAveragePrice(BigDecimal.valueOf(priceBmob.getMinAveragePrice()));
        productPriceInfo.setMinPrice(BigDecimal.valueOf(priceBmob.getMinPrice()));
        productPriceInfo.setProductId(String.valueOf(priceBmob.getType()));

        ProductPriceInfo result = productPriceInfoRepository.save(productPriceInfo);
    }

    public QueryBmobResults getPS5HistoryPrice(PS5TypeEnum ps5TypeEnum) {
        QueryBmobResults queryBmobResults = new QueryBmobResults();
        List<PriceBmob> priceBmobList = new ArrayList<>();
        queryBmobResults.setResults(priceBmobList);

        List<ProductPriceInfo> list = productPriceInfoRepository.findAll();

        for (ProductPriceInfo productPriceInfo : list) {
            PriceBmob priceBmob = new PriceBmob();
            priceBmob.setType(Integer.parseInt(productPriceInfo.getProductId()));
            priceBmob.setAveragePrice(productPriceInfo.getAveragePrice().doubleValue());
            priceBmob.setMinAveragePrice(productPriceInfo.getMinAveragePrice().doubleValue());
            priceBmob.setMinPrice(productPriceInfo.getMinPrice().doubleValue());
            priceBmob.setCreateDate(new Date());
            priceBmobList.add(priceBmob);
        }

        return queryBmobResults;
    }
}
