package com.gzy.quickapi.ps5;

import com.gzy.quickapi.ps5.bmob.QueryBmobResults;
import com.gzy.quickapi.ps5.bmob.PriceBmob;
import com.gzy.quickapi.ps5.data.PriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class PS5Controller {

    @Autowired
    private PS5Service ps5Service;

    // 光驱版
    private PriceData opticalDrivePriceData;

    // 数字版
    private PriceData priceData;

    @GetMapping("/ps5")
    public ModelAndView ps5Price(@RequestParam(name = "opticalDrive", defaultValue = "true") boolean opticalDrive,
                                 Map<String, Object> map) {
        Date currentDate = new Date();
        int updateTime = 1000 * 60 * 60;
        map.put("title", opticalDrive ? "PS5光驱版" : "PS5数字版");
        if (opticalDrive) {
            if (opticalDrivePriceData == null
                    || currentDate.getTime() - opticalDrivePriceData.getUpdateDate().getTime() > updateTime) {
                opticalDrivePriceData = ps5Service.getPS5ProductData(0);
            }
            map.put("resultData", opticalDrivePriceData);
        } else {
            if (priceData == null || currentDate.getTime() - priceData.getUpdateDate().getTime() > updateTime) {
                priceData = ps5Service.getPS5ProductData(1);
            }
            map.put("resultData", priceData);
        }

        QueryBmobResults queryBmobResults = ps5Service.getPS5HistoryPrice(opticalDrive ? 0 : 1);
        List<Double> priceList = new ArrayList<>();
        List<Double> minPriceList = new ArrayList<>();
        List<String> labelList = new ArrayList<>();
        for (PriceBmob priceBmob : queryBmobResults.getResults()) {
            priceList.add(priceBmob.getAveragePrice());
            minPriceList.add(priceBmob.getMinAveragePrice());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date = sdf.format(priceBmob.getCreateDate());
            labelList.add("\"" + date + "\"");
        }
        map.put("priceList", priceList);
        map.put("minPriceList", minPriceList);
        map.put("labelList", labelList);

        return new ModelAndView("ps5/index", map);
    }
}
