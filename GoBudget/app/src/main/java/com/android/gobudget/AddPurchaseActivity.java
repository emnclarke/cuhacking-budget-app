package com.android.gobudget;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.gobudget.R;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddPurchaseActivity extends AppCompatActivity {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    TextView dblist;
    TextInputEditText pcname;
    TextInputEditText pcamount;
    Spinner pccategory;
    TextInputEditText pcdescription;

    public ArrayList<Purchase> createExampleArray() {
        ArrayList<Purchase> Testpurchases = new ArrayList<>();
        Testpurchases.add(new Purchase("2019-2-23 12:22:22", 12, "Coffee", "Food", "Tim's"));
        Testpurchases.add(new Purchase("2019-2-23 22:22:22", 102, "Shoes", "Clothes", "Kijiji"));
        Testpurchases.add(new Purchase("2019-2-24 8:21:12", 1000, "Coffee", "Food", "Tim's"));
        Testpurchases.add(new Purchase("2019-2-25 12:22:22", 121, "Waffles", "Food", "WaffleHouse"));
        Testpurchases.add(new Purchase("2019-2-25 22:22:22", 12, "Coffee", "Food", "Tim's"));
        Testpurchases.add(new Purchase("2019-2-25 2:22:22", 122, "Coffee", "Recreation", "Ree"));
        Testpurchases.add(new Purchase("2019-2-26 12:22:22", 1, "Coffee", "Food", "Tim's"));
        Testpurchases.add(new Purchase("2019-2-27 12:22:22", 12, "Coffee", "Food", "Tim's"));
        Testpurchases.add(new Purchase("2019-2-28 12:22:22", 122, "Beer", "Food", "Bar"));
        Testpurchases.add(new Purchase("2019-2-28 22:22:22", 12, "Coffee", "Food", "Food Truck"));
        Testpurchases.add(new Purchase("2019-3-1 12:22:22", 152, "Waffle", "Food", "McDonald's"));
        Testpurchases.add(new Purchase("2019-3-2 12:22:22", 12, "Coffee", "Food", "Tim's"));

        return Testpurchases;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);

        pcname = findViewById(R.id.pcname);
        pcamount = findViewById(R.id.pcamount);
        pccategory = findViewById(R.id.spinner);
        pcdescription = findViewById(R.id.pcdescription);

        updateCategories();
    }

    public void updateCategories() {
        DBHandler db = new DBHandler(this, null, null, 1);
        ArrayList<String> categories = db.getCategories();
        Set uniqueCategories = new HashSet(categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList(uniqueCategories));
        pccategory.setAdapter(adapter);
    }

    public void addPurchase(View view) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        double amount = Double.parseDouble(pcamount.getText().toString());
        String name = pcname.getText().toString();
        String category = pccategory.getSelectedItem().toString();
        String description = pcname.getText().toString();
        Purchase purchase = new Purchase(amount, name, category, description);
        dbHandler.addHandler(purchase);
        pcname.setText("");
        pcamount.setText("");
        pccategory.setSelection(0);
        pcdescription.setText("");


    }

    public void addPurchase(Purchase purchase){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.addHandler(purchase);
    }
}
