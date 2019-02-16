package com.android.gobudget;

import android.os.Build;
import android.support.annotation.RequiresApi;

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
    public float weeklyTotal(LocalDate week) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double totals(LocalDate start, LocalDate end) {
        float total = 0;
        for (Purchase purchase : purchases) {
            if ((purchase.getLocalDate()).isAfter(start) && purchase.getLocalDate().isBefore(end)) {
                total += purchase.getAmount();
            }
        }
        return total;
    }
}
