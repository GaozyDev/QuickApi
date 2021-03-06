package com.gzy.quickapi.taobao;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.Device;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.options.PageNavigateOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RestController
public class TaobaoController {

    @GetMapping("/taobao")
    public String isWorkingDay() {

        try {
            BrowserFetcher.downloadIfNotExist(null);
            ArrayList<String> argList = new ArrayList<>();
            argList.add("--no-sandbox");
            argList.add("--disable-setuid-sandbox");
            LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false)
                    .withExecutablePath("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe").build();
            Browser browser = Puppeteer.launch(options);
            Page page = browser.newPage();
            Device device = Device.IPAD;
            page.emulate(device);
            //(
            //        '''() =>{ Object.defineProperties(navigator,{ webdriver:{ get: () => false } }) }''')
            PageNavigateOptions navigateOptions = new PageNavigateOptions();
            navigateOptions.setTimeout(1200000);
            page.goTo("https://h5.m.taobao.com/awp/core/detail.htm?id=620825667183", navigateOptions);
            page.waitForSelector("#sys_list\\$sys_list_10014 > div:nth-child(9) > div > div:nth-child(2) > div:nth-child(1) > span").click();
//            ElementHandle elementHandle = page.waitForSelector("body > div.page-box > div.con-box > div.news > main > h1");
            return "finish";
        } catch (InterruptedException | IOException | ExecutionException e) {
            e.printStackTrace();
        }

        return "";
    }
}
