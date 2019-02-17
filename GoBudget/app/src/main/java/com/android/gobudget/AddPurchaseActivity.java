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
import android.widget.TextView;

import com.example.gobudget.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddPurchaseActivity extends AppCompatActivity {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    TextView dblist;
    TextInputEditText pcname;
    TextInputEditText pcamount;
    TextInputEditText pccategory;
    TextInputEditText pcdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dblist = findViewById(R.id.lst);
        pcname = findViewById(R.id.pcname);
        pcamount = findViewById(R.id.pcamount);
        pccategory = findViewById(R.id.pccategory);
        pcdescription = findViewById(R.id.pcdescription);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadPurchases(View view) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        String db = "";
        ArrayList<Purchase> purchases = dbHandler.loadHandler();

        for(Purchase purchase : purchases) {
            long id = purchase.getId();
            LocalDateTime date = purchase.getDate();
            double amount = purchase.getAmount();
            String name = purchase.getName();
            String category = purchase.getCategory();
            String description = purchase.getDescription();
            db += id + " " + date.format(dateTimeFormatter) + " " + amount + " " + name + " " + category  + " " + description +
                    System.getProperty("line.separator");
        }

        dblist.setText(db);
        pcname.setText("");
        pcamount.setText("");
        pccategory.setText("");
        pcdescription.setText("");
    }

    public void addPurchase(View view) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        double amount = Double.parseDouble(pcamount.getText().toString());
        String name = pcname.getText().toString();
        String category = pccategory.getText().toString();
        String description = pcname.getText().toString();
        Purchase purchase = new Purchase(amount, name, category, description);
        dbHandler.addHandler(purchase);
        pcname.setText("");
        pcamount.setText("");
        pccategory.setText("");
        pcdescription.setText("");
    }
}
