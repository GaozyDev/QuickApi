package com.gzy.quickapi.price.controller;

import com.gzy.quickapi.price.dataobject.ProductPrice;
import com.gzy.quickapi.price.dto.AveragePriceInfo;
import com.gzy.quickapi.price.enums.ProductIdEnum;
import com.gzy.quickapi.price.service.PriceMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping
public class PriceMonitorController {

    @Autowired
    private PriceMonitorService priceMonitorService;

    @GetMapping("/ps5")
    public ModelAndView ps5Price(@RequestParam(name = "opticalDrive", defaultValue = "true") boolean opticalDrive,
                                 Map<String, Object> map) {
        map.put("title", opticalDrive ? ProductIdEnum.PS5_OPTICAL_DRIVE.getProductName() : ProductIdEnum.PS5_DIGITAL_EDITION.getProductName());
        AveragePriceInfo priceDataInfos;
        if (opticalDrive) {
            if (PriceMonitorService.opticalDriveAveragePriceInfo == null) {
                PriceMonitorService.opticalDriveAveragePriceInfo = priceMonitorService.getProductPriceInfos(ProductIdEnum.PS5_OPTICAL_DRIVE);
            }
            priceDataInfos = PriceMonitorService.opticalDriveAveragePriceInfo;
        } else {
            if (PriceMonitorService.digitalEditionAveragePriceInfo == null) {
                PriceMonitorService.digitalEditionAveragePriceInfo = priceMonitorService.getProductPriceInfos(ProductIdEnum.PS5_DIGITAL_EDITION);
            }
            priceDataInfos = PriceMonitorService.digitalEditionAveragePriceInfo;
        }

        map.put("resultData", priceDataInfos);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(priceDataInfos.getUpdateDate());
        map.put("updateTime", date);

        List<ProductPrice> productPriceList = priceMonitorService.getProductPriceList(opticalDrive ? ProductIdEnum.PS5_OPTICAL_DRIVE : ProductIdEnum.PS5_DIGITAL_EDITION);
        setChartData(map, productPriceList);

        return new ModelAndView("price/index", map);
    }

    private void setChartData(Map<String, Object> map, List<ProductPrice> productPriceList) {
        List<Double> averagePriceList = new ArrayList<>();
        List<Double> minAveragePriceList = new ArrayList<>();
        List<Double> minPriceList = new ArrayList<>();
        List<String> labelList = new ArrayList<>();

        Date currentDate = new Date();
        Calendar todayCalendar = Calendar.getInstance(Locale.CHINA);
        todayCalendar.setTime(currentDate);
        int historyDataIndex = 0;
        for (int i = productPriceList.size() - 1; i >= 0; i--) {
            Date date = productPriceList.get(i).getCreateTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (!(todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                    todayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    todayCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
            ) {
                historyDataIndex = i;
                break;
            }
        }
        List<ProductPrice> lastData = productPriceList.subList(0, historyDataIndex + 1);

        int dayStep = lastData.size() / 10 + 1;

        double averagePrice = 0;
        double minAveragePrice = 0;
        double minPrice = 0;
        Date date;
        for (int i = 0; i < lastData.size(); i++) {
            ProductPrice productPrice = lastData.get(i);
            averagePrice += productPrice.getAveragePrice();
            minAveragePrice += productPrice.getMinAveragePrice();
            minPrice += productPrice.getMinPrice();
            date = lastData.get(i).getCreateTime();

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

        if (!productPriceList.isEmpty()) {
            ProductPrice newData = productPriceList.get(productPriceList.size() - 1);
            averagePriceList.add(newData.getAveragePrice());
            minAveragePriceList.add(newData.getMinAveragePrice());
            minPriceList.add(newData.getMinPrice());
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setTime(newData.getCreateTime());
            labelList.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        }

        map.put("averagePriceList", averagePriceList);
        map.put("minAveragePriceList", minAveragePriceList);
        map.put("minPriceList", minPriceList);
        map.put("labelList", labelList);
    }
}
