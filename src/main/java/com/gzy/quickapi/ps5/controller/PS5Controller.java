package com.gzy.quickapi.ps5.controller;

import com.gzy.quickapi.ps5.dto.ProductPriceInfos;
import com.gzy.quickapi.ps5.dataobject.ProductPrice;
import com.gzy.quickapi.ps5.enums.PS5TypeEnum;
import com.gzy.quickapi.ps5.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping
public class PS5Controller {

    @Autowired
    private PriceService priceService;

    @GetMapping("/ps5")
    public ModelAndView ps5Price(@RequestParam(name = "opticalDrive", defaultValue = "true") boolean opticalDrive,
                                 Map<String, Object> map) {
        map.put("title", opticalDrive ? PS5TypeEnum.OPTICAL_DRIVE.getTypeName() : PS5TypeEnum.DIGITAL_EDITION.getTypeName());
        ProductPriceInfos priceDataInfos;
        if (opticalDrive) {
            if (PriceService.opticalDriveProductPriceInfos == null) {
                PriceService.opticalDriveProductPriceInfos = priceService.getPS5ProductData(PS5TypeEnum.OPTICAL_DRIVE);
            }
            priceDataInfos = PriceService.opticalDriveProductPriceInfos;
        } else {
            if (PriceService.digitalEditionProductPriceInfos == null) {
                PriceService.digitalEditionProductPriceInfos = priceService.getPS5ProductData(PS5TypeEnum.DIGITAL_EDITION);
            }
            priceDataInfos = PriceService.digitalEditionProductPriceInfos;
        }

        map.put("resultData", priceDataInfos);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(priceDataInfos.getUpdateDate());
        map.put("updateTime", date);

        List<ProductPrice> productPriceData = priceService.getPS5HistoryPrice(opticalDrive ? PS5TypeEnum.OPTICAL_DRIVE : PS5TypeEnum.DIGITAL_EDITION);
        setChartData(map, productPriceData);

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

        BigDecimal averagePrice = new BigDecimal(BigInteger.ZERO);
        BigDecimal minAveragePrice = new BigDecimal(BigInteger.ZERO);
        BigDecimal minPrice = new BigDecimal(BigInteger.ZERO);
        Date date;
        for (int i = 0; i < lastData.size(); i++) {
            ProductPrice productPrice = lastData.get(i);
            averagePrice = averagePrice.add(productPrice.getAveragePrice());
            minAveragePrice = minAveragePrice.add(productPrice.getMinAveragePrice());
            minPrice = minPrice.add(productPrice.getMinPrice());
            date = lastData.get(i).getCreateTime();

            if ((i + 1) % dayStep == 0) {
                averagePriceList.add(averagePrice.doubleValue() / dayStep);
                minAveragePriceList.add(minAveragePrice.doubleValue() / dayStep);
                minPriceList.add(minPrice.doubleValue() / dayStep);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                labelList.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

                averagePrice = new BigDecimal(BigInteger.ZERO);
                minAveragePrice = new BigDecimal(BigInteger.ZERO);
                minPrice = new BigDecimal(BigInteger.ZERO);
            }
        }

        if (!productPriceList.isEmpty()) {
            ProductPrice newData = productPriceList.get(productPriceList.size() - 1);
            averagePriceList.add(newData.getAveragePrice().doubleValue());
            minAveragePriceList.add(newData.getMinAveragePrice().doubleValue());
            minPriceList.add(newData.getMinPrice().doubleValue());
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
