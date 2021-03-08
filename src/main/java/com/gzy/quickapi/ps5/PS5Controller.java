package com.gzy.quickapi.ps5;

import com.gzy.quickapi.ps5.bean.PriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
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
        ps5Service.getPS5Price();
//        Date currentDate = new Date();
//        int updateTime = 1000 * 60 * 10;
//        if (opticalDrive) {
//            if (opticalDrivePriceData == null
//                    || currentDate.getTime() - opticalDrivePriceData.getUpdateDate().getTime() > updateTime) {
//                opticalDrivePriceData = ps5Service.getPS5OpticalDriveProductData();
//            }
//            map.put("resultData", opticalDrivePriceData);
//        } else {
//            if (priceData == null || currentDate.getTime() - priceData.getUpdateDate().getTime() > updateTime) {
//                priceData = ps5Service.getPS5ProductData();
//            }
//            map.put("resultData", priceData);
//        }
        return new ModelAndView("ps5/index", map);
    }
}
