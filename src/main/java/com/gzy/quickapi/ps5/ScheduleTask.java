package com.gzy.quickapi.ps5;

import com.gzy.quickapi.ps5.enums.PS5TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleTask {

    @Autowired
    PS5Service ps5Service;

    @Scheduled(cron = "0 0 0-23 * * ?")
    private void configureTasks() {
        PS5Service.opticalDrivePriceData = ps5Service.getPS5ProductData(PS5TypeEnum.OPTICAL_DRIVE);
        PS5Service.digitalEditionPriceData = ps5Service.getPS5ProductData(PS5TypeEnum.DIGITAL_EDITION);
    }
}