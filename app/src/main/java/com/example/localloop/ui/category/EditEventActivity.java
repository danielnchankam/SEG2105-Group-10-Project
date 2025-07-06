package com.example.localloop.ui.category;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localloop.DatabaseHelper;
import com.example.localloop.R;
import com.example.localloop.model.Category;
import com.example.localloop.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    EditText editTextName, editTextDescription, editTextFee;
    Spinner spinnerCategory;
    Button buttonUpdate;

    DatabaseHelper dbHelper;
    List<Category> categoryList;
    Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextFee = findViewById(R.id.editTextFee);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonUpdate = findViewById(R.id.buttonSubmit); // reuse same ID as AddEventActivity

        dbHelper = new DatabaseHelper(this);

        // Get eventId from Intent
        int eventId = getIntent().getIntExtra("eventId", -1);
        if (eventId == -1) {
            Toast.makeText(this, "Invalid event", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load event from DB
        currentEvent = getEventById(eventId);
        if (currentEvent == null) {
            Toast.makeText(this, "Event not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Pre-fill fields
        editTextName.setText(currentEvent.getName());
        editTextDescription.setText(currentEvent.getDescription());
        editTextFee.setText(String.valueOf(currentEvent.getFee()));

        // Load categories
        categoryList = dbHelper.getAllCategories();
        List<String> categoryNames = new ArrayList<>();
        for (Category cat : categoryList) {
            categoryNames.add(cat.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Set spinner to current category
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId() == currentEvent.getCategoryId()) {
                spinnerCategory.setSelection(i);
                break;
            }
        }

        // Update button click
        buttonUpdate.setText("Update Event"); // just in case
        buttonUpdate.setOnClickListener(v -> updateEvent());
    }

    private Event getEventById(int id) {
        List<Event> allEvents = dbHelper.getAllEvents();
        for (Event e : allEvents) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    private void updateEvent() {
        String name = editTextName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String feeStr = editTextFee.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            return;
        }
        if (description.length() > 255) {
            editTextDescription.setError("Too long");
            return;
        }

        double fee = 0.0;
        try {
            fee = Double.parseDouble(feeStr);
            if (fee < 0) {
                editTextFee.setError("Fee must be ≥ 0");
                return;
            }
        } catch (NumberFormatException e) {
            editTextFee.setError("Invalid fee");
            return;
        }

        int selectedCategoryId = categoryList.get(spinnerCategory.getSelectedItemPosition()).getId();

        currentEvent.setName(name);
        currentEvent.setDescription(description);
        currentEvent.setFee(fee);
        currentEvent.setCategoryId(selectedCategoryId);

        int rows = dbHelper.updateEvent(currentEvent);
        if (rows > 0) {
            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }
}
