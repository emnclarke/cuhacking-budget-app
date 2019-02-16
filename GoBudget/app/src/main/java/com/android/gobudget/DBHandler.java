package com.android.gobudget;

import com.android.gobudget.Purchase;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "transactionsDB.db";
    public static final String TABLE_NAME = "Purchases";
    public static final String COLUMN_ID = "PurchaseID";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_AMOUNT = "Amount";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_DESCRIPTION = "Description";


    //initialize the database
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID
                + "INTEGER PRIMARYKEY," + COLUMN_DATE + "TEXT," + COLUMN_AMOUNT + "REAL,"
                + COLUMN_NAME + "TEXT, " + COLUMN_CATEGORY + "TEXT,"
                + COLUMN_DESCRIPTION + "TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public ArrayList<Purchase> loadHandler() {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        Purchase purchase = new Purchase();
        while (cursor.moveToNext()) {
            purchase.setId(Integer.parseInt(cursor.getString(0)));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
            purchase.setDate(LocalDateTime.parse(cursor.getString(1), formatter));
            purchase.setAmount(cursor.getDouble(2));
            purchase.setName(cursor.getString(3));
            purchase.setCategory(cursor.getString(4));
            purchase.setDescription(cursor.getString(5));
            purchases.add(purchase);
        }
        cursor.close();
        db.close();
        return purchases;
    }

    public void addHandler(Purchase purchase) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, purchase.getId());
        values.put(COLUMN_DATE, purchase.getDate().toString());
        values.put(COLUMN_AMOUNT, purchase.getAmount());
        values.put(COLUMN_NAME, purchase.getName());
        values.put(COLUMN_CATEGORY, purchase.getCategory());
        values.put(COLUMN_DESCRIPTION, purchase.getDescription());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Purchase findHandler(long id) {
        String query = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_ID + " = " + "'" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Purchase purchase = new Purchase();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            purchase.setId(Integer.parseInt(cursor.getString(0)));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
            purchase.setDate(LocalDateTime.parse(cursor.getString(1), formatter));
            purchase.setAmount(cursor.getDouble(2));
            purchase.setName(cursor.getString(3));
            purchase.setCategory(cursor.getString(4));
            purchase.setDescription(cursor.getString(5));
            cursor.close();
        } else {
            purchase = null;
        }
        db.close();
        return purchase;
    }

    public boolean deleteHandler(int id) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_ID + "= '" + String.valueOf(id) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Purchase purchase = new Purchase();
        if (cursor.moveToFirst()) {
            purchase.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + "=?",
                    new String[] {
                String.valueOf(purchase.getId())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(int id, LocalDateTime date, double amount, String name, String category, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, id);
        args.put(COLUMN_DATE, date.toString());
        args.put(COLUMN_AMOUNT, amount);
        args.put(COLUMN_NAME, name);
        args.put(COLUMN_CATEGORY, category);
        args.put(COLUMN_DESCRIPTION, description);
        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + id, null) > 0;
    }

}
