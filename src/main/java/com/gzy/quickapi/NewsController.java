package com.gzy.quickapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class NewsController {

    @GetMapping("/news")
    public String news() {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        String regex = "<p class=\"plc-title\">.*?</p>";
        Pattern pattern = Pattern.compile(regex);
        try {
            URL url = new URL("https://m.ithome.com");
            URLConnection connection = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(string);
                int i = 0;
                while (matcher.find()) {
                    result.append(matcher.group().replace("<p class=\"plc-title\">", "").replace("</p>", ""));
                    result.append(System.getProperty("line.separator"));
                    result.append(System.getProperty("line.separator"));
                    i++;
                    if (i > 5) {
                        break;
                    }
                }
            }
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
        System.out.println(result.toString());
        return result.toString();
    }
}
