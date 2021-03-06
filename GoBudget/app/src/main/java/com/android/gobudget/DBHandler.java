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
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
                + " TEXT PRIMARY KEY, " + COLUMN_DATE + " TEXT, " + COLUMN_AMOUNT + " REAL, "
                + COLUMN_NAME + " TEXT, " + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE = "CREATE TABLE Categories(ID INTEGER PRIMARY KEY, Category TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public ArrayList<Purchase> loadHandler() {
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        while (cursor.moveToNext()) {
            Purchase purchase = new Purchase();
            purchase.setId(cursor.getLong(0));
            purchase.setDate(LocalDateTime.parse(cursor.getString(1), dateTimeFormatter));
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

    public ArrayList<Purchase> loadThreeHandler() {
        String query = "Select * FROM " + TABLE_NAME + " ORDER BY Date DESC LIMIT 3";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        while (cursor.moveToNext()) {
            Purchase purchase = new Purchase();
            purchase.setId(cursor.getLong(0));
            purchase.setDate(LocalDateTime.parse(cursor.getString(1), dateTimeFormatter));
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

    public ArrayList<String> getCategories() {
        String query = "Select * FROM Categories";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> categories = new ArrayList<String>();
        while (cursor.moveToNext()) {
            categories.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return categories;
    }

    public void addCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", System.currentTimeMillis());
        values.put("Category", category);
        db.insert("Categories", null, values);
        db.close();
    }

    public boolean deleteCategory(String category) {
        boolean result = false;
        String query = "Select * FROM Categories WHERE category = '" + category + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String categoryTemp = cursor.getString(0);
            db.delete("Categories", "category" + "=?",
                    new String[] { categoryTemp });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public void addHandler(Purchase purchase) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, purchase.getId());
        values.put(COLUMN_DATE, purchase.getDate().format(dateTimeFormatter));
        values.put(COLUMN_AMOUNT, purchase.getAmount());
        values.put(COLUMN_NAME, purchase.getName());
        values.put(COLUMN_CATEGORY, purchase.getCategory());
        values.put(COLUMN_DESCRIPTION, purchase.getDescription());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Purchase findHandler(String column, String variable) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + "'" + variable + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Purchase purchase = new Purchase();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            purchase.setId(Integer.parseInt(cursor.getString(0)));
            purchase.setDate(LocalDateTime.parse(cursor.getString(1), dateTimeFormatter));
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
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + String.valueOf(id) + "'";
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

    public void drop(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + table + ";");

        switch(table) {
            case "Purchases":
                String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID
                        + " TEXT PRIMARY KEY, " + COLUMN_DATE + " TEXT, " + COLUMN_AMOUNT + " REAL, "
                        + COLUMN_NAME + " TEXT, " + COLUMN_CATEGORY + " TEXT, "
                        + COLUMN_DESCRIPTION + " TEXT)";
                db.execSQL(CREATE_TABLE);
                break;
            case "Categories":
                CREATE_TABLE = "CREATE TABLE Categories(ID INTEGER PRIMARY KEY, Category TEXT)";
                db.execSQL(CREATE_TABLE);
                break;
        }
    }

}
