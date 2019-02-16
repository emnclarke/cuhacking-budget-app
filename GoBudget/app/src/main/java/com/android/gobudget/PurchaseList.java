package com.android.gobudget;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class PurchaseList {
    private String category;
    private ArrayList<Purchase> purchases;

    public PurchaseList(String category) {
        this.category = category;
        purchases = new ArrayList<>();
    }

    public boolean addPurchase(Purchase purchase) {
        if (purchase.getCategory() != this.category) {
            return false;
        }
        purchases.add(purchase);
        return true;
    }

    /**
     * @param week - The first monday of the selected week.
     * @return - The total purchases amount for the week
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public double weeklyTotal(LocalDate week) {
        if (week.getDayOfWeek() == DayOfWeek.MONDAY) {
            LocalDate start = week;
            LocalDate end = week.plusDays(7);
            return (totals(start, end));
        } else {
            return -1;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double biweeklyTotal(LocalDate week) {
        if (week.getDayOfWeek() == DayOfWeek.MONDAY) {
            LocalDate start = week;
            LocalDate end = week.plusDays(14);
            return (totals(start, end));
        } else {
            return -1;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double monthlyTotal(LocalDate month) {
        LocalDate start = month.withDayOfMonth(1);
        LocalDate end = month.withDayOfMonth(month.lengthOfMonth());
        return (totals(start, end));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double yearlyTotal(LocalDate year) {
        LocalDate start = year.withDayOfYear(1);
        LocalDate end = year.withDayOfYear(year.lengthOfYear());
        return (totals(start, end));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public double totals(LocalDate start, LocalDate end) {
        double total = 0;
        for (Purchase purchase : purchases) {
            if ((purchase.getLocalDate()).isAfter(start) && purchase.getLocalDate().isBefore(end)) {
                total += purchase.getAmount();
            }
        }
        return total;
    }
}
