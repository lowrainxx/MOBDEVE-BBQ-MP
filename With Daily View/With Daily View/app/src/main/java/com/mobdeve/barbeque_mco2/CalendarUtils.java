package com.mobdeve.barbeque_mco2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {
    public static LocalDate selectedDate;

    public static String formattedDate(LocalDate date){
        // to change date format into month day year, default format of LocalDate is yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy");
        return date.format(formatter);
    }

    public static String formattedTime(LocalTime time){
        // to change time format into hours mins secs am/pm, default format of LocalTime is hh mm ss ns
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        return time.format(formatter);
    }

    public static String formattedShortTime(LocalTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static String monthYearFromDate(LocalDate date) {
        // to change date format into month year, default format of LocalDate is yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String monthDayFromDate(LocalDate date) {
        // to change date format into month day
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
    }

    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        // to get how many days are in a month
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);

        // returns an integer from 1-7 (1=SUN, 7=SAT)
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        for (int i=1; i<=42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                // if after the first/last of the month, add a blank cell
                daysInMonthArray.add(null);
            } else {
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i - dayOfWeek));
            }
        }

        return daysInMonthArray;
    }


    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while(current.isBefore(endDate)){
            days.add(current);
            current = current.plusDays(1);
        }

        return days;
    }

    private static LocalDate sundayDate (LocalDate current) {
        // sunday as start of week
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while(current.isAfter(oneWeekAgo)){
            if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }
}
