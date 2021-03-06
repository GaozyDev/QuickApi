package com.gzy.quickapi.ithome;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.options.PageNavigateOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ITHomeController {

    @GetMapping("/ITHome")
    public String itHome(@RequestParam(name = "num", defaultValue = "6") int num) {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL("https://m.ithome.com");
            URLConnection connection = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            result = matchItemData(stringBuilder.toString(), num);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
        return result;
    }

    private String matchItemData(String string, int num) {
        String regex = "<div class=\"plc-con\">.*?</div>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            String s = matcher.group();
            if (!s.contains("<span class=\"tip tip-gray\">广告</span>")) {
                stringBuilder.append(matchItemTitle(s));
                i++;
            }
            if (i > num) {
                break;
            }
        }

        return stringBuilder.toString();
    }

    private String matchItemTitle(String string) {
        String regex = "<p class=\"plc-title\">.*?</p>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            stringBuilder.append(matcher.group().replace("<p class=\"plc-title\">", "").replace("</p>", ""));
            stringBuilder.append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }

    @GetMapping("/ithome-test")
    public String isWorkingDay() {

        try {
            BrowserFetcher.downloadIfNotExist(null);
            ArrayList<String> argList = new ArrayList<>();
            argList.add("--no-sandbox");
            argList.add("--disable-setuid-sandbox");
            LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false)
                    .withExecutablePath("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe")
                    .withViewport(null).build();
            Browser browser = Puppeteer.launch(options);
            Page page = browser.newPage();

            PageNavigateOptions navigateOptions = new PageNavigateOptions();
            navigateOptions.setTimeout(1200000);
            page.goTo("https://m.ithome.com/", navigateOptions);
            page.waitForSelector("body > div.index-box > div.content > div:nth-child(1) > a > div.plc-con > p.plc-title").click();
            ElementHandle elementHandle = page.waitForSelector("body > div.page-box > div.con-box > div.news > main > h1");
            return (String) page.$eval("body > div.page-box > div.con-box > div.news > main > h1", "node => node.innerText");
        } catch (InterruptedException | IOException | ExecutionException e) {
            e.printStackTrace();
        }

        return "";
    }
}
