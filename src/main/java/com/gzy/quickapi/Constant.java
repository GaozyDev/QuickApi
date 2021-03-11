package com.gzy.quickapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "constant")
public class Constant {

    private String chromePath;

    private String bmobAppId;

    private String bmobAppKey;

    public String getChromePath() {
        return chromePath;
    }

    public void setChromePath(String chromePath) {
        this.chromePath = chromePath;
    }

    public String getBmobAppId() {
        return bmobAppId;
    }

    public void setBmobAppId(String bmobAppId) {
        this.bmobAppId = bmobAppId;
    }

    public String getBmobAppKey() {
        return bmobAppKey;
    }

    public void setBmobAppKey(String bmobAppKey) {
        this.bmobAppKey = bmobAppKey;
    }
}
