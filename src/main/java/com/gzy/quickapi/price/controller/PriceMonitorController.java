package com.gzy.quickapi.price.controller;

import com.gzy.quickapi.price.dto.HistoryPriceInfo;
import com.gzy.quickapi.price.enums.ProductIdEnum;
import com.gzy.quickapi.price.service.PriceMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping
public class PriceMonitorController {

    @Autowired
    private PriceMonitorService priceMonitorService;

    @GetMapping("/")
    public ModelAndView ps5Price(Map<String, Object> map) {
        if (PriceMonitorService.opticalAveragePriceInfo == null) {
            PriceMonitorService.opticalAveragePriceInfo = priceMonitorService.getProductPriceInfos(ProductIdEnum.PS5_OPTICAL_DRIVE);
        }
        if (PriceMonitorService.digitalAveragePriceInfo == null) {
            PriceMonitorService.digitalAveragePriceInfo = priceMonitorService.getProductPriceInfos(ProductIdEnum.PS5_DIGITAL_EDITION);
        }

        map.put("opticalResultData", PriceMonitorService.opticalAveragePriceInfo);
        map.put("digitalResultData", PriceMonitorService.digitalAveragePriceInfo);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(PriceMonitorService.opticalAveragePriceInfo.getUpdateDate());
        map.put("updateTime", date);

        HistoryPriceInfo opticalHistoryPrice = priceMonitorService.getHistoryPriceInfo(ProductIdEnum.PS5_OPTICAL_DRIVE);
        HistoryPriceInfo digitalHistoryPrice = priceMonitorService.getHistoryPriceInfo(ProductIdEnum.PS5_DIGITAL_EDITION);
        map.put("opticalChartData", opticalHistoryPrice);
        map.put("digitalChartData", digitalHistoryPrice);

        return new ModelAndView("price/index", map);
    }
}
