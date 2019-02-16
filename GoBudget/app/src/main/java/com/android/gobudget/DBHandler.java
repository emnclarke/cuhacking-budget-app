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
    public static final String COLUMN_ID = "PurchaseID";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_AMOUNT = "Date";
    public static final String COLUMN_NAME = "Date";
    public static final String COLUMN_CATEGORY = "Date";
    public static final String COLUMN_DESCRIPTION = "Date";


    //initialize the database
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + COLUMN_ID +
                "INTEGER PRIMARYKEY," + COLUMN_NAME + "TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public String loadHandler() {
        return "";
    }

    public void addHandler(Purchase purchase) {}

    public Purchase findHandler(String purchaseName) {
        return null;
    }

    public boolean deleteHandler(int ID) {
        return false;
    }

    public boolean updateHandler(int ID, String name) {
        return false;
    }

}
