package com.gzy.quickapi.ps5;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class PS5Controller {

    private Date lastUpdateDate = new Date(0);

    private final List<Double> priceList = new ArrayList<>();

    private final StringBuilder html = new StringBuilder();

    @GetMapping("/ps5")
    public String ps5Price(@RequestParam(name = "digital", defaultValue = "false") boolean digital) {

        Date currentDate = new Date();
        if (currentDate.getTime() - lastUpdateDate.getTime() < 1000 * 60 * 10) {
            return html.toString();
        }

        lastUpdateDate = currentDate;
        priceList.clear();
        html.setLength(0);

        try {
            BrowserFetcher.downloadIfNotExist(null);
            ArrayList<String> argList = new ArrayList<>();
            argList.add("--no-sandbox");
            argList.add("--disable-setuid-sandbox");
            LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false)
                    .withExecutablePath("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe").build();
            Browser browser = Puppeteer.launch(options);

            Page page = browser.newPage();
            if (digital) {
                page.goTo("https://search.smzdm.com/?c=home&s=ps5%E6%95%B0%E5%AD%97%E7%89%88&brand_id=249&min_price=3000&max_price=5000&v=b&p=1");
            } else {
                page.goTo("https://search.smzdm.com/?c=home&s=ps5%E5%85%89%E9%A9%B1%E7%89%88&brand_id=249&min_price=3500&max_price=5500&v=b&p=1");
            }

            parsePage(page, html, priceList);

            boolean hasNextPage;
            List<ElementHandle> pages = page.$$("#J_feed_pagenation li");
            ElementHandle elementHandle = pages.get(pages.size() - 1);
            String text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
            hasNextPage = "下一页".equals(text);

            while (hasNextPage) {
                elementHandle.click();
                parsePage(page, html, priceList);
                pages = page.$$("#J_feed_pagenation li");
                elementHandle = pages.get(pages.size() - 1);
                text = (String) elementHandle.$eval("a", "node => node.innerText", new ArrayList<>());
                hasNextPage = "下一页".equals(text);
            }

            page.close();

            double totalPrice = 0;
            double minPrice = Double.MAX_VALUE;
            for (Double aDouble : priceList) {
                totalPrice += aDouble;
                if (minPrice > aDouble) {
                    minPrice = aDouble;
                }
            }
            double averagePrice = totalPrice / priceList.size();
            html.append("<h3>")
                    .append("平均价:").append(averagePrice)
                    .append("最低价:").append(minPrice)
                    .append("</h3>");
            return html.toString();
        } catch (InterruptedException | IOException | ExecutionException e) {
            e.printStackTrace();
        }

        return html.toString();
    }

    private void parsePage(Page page, StringBuilder html, List<Double> priceList) {
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
                        html.append("<div style=\"line-height:30px;\">")
                                .append("title:").append(title).append(" priceText:").append(priceText).append(" platform:").append(platform).append("\n")
                                .append("</div>");
                        priceList.add(price);
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
