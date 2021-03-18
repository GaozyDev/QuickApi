package com.gzy.quickapi.price.crawler;

import com.gzy.quickapi.Constant;
import com.gzy.quickapi.price.dto.ProductPriceInfo;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class.getName());

    @Autowired
    private Constant constant;

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
}
