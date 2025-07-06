package com.example.localloop.ui.category;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localloop.DatabaseHelper;
import com.example.localloop.R;
import com.example.localloop.model.Event;

import java.util.List;

public class MyEventsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        eventList = dbHelper.getAllEvents();

        EventAdapter adapter = new EventAdapter(eventList, event -> {
            Intent intent = new Intent(MyEventsActivity.this, EditEventActivity.class);
            intent.putExtra("eventId", event.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}
