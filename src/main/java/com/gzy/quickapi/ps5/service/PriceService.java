package com.gzy.quickapi.ps5.service;

import com.gzy.quickapi.Constant;
import com.gzy.quickapi.ps5.dto.ProductPriceInfo;
import com.gzy.quickapi.ps5.dto.ProductPriceInfos;
import com.gzy.quickapi.ps5.dataobject.ProductPrice;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class PriceService {

    @Autowired
    private Constant constant;

    @Autowired
    private ProductPriceInfoRepository productPriceInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(PriceService.class.getName());

    // 光驱版
    public static ProductPriceInfos opticalDriveProductPriceInfos;

    // 数字版
    public static ProductPriceInfos digitalEditionProductPriceInfos;

    public ProductPriceInfos getPS5ProductData(PS5TypeEnum ps5TypeEnum) {
        String url;
        if (ps5TypeEnum == PS5TypeEnum.OPTICAL_DRIVE) {
            url = "https://search.smzdm.com/?c=home&s=ps5%E5%85%89%E9%A9%B1%E7%89%88&brand_id=249&min_price=3500&max_price=5500&v=b&p=1";
        } else {
            url = "https://search.smzdm.com/?c=home&s=ps5%E6%95%B0%E5%AD%97%E7%89%88&brand_id=249&min_price=3000&max_price=5000&v=b&p=1";
        }

        List<ProductPriceInfo> productPriceInfoList = startWebCrawler(url);

        ProductPriceInfos productPriceInfos = new ProductPriceInfos();
        productPriceInfos.setUpdateDate(new Date());
        productPriceInfos.setProductDataList(productPriceInfoList);
        double averagePrice = calAveragePrice(productPriceInfoList);
        double minAveragePrice = calAveragePrice(productPriceInfoList.subList(0, productPriceInfoList.size() / 5));
        double minPrice = calMinPrice(productPriceInfoList);
        productPriceInfos.setAveragePrice(averagePrice);
        productPriceInfos.setMinAveragePrice(minAveragePrice);
        productPriceInfos.setMinPrice(minPrice);

        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(KeyUtil.genUniqueKey());
        productPrice.setAveragePrice(productPriceInfos.getAveragePrice());
        productPrice.setMinAveragePrice(productPriceInfos.getMinAveragePrice());
        productPrice.setMinPrice(productPriceInfos.getMinPrice());
        productPrice.setProductId(String.valueOf(ps5TypeEnum.getTypeCode()));
        productPriceInfoRepository.save(productPrice);

        return productPriceInfos;
    }

    public List<ProductPriceInfo> startWebCrawler(String url) {
        List<ProductPriceInfo> productPriceInfoList = new ArrayList<>();
        try {
            Browser browser = launchBrowser();
            Page page = browser.newPage();
            page.goTo(url);
            parsePage(page, productPriceInfoList);

            boolean hasNextPage;
            List<ElementHandle> pages = page.$$("#J_feed_pagenation li");
            ElementHandle elementHandle = pages.get(pages.size() - 1);
            String text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
            hasNextPage = "下一页".equals(text);
            while (hasNextPage) {
                elementHandle.click();
                parsePage(page, productPriceInfoList);
                pages = page.$$("#J_feed_pagenation li");
                elementHandle = pages.get(pages.size() - 1);
                text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
                hasNextPage = "下一页".equals(text);
            }

            page.close();
            browser.close();

            productPriceInfoList.sort(Comparator.comparing(ProductPriceInfo::getPrice));
        } catch (InterruptedException | IOException | ExecutionException e) {
            e.printStackTrace();
            logger.error("[浏览器错误]" + e);
        }

        return productPriceInfoList;
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

    private void parsePage(Page page, List<ProductPriceInfo> productPriceInfoList) {
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
                        ProductPriceInfo productPriceInfo = new ProductPriceInfo(title, price, platform);
                        productPriceInfoList.add(productPriceInfo);
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

    private double calAveragePrice(List<ProductPriceInfo> productPriceInfoList) {
        double averagePrice = Double.MAX_VALUE;
        OptionalDouble average = productPriceInfoList.stream().mapToDouble(ProductPriceInfo::getPrice).average();
        if (average.isPresent()) {
            averagePrice = average.getAsDouble();
        }
        return averagePrice;
    }

    private double calMinPrice(List<ProductPriceInfo> productPriceInfoList) {
        double minPrice = Double.MAX_VALUE;
        OptionalDouble min = productPriceInfoList.stream().mapToDouble(ProductPriceInfo::getPrice).min();
        if (min.isPresent()) {
            minPrice = min.getAsDouble();
        }
        return minPrice;
    }

    public List<ProductPrice> getPS5HistoryPrice(PS5TypeEnum ps5TypeEnum) {
        return productPriceInfoRepository.findByProductId(String.valueOf(ps5TypeEnum.getTypeCode()));
    }
}
