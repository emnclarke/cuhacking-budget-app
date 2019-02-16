package com.android.gobudget;

import com.android.gobudget.Purchase;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "transactionsDB.db";
    public static final String TABLE_NAME = "Purchases";
    public static final String COLUMN_ID = "StudentID";
    public static final String COLUMN_NAME = "StudentName";

    //initialize the database
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public String loadHandler() {}

    public void addHandler(Purchase purchase) {}

    public Purchase findHandler(String studentname) {}

    public boolean deleteHandler(int ID) {}

    public boolean updateHandler(int ID, String name) {}

}
