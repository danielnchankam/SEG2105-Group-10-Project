package com.example.localloop.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localloop.R;
import com.example.localloop.db.CategoryDatabaseHelper;

public class CategoryListActivity extends AppCompatActivity {

    private CategoryAdapter adapter;
    private CategoryDatabaseHelper db;

    @Override protected void onCreate(Bundle s){
        super.onCreate(s);
        setContentView(R.layout.activity_category_list);

        db = new CategoryDatabaseHelper(this);
        adapter = new CategoryAdapter(id -> {
            Intent in = new Intent(this, AddEditCategoryActivity.class);
            in.putExtra(AddEditCategoryActivity.EXTRA_ID, id);
            startActivity(in);
        });

        RecyclerView rv = findViewById(R.id.recyclerCategories);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        Button add = findViewById(R.id.btnAdd);
        add.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditCategoryActivity.class))
        );
    }
    @Override protected void onResume(){
        super.onResume();
        adapter.submitList(db.getAll());    // refresh
    }
}
