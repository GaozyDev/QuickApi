package com.gzy.quickapi.ps5.task;

import com.gzy.quickapi.ps5.enums.PS5TypeEnum;
import com.gzy.quickapi.ps5.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleTask {

    @Autowired
    PriceService priceService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class.getName());

    @Scheduled(cron = "0 0 0/2 * * ?")
    private void configureTasks() {
        logger.info("定时爬虫：" + new Date());
        PriceService.opticalDriveProductPriceInfos = priceService.getPS5ProductData(PS5TypeEnum.OPTICAL_DRIVE);
        PriceService.digitalEditionProductPriceInfos = priceService.getPS5ProductData(PS5TypeEnum.DIGITAL_EDITION);
    }
}