package com.gzy.quickapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class DateController {

    private final List<Day> holidayList = new ArrayList<>();

    private final List<Day> workDayList = new ArrayList<>();

    public DateController() {
        holidayList.add(new Day(2021, 1, 1, 3, "元旦"));
        holidayList.add(new Day(2021, 2, 11, 7, "春节"));
        holidayList.add(new Day(2021, 4, 3, 3, "清明节"));
        holidayList.add(new Day(2021, 5, 1, 5, "劳动节"));
        holidayList.add(new Day(2021, 6, 12, 3, "端午节"));
        holidayList.add(new Day(2021, 9, 19, 3, "中秋节"));
        holidayList.add(new Day(2021, 10, 1, 7, "国庆节"));

        workDayList.add(new Day(2021, 2, 7, 1, "调休日"));
        workDayList.add(new Day(2021, 2, 20, 1, "调休日"));
        workDayList.add(new Day(2021, 4, 25, 1, "调休日"));
        workDayList.add(new Day(2021, 5, 8, 1, "调休日"));
        workDayList.add(new Day(2021, 9, 18, 1, "调休日"));
        workDayList.add(new Day(2021, 9, 26, 1, "调休日"));
        workDayList.add(new Day(2021, 5, 10, 9, "调休日"));
    }

    @GetMapping("/isWorkingDay")
    public String isWorkingDay(@RequestParam(name = "year") int year,
                                @RequestParam(name = "month") int month,
                                @RequestParam(name = "day") int day) {
        // 法定节假日
        for (Day d : holidayList) {
            if (d.getYear() == year
                    && d.getMonth() == month
                    && (d.getDay() <= day && day <= d.getDay() + d.getDays() - 1)) {
                return "false";
            }
        }

        // 法定节假日调休
        for (Day d : workDayList) {
            if (d.getYear() == year
                    && d.getMonth() == month
                    && d.getDay() == day) {
                return "true";
            }
        }

        // 是否是周一到周五
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            return "true";
        } else {
            return "false";
        }
    }
}
