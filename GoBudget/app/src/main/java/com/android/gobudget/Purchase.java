package com.android.gobudget;

import android.os.Build;
import android.support.annotation.RequiresApi;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.time.DayOfWeek;


public class Purchase {
    private float amount;
    private LocalDateTime date;
    private String name;
    private String category;
    private String description;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Purchase(float amount, String name, String category, String description) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean thisWeek() {
        LocalDate currDate = LocalDate.now();
        LocalDate previousMonday = currDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

        return false;
    }

}