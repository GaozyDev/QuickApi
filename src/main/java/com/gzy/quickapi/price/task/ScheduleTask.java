package com.gzy.quickapi.price.task;

import com.gzy.quickapi.price.enums.ProductIdEnum;
import com.gzy.quickapi.price.service.PriceMonitorService;
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
    PriceMonitorService priceMonitorService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class.getName());

    @Scheduled(cron = "0 0 0/3 * * ?")
    private void configureTasks() {
        logger.info("定时爬虫：" + new Date());
        PriceMonitorService.opticalDriveAveragePriceInfo = priceMonitorService.getProductPriceInfos(ProductIdEnum.PS5_OPTICAL_DRIVE);
        PriceMonitorService.digitalEditionAveragePriceInfo = priceMonitorService.getProductPriceInfos(ProductIdEnum.PS5_DIGITAL_EDITION);
    }
}