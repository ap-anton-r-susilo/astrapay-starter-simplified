package com.astrapay.starter.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarSelector {
    public static final int FIRST_DAY_OF_THE_MONTH = 1;
    private static final int ADDITIONAL_MONTH_INDEX = 1;
    public static final int ADDITIONAL_DAYS = 1;

    @Getter
    @AllArgsConstructor
    public enum Month {
        JANUARY(Calendar.JANUARY),
        FEBRUARY(Calendar.FEBRUARY),
        MARCH(Calendar.MARCH),
        APRIL(Calendar.APRIL),
        MAY(Calendar.MAY),
        JUNE(Calendar.JUNE),
        JULY(Calendar.JULY),
        AUGUST(Calendar.AUGUST),
        SEPTEMBER(Calendar.SEPTEMBER),
        OCTOBER(Calendar.OCTOBER),
        NOVEMBER(Calendar.NOVEMBER),
        DECEMBER(Calendar.DECEMBER);

        Integer monthIndex;
    }

    private Map<Month, Integer> monthMap = new HashMap<>();

    public CalendarSelector(){
        monthMap.put(Month.JANUARY, Month.JANUARY.getMonthIndex());
        monthMap.put(Month.FEBRUARY, Month.FEBRUARY.getMonthIndex());
        monthMap.put(Month.MARCH, Month.MARCH.getMonthIndex());
        monthMap.put(Month.APRIL, Month.APRIL.getMonthIndex());
        monthMap.put(Month.MAY, Month.MAY.getMonthIndex());
        monthMap.put(Month.JUNE, Month.JUNE.getMonthIndex());
        monthMap.put(Month.JULY, Month.JULY.getMonthIndex());
        monthMap.put(Month.AUGUST, Month.AUGUST.getMonthIndex());
        monthMap.put(Month.SEPTEMBER, Month.SEPTEMBER.getMonthIndex());
        monthMap.put(Month.OCTOBER, Month.OCTOBER.getMonthIndex());
        monthMap.put(Month.NOVEMBER, Month.NOVEMBER.getMonthIndex());
        monthMap.put(Month.DECEMBER, Month.DECEMBER.getMonthIndex());
    }

    public Integer getMonth(Month month){
        return this.monthMap.get(month) + ADDITIONAL_MONTH_INDEX;
    }

}
