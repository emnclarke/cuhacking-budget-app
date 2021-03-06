package com.android.gobudget;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.gobudget.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class History extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        String db = "";
        ArrayList<Purchase> purchases = dbHandler.loadHandler();

        TableLayout layout = (TableLayout) findViewById(R.id.table);

        for(Purchase purchase : purchases) {
            TableRow row = new TableRow(this);

            long id = purchase.getId();

            LocalDateTime date = purchase.getDate();
            String amount = String.format("%1$17s",purchase.getAmount());
            String name =  String.format("%1$17s", purchase.getName());
            String category = String.format("%1$17s", purchase.getCategory());
            String description = purchase.getDescription();
            db += date.format(dateTimeFormatter) + " " +  category + " " + amount + " " +
                    System.getProperty("line.separator");
        }

        TextView textView = new TextView(this);
        textView.setText(db);
        textView.setMinimumWidth(10);
        //optional: add your buttons to any layout if you want to see them in your screen
        layout.addView(textView);


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
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }else if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
