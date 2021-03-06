package com.android.gobudget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.gobudget.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    TextView spendMonth;
    TextView spendWeek;
    TextView spendDay;
    TextView purchase1;
    TextView purchase2;
    TextView purchase3;
    FloatingActionsMenu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        menu = findViewById(R.id.multiple_actions);

        purchase1 = findViewById(R.id.purchase1);
        purchase2 = findViewById(R.id.purchase2);
        purchase3 = findViewById(R.id.purchase3);


        spendMonth = findViewById(R.id.spendMonth);
        spendWeek = findViewById(R.id.spendWeek);
        spendDay = findViewById(R.id.spendDay);


        spendMonth.setText(updateTotal("month"));
        spendWeek.setText(updateTotal("week"));
        spendDay.setText(updateTotal("day"));

        FloatingActionsMenu menu = findViewById(R.id.multiple_actions);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse();
                changeViewAdd();
            }
        });

        findViewById(R.id.add_cat_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse();
                changeViewAddCat();
            }
        });

        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse();
                spendMonth.setText(updateTotal("month"));
                spendWeek.setText(updateTotal("week"));
                spendDay.setText(updateTotal("day"));
            }
        });
    }

    private void collapse() {
        menu.collapse();
    }

    public String[] getMostRecent() {
        DBHandler db = new DBHandler(this, null, null, 1);
        ArrayList<Purchase> purchases = db.loadThreeHandler();
        String results[] = new String[3];
        int i = 0;
        for (Purchase purchase : purchases) {
            results[i++] = purchase.getDate().format(dateTimeFormatter) + " " + purchase.getName()
                    + " " + purchase.getAmount();
        }
        return results;
    }

    public String updateTotal(String period) {
        String[] recents = getMostRecent();
        purchase1.setText(recents[0]);
        purchase2.setText(recents[1]);
        purchase3.setText(recents[2]);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<Purchase> purchases = dbHandler.loadHandler();
        LocalDateTime beginning;
        double amount = 0;
        switch (period) {
            case "day":
                beginning = LocalDateTime.now().withHour(0).withSecond(0);
                break;
            case "week":
                beginning = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).withHour(0).withSecond(0);
                break;
            case "month":
                beginning = LocalDateTime.now().withDayOfMonth(1).withHour(0).withSecond(0);
                break;
            default:
                beginning = null;
        }
        if (!purchases.isEmpty()) {
            for (Purchase purchase : purchases) {
                if (purchase.getDate().isAfter(beginning)) {
                    amount += purchase.getAmount();
                }
            }
            return "$" + Double.toString(amount);
        } else {
            return "$0.00";
        }
    }

    public void changeViewAdd() {
        Intent intent = new Intent(this, AddPurchaseActivity.class);
        startActivity(intent);
    }

    public void changeViewAddCat() {
        Intent intent = new Intent(this, AddCategory.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset_money) {
            DBHandler db = new DBHandler(this, null, null, 1);
            db.drop("Purchases");
            return true;
        }
        if (id == R.id.action_reset_cat) {
            DBHandler db = new DBHandler(this, null, null, 1);
            db.drop("Categories");
            return true;
        }
        if (id == R.id.action_load_default) {
            ArrayList<Purchase> Testpurchases = new ArrayList<>();
            Testpurchases.add(new Purchase("2019-02-03 12:22:22", 12, "Coffee", "Food", "Tim's"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-03 22:22:22", 102, "Shoes", "Clothes", "Kijiji"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-04 08:21:12", 1000, "Coffee", "Food", "Tim's"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-05 12:22:22", 121, "Waffles", "Food", "WaffleHouse"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-15 22:22:22", 12, "Coffee", "Food", "Tim's"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-05 02:22:22", 122, "Coffee", "Recreation", "Ree"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-17 18:22:22", 1, "Coffee", "Food", "Tim's"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-01 12:22:22", 12, "Coffee", "Food", "Tim's"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-14 12:22:22", 122, "Beer", "Food", "Bar"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-15 22:22:22", 12, "Coffee", "Food", "Food Truck"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-17 12:22:22", 152, "Waffle", "Food", "McDonald's"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Testpurchases.add(new Purchase("2019-02-17 12:22:22", 12, "Coffee", "Food", "Tim's"));


            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            dbHandler.drop("Purchases");
            dbHandler.drop("Categories");
            for (Purchase purchase : Testpurchases) {
                dbHandler.addHandler(purchase);
                dbHandler.addCategory(purchase.getCategory());

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, Budget.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, Projection.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
