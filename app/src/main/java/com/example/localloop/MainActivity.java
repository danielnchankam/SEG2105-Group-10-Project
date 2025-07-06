package com.example.localloop;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.localloop.model.Category;
import com.example.localloop.ui.category.AddEventActivity;
import com.example.localloop.ui.category.MyEventsActivity; // ✅ Import MyEventsActivity

public class MainActivity extends AppCompatActivity {

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "XPI76SZUqyCjVxgnUjm0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (dbHelper.getAllCategories().isEmpty()) {
            dbHelper.insertCategory(new Category("Test Category", "Temporary test category"));
        }

        EditText usernameField = findViewById(R.id.usernameField);
        EditText passwordField = findViewById(R.id.passwordField);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            String inputUsername = usernameField.getText().toString();
            String inputPassword = passwordField.getText().toString();

            if (inputUsername.equals(ADMIN_USERNAME) && inputPassword.equals(ADMIN_PASSWORD)) {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.putExtra("username", inputUsername);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        Button addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            startActivity(intent);
        });

        // ✅ New button to open MyEventsActivity
        Button viewMyEventsButton = findViewById(R.id.viewMyEventsButton);
        viewMyEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyEventsActivity.class);
            startActivity(intent);
        });
    }
}
