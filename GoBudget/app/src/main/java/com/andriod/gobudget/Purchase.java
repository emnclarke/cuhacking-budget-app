package com.andriod.gobudget;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Purchase {
    private float amount;
    private LocalDateTime date;
    private String name;
    private String category;
    private String note;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Purchase(float amount, String name, String category, String note) {
        date = LocalDateTime.now();
        this.amount = amount;
        this.name = name;
        this.category = category;

    }
}
