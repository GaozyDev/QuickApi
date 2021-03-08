package com.gzy.quickapi.ps5;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PS5Service {

    public ResultData getPS5OpticalDriveProductData() {
        return startWebCrawler("https://search.smzdm.com/?c=home&s=ps5%E5%85%89%E9%A9%B1%E7%89%88&brand_id=249&min_price=3500&max_price=5500&v=b&p=1");
    }

    public ResultData getPS5ProductData() {
        return startWebCrawler("https://search.smzdm.com/?c=home&s=ps5%E6%95%B0%E5%AD%97%E7%89%88&brand_id=249&min_price=3000&max_price=5000&v=b&p=1");
    }

    public ResultData startWebCrawler(String url) {
        ResultData resultData = new ResultData();
        resultData.setUpdateDate(new Date());
        List<ProductInfo> productInfos = new ArrayList<>();
        resultData.setProductInfos(productInfos);

        try {
            BrowserFetcher.downloadIfNotExist(null);
            ArrayList<String> argList = new ArrayList<>();
            argList.add("--no-sandbox");
            argList.add("--disable-setuid-sandbox");
            LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false)
                    .withExecutablePath("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome").build();
            Browser browser = Puppeteer.launch(options);

            Page page = browser.newPage();
            page.goTo(url);

            parsePage(page, productInfos);

            boolean hasNextPage;
            List<ElementHandle> pages = page.$$("#J_feed_pagenation li");
            ElementHandle elementHandle = pages.get(pages.size() - 1);
            String text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
            hasNextPage = "下一页".equals(text);

            while (hasNextPage) {
                elementHandle.click();
                parsePage(page, productInfos);
                pages = page.$$("#J_feed_pagenation li");
                elementHandle = pages.get(pages.size() - 1);
                text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
                hasNextPage = "下一页".equals(text);
            }

            page.close();

            double totalPrice = 0;
            double minPrice = Double.MAX_VALUE;
            double averagePrice;
            for (ProductInfo productInfo : productInfos) {
                double price = productInfo.getPrice();
                totalPrice += price;
                if (minPrice > price) {
                    minPrice = price;
                }
            }

            averagePrice = totalPrice / productInfos.size();

            resultData.setAveragePrice(averagePrice);
            resultData.setMinPrice(minPrice);
            return resultData;
        } catch (InterruptedException | IOException | ExecutionException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    private void parsePage(Page page, List<ProductInfo> productInfos) {
        try {
            page.waitForSelector("#feed-main-list");
            List<ElementHandle> elementHandles = page.$$("#feed-main-list .feed-row-wide");
            for (ElementHandle elementHandle : elementHandles) {
                try {
                    String title = (String) elementHandle.$eval(".feed-block h5.feed-block-title a.feed-nowrap", "node => node.innerText", new ArrayList<>());
                    String priceText = (String) elementHandle.$eval(".feed-block h5.feed-block-title div.z-highlight", "node => node.innerText", new ArrayList<>());
                    String platform = (String) elementHandle.$eval(".feed-block div.z-feed-foot span.feed-block-extras span", "node => node.innerText", new ArrayList<>());
                    String[] text = priceText.split("元");
                    if (text.length >= 1) {
                        double price = Double.parseDouble(text[0]);
                        ProductInfo productInfo = new ProductInfo(title, price, platform);
                        productInfos.add(productInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
