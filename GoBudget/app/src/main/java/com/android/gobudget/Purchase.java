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
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
    public int getDay(){
        return date.getDayOfMonth();
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

    public Purchase(String date, double amount, String name, String category, String description) {

        LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
        this.id = System.currentTimeMillis();
        this.date = dateTime;
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public Purchase(double amount, String name, String category, String description) {
        this(LocalDateTime.now().format(dateTimeFormatter), amount, name, category, description);
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

    public LocalDate getLocalDate() {
        return date.toLocalDate();
    }

    public boolean thisWeek() {
        LocalDate currDate = date.toLocalDate();
        LocalDate previousMonday = currDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        return (currDate.isAfter(previousMonday));
    }

    public boolean thisBiWeek() {
        LocalDate currDate = date.toLocalDate();
        LocalDate previousBiMonday = currDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        previousBiMonday = previousBiMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        return (currDate.isAfter(previousBiMonday));
    }

    public boolean thisMonth() {
        LocalDate currDate = date.toLocalDate();
        LocalDate firstDayOfMonth = currDate.withDayOfMonth(1);
        return (currDate.isAfter(firstDayOfMonth));
    }

    public boolean thisYear() {
        LocalDate currDate = date.toLocalDate();
        LocalDate firstDayOfYear = currDate.withDayOfYear(1);
        return (currDate.isAfter(firstDayOfYear));
    }

}