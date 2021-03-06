package com.android.gobudget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.gobudget.R;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class Projection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView mStartDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateStartListner;
    private String startDate;

    private TextView mEndDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateEndListner;
    private String endDate;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    TextView dblist;

    LocalDateTime start;
    LocalDateTime end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        graphAndButtons();

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
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, History.class);
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

    public void graphAndButtons() {
        mStartDisplayDate = (TextView) findViewById(R.id.start_date);
        mEndDisplayDate = (TextView) findViewById(R.id.end_date);

        mStartDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Projection.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateStartListner,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 86400000);
                dialog.show();
            }
        });

        mDateStartListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = "";
                if (month < 10 && dayOfMonth < 10) {
                    date = year + "-0" + month + "-" + "0" + dayOfMonth;
                } else if (month >= 10 && dayOfMonth < 10) {
                    date = year + "-" + month + "-" + "0" + dayOfMonth;
                } else if (month < 10 && dayOfMonth >= 10) {
                    date = year + "-0" + month + "-" + dayOfMonth;
                } else {
                    date = year + "-" + month + "-" + dayOfMonth;
                }
                startDate = date;
                start = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 1);
                mStartDisplayDate.setText(date);
            }
        };

        mEndDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Projection.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateEndListner,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        mDateEndListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = "";
                if (month < 10 && dayOfMonth < 10) {
                    date = year + "-0" + month + "-" + "0" + dayOfMonth;
                } else if (month >= 10 && dayOfMonth < 10) {
                    date = year + "-" + month + "-" + "0" + dayOfMonth;
                } else if (month < 10 && dayOfMonth >= 10) {
                    date = year + "-0" + month + "-" + dayOfMonth;
                } else {
                    date = year + "-" + month + "-" + dayOfMonth;
                }
                endDate = date;
                end = LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59);
                //throw new RuntimeException("Date: " + endDate);
                mEndDisplayDate.setText(date);
                startGraph();

            }
        };


    }
    public void startGraph(){


        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        String db = "";
        ArrayList<Purchase> purchases = dbHandler.loadHandler();


        ArrayList<EntryPoint> xyvalue = new ArrayList<>();
        PurchaseList purchaseList = new PurchaseList("Temp");

        int total = 0;
        for (Purchase purchase : purchases) {
            LocalDateTime date = purchase.getDate();

            //Compare to start and end date
            //if greater or equal to start date, count the amount of that day
            //increment the day
            //count that day
            //stop at end day
            //date.compareTo(start) >= 0 && date.compareTo(end) < 1
           total += (int)purchase.getAmount();
           xyvalue.add(new EntryPoint(date.getDayOfMonth(), total));


        }

        List<Entry> entries = new ArrayList<>();


        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setDragEnabled(true);


        for (EntryPoint i : xyvalue) {
            entries.add(new Entry(i.getX(), i.getY()));
        }

        LineDataSet set1 = new LineDataSet(entries, "Date");
        set1.setHighlightEnabled(true);
        set1.setDrawHighlightIndicators(true);

        //set1.setFillAlpha(110);
        //set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setFillColor(Color.BLUE);
        set1.setValueFormatter(new MyValueFormatter());
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData lineData = new LineData(dataSets);


        YAxis left = chart.getAxisLeft();
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true); // draw a zero line
        left.setAxisMinimum(0);
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);


        chart.setTouchEnabled(false);
        chart.setNoDataText("Empty");
        chart.setData(lineData);
        chart.invalidate();

    }

}
