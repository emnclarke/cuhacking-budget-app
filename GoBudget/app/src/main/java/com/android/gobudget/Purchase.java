package com.android.gobudget;

import android.os.Build;
import android.support.annotation.RequiresApi;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.time.DayOfWeek;


public class Purchase {
    private double amount;
    private LocalDateTime date;
    private String name;
    private String category;
    private String description;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Purchase(double amount, String name, String category, String description) {
        date = LocalDateTime.now();
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void changeCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public double getAmount() {
        return this.amount;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getLocalDate() {
        return date.toLocalDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean thisWeek() {
        LocalDate currDate = date.toLocalDate();
        LocalDate previousMonday = currDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        return (currDate.isAfter(previousMonday));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean thisBiWeek() {
        LocalDate currDate = date.toLocalDate();
        LocalDate previousBiMonday = currDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        previousBiMonday = previousBiMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        return (currDate.isAfter(previousBiMonday));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean thisMonth() {
        LocalDate currDate = date.toLocalDate();
        LocalDate firstDayOfMonth = currDate.withDayOfMonth(1);
        return (currDate.isAfter(firstDayOfMonth));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean thisYear() {
        LocalDate currDate = date.toLocalDate();
        LocalDate firstDayOfYear = currDate.withDayOfYear(1);
        return (currDate.isAfter(firstDayOfYear));
    }

}