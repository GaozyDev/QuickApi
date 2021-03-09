package com.gzy.quickapi.ps5;

import com.gzy.quickapi.ps5.bean.BmobResult;
import com.gzy.quickapi.ps5.bean.PriceBmob;
import com.gzy.quickapi.ps5.bean.PriceData;
import com.gzy.quickapi.ps5.bean.ProductData;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PS5Service {

    private String chromePathMac = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";

    private String chromePathPc = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";

    public PriceData getPS5ProductData(int type) {
        String url;
        if (type == 0) {
            url = "https://search.smzdm.com/?c=home&s=ps5%E5%85%89%E9%A9%B1%E7%89%88&brand_id=249&min_price=3500&max_price=5500&v=b&p=1";
        } else {
            url = "https://search.smzdm.com/?c=home&s=ps5%E6%95%B0%E5%AD%97%E7%89%88&brand_id=249&min_price=3000&max_price=5000&v=b&p=1";
        }
        PriceData priceData = startWebCrawler(url);
        PriceBmob priceBmob = new PriceBmob();
        priceBmob.setAveragePrice(priceData.getAveragePrice());
        priceBmob.setMinPrice(priceData.getMinPrice());
        priceBmob.setType(type);
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
            BrowserFetcher.downloadIfNotExist(null);
            ArrayList<String> argList = new ArrayList<>();
            argList.add("--no-sandbox");
            argList.add("--disable-setuid-sandbox");
            LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false)
                    .withExecutablePath(chromePathMac).build();
            Browser browser = Puppeteer.launch(options);

            Page page = browser.newPage();
            page.goTo(url);

            parsePage(page, productDataList);

//            boolean hasNextPage;
//            List<ElementHandle> pages = page.$$("#J_feed_pagenation li");
//            ElementHandle elementHandle = pages.get(pages.size() - 1);
//            String text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
//            hasNextPage = "下一页".equals(text);

//            while (hasNextPage) {
//                elementHandle.click();
//                parsePage(page, productDataList);
//                pages = page.$$("#J_feed_pagenation li");
//                elementHandle = pages.get(pages.size() - 1);
//                text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
//                hasNextPage = "下一页".equals(text);
//            }

            page.close();
            browser.close();

            double totalPrice = 0;
            double minPrice = Double.MAX_VALUE;
            double averagePrice;
            for (ProductData productData : productDataList) {
                double price = productData.getPrice();
                totalPrice += price;
                if (minPrice > price) {
                    minPrice = price;
                }
            }

            averagePrice = totalPrice / productDataList.size();
            priceData.setAveragePrice(averagePrice);
            priceData.setMinPrice(minPrice);
            return priceData;
        } catch (InterruptedException | IOException | ExecutionException e) {
            e.printStackTrace();
        }

        return priceData;
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
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void savePS5Price(PriceBmob priceBmob) {
        String url = "https://api2.bmob.cn/1/classes/PS5Price";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Bmob-Application-Id", "86855067edf9cf9d0132c02f2f7aed6e");
        headers.add("X-Bmob-REST-API-Key", "42779deb594bad1d40bf134c5a91dc6c");
        HttpEntity<PriceBmob> httpEntity = new HttpEntity<>(priceBmob, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println(stringResponseEntity.getBody());
    }

    public BmobResult getPS5HistoryPrice(int type) {
//        String url = "https://api2.bmob.cn/1/classes/PS5Price?where={\"type\":" + type + "}";
        String url = "https://api2.bmob.cn/1/classes/PS5Price";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Bmob-Application-Id", "86855067edf9cf9d0132c02f2f7aed6e");
        headers.add("X-Bmob-REST-API-Key", "42779deb594bad1d40bf134c5a91dc6c");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<BmobResult> response = restTemplate.exchange(url, HttpMethod.GET, entity, BmobResult.class);
        return response.getBody();
    }
}
