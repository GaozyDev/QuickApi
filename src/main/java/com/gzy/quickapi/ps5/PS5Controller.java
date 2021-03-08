package com.gzy.quickapi.ps5;

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
    private ResultData opticalDriveResultData;

    // 数字版
    private ResultData resultData;

    @GetMapping("/ps5")
    public ModelAndView ps5Price(@RequestParam(name = "opticalDrive", defaultValue = "true") boolean opticalDrive,
                                 Map<String, Object> map) {
        Date currentDate = new Date();
        int updateTime = 1000 * 60 * 10;
        if (opticalDrive) {
            if (opticalDriveResultData == null
                    || currentDate.getTime() - opticalDriveResultData.getUpdateDate().getTime() > updateTime) {
                opticalDriveResultData = ps5Service.getPS5OpticalDriveProductData();
            }
            map.put("resultData", opticalDriveResultData);
        } else {
            if (resultData == null || currentDate.getTime() - resultData.getUpdateDate().getTime() > updateTime) {

                resultData = ps5Service.getPS5ProductData();
            }
            map.put("resultData", resultData);
        }
        return new ModelAndView("ps5/index", map);
    }
}
