package com.android.gobudget;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.gobudget.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddCategory extends AppCompatActivity {
    TextInputEditText category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_addcat);

        category = findViewById(R.id.addcategory);
    }

    public ArrayList<String> getCategories(View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<String> categories = dbHandler.getCategories();
        return categories;
    }

    public void addCategory(View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.addCategory(category.getText().toString());
        category.setText("");
    }
}
