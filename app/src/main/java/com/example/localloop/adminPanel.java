package com.example.localloop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class adminPanel extends AppCompatActivity {

    private TextInputEditText categoryEditText;
    private DatabaseHelper dbHelper; // Variable for our database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_admin);

        categoryEditText = findViewById(R.id.categoryToAdd);
        dbHelper = new DatabaseHelper(this); // Create an instance of our helper
    }


    public void onAddCategoryClick(View view) {
        String categoryName = categoryEditText.getText().toString().trim();


        if (categoryName.isEmpty()) {
            categoryEditText.setError("Category name is required");
            return;
        }


        // Get the database in write mode.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CAT_NAME, categoryName);


        long newRowId = db.insert(DatabaseHelper.TABLE_CATEGORIES, null, values);

        // Check if the insert was successful and provide feedback.
        if (newRowId != -1) {
            // Success
            Toast.makeText(this, "Category saved successfully!", Toast.LENGTH_SHORT).show();
            categoryEditText.setText(""); // Clear the input field
        } else {
            // Failure
            Toast.makeText(this, "Error saving category.", Toast.LENGTH_SHORT).show();
        }

        // Close Database
        db.close();
    }
}