package com.gzy.quickapi.ps5;

import com.gzy.quickapi.ps5.bmob.PriceBmob;
import com.gzy.quickapi.ps5.bmob.QueryBmobResults;
import com.gzy.quickapi.ps5.data.PriceData;
import com.gzy.quickapi.ps5.enums.PS5TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping
public class PS5Controller {

    @Autowired
    private PS5Service ps5Service;

    @GetMapping("/ps5")
    public ModelAndView ps5Price(@RequestParam(name = "opticalDrive", defaultValue = "true") boolean opticalDrive,
                                 Map<String, Object> map) {
        map.put("title", opticalDrive ? PS5TypeEnum.OPTICAL_DRIVE.getTypeName() : PS5TypeEnum.DIGITAL_EDITION.getTypeName());
        PriceData priceData;
        if (opticalDrive) {
            if (PS5Service.opticalDrivePriceData == null) {
                PS5Service.opticalDrivePriceData = ps5Service.getPS5ProductData(PS5TypeEnum.OPTICAL_DRIVE);
            }
            priceData = PS5Service.opticalDrivePriceData;
        } else {
            if (PS5Service.digitalEditionPriceData == null) {
                PS5Service.digitalEditionPriceData = ps5Service.getPS5ProductData(PS5TypeEnum.DIGITAL_EDITION);
            }
            priceData = PS5Service.digitalEditionPriceData;
        }

        map.put("resultData", priceData);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(priceData.getUpdateDate());
        map.put("updateTime", calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE));

        QueryBmobResults queryBmobResults = ps5Service.getPS5HistoryPrice(opticalDrive ? PS5TypeEnum.OPTICAL_DRIVE : PS5TypeEnum.DIGITAL_EDITION);
        List<PriceBmob> priceBmobList = queryBmobResults.getResults();

        setDataToMap(map, priceBmobList);

        return new ModelAndView("ps5/index", map);
    }

    private void setDataToMap(Map<String, Object> map, List<PriceBmob> priceBmobList) {
        List<Double> averagePriceList = new ArrayList<>();
        List<Double> minAveragePriceList = new ArrayList<>();
        List<Double> minPriceList = new ArrayList<>();
        List<String> labelList = new ArrayList<>();

        Date currentDate = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(currentDate);
        int historyDataCount = 0;
        for (int i = priceBmobList.size() - 1; i >= 0; i--) {
            Date date = priceBmobList.get(i).getCreateDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (!(todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                    todayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    todayCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
            ) {
                historyDataCount = i;
                break;
            }
        }
        List<PriceBmob> lastData = priceBmobList.subList(0, historyDataCount);
        List<PriceBmob> todayData = priceBmobList.subList(historyDataCount, priceBmobList.size() - 1);

        int dayStep = lastData.size() / 10;

        double averagePrice = 0;
        double minAveragePrice = 0;
        double minPrice = 0;
        Date date;
        for (int i = 0; i < lastData.size(); i++) {
            PriceBmob priceBmob = lastData.get(i);
            averagePrice += priceBmob.getAveragePrice();
            minAveragePrice += priceBmob.getMinAveragePrice();
            minPrice += priceBmob.getMinPrice();
            date = lastData.get(i).getCreateDate();

            if ((i + 1) % dayStep == 0) {
                averagePriceList.add(averagePrice / dayStep);
                minAveragePriceList.add(minAveragePrice / dayStep);
                minPriceList.add(minPrice / dayStep);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                labelList.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

                averagePrice = 0;
                minAveragePrice = 0;
                minPrice = 0;
            }
        }

        PriceBmob newData = todayData.get(todayData.size() - 1);
        averagePriceList.add(newData.getAveragePrice());
        minAveragePriceList.add(newData.getMinAveragePrice());
        minPriceList.add(newData.getMinPrice());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newData.getCreateDate());
        labelList.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        map.put("averagePriceList", averagePriceList);
        map.put("minAveragePriceList", minAveragePriceList);
        map.put("minPriceList", minPriceList);
        map.put("labelList", labelList);
    }
}
