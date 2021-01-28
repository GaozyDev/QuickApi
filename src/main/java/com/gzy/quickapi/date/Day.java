package com.gzy.quickapi.date;

public class Day {

    private int year;
    private int month;
    private int day;
    private int days;
    private String name;

    public Day(int year, int month, int day, int days, String name) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.days = days;
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
