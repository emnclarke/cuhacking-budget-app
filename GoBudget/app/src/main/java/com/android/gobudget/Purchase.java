package com.android.gobudget;

import android.os.Build;
import android.support.annotation.RequiresApi;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.time.DayOfWeek;
import java.util.Date;


public class Purchase {
    private long id;
    private double amount;
    private LocalDateTime date;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String category;
    private String description;

    public Purchase() {
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Purchase(String date, double amount, String name, String category, String description) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
        this.id = System.currentTimeMillis();
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Purchase(double amount, String name, String category, String description) {
        this(LocalDateTime.now().toString(), amount, name, category, description);
    }


    public String getCategory() {
        return this.category;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getName() {
        return this.name;
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