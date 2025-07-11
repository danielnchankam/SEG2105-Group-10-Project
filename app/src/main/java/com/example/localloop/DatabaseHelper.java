package com.example.localloop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.localloop.model.Category;
import com.example.localloop.model.Event;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LocalLoop.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL);";

    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_CAT_ID = "id";
    public static final String COLUMN_CAT_NAME = "name";

    private static final String CREATE_TABLE_CATEGORIES =
            "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                    COLUMN_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CAT_NAME + " TEXT NOT NULL, " +
                    "description TEXT);";

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_EVENT_ID = "id";
    public static final String COLUMN_EVENT_NAME = "name";
    public static final String COLUMN_EVENT_DESCRIPTION = "description";
    public static final String COLUMN_EVENT_FEE = "fee";
    public static final String COLUMN_EVENT_CATEGORY_ID = "categoryId";
    public static final String COLUMN_EVENT_DATE = "date";
    public static final String COLUMN_EVENT_TIME = "time";

    private static final String CREATE_TABLE_EVENTS =
            "CREATE TABLE " + TABLE_EVENTS + " (" +
                    COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EVENT_NAME + " TEXT NOT NULL, " +
                    COLUMN_EVENT_DESCRIPTION + " TEXT, " +
                    COLUMN_EVENT_FEE + " REAL, " +
                    COLUMN_EVENT_CATEGORY_ID + " INTEGER, " +
                    COLUMN_EVENT_DATE + " TEXT, " +
                    COLUMN_EVENT_TIME + " TEXT, " +
                    "FOREIGN KEY(" + COLUMN_EVENT_CATEGORY_ID + ") REFERENCES " +
                    TABLE_CATEGORIES + "(" + COLUMN_CAT_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    public long insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, event.getName());
        values.put(COLUMN_EVENT_DESCRIPTION, event.getDescription());
        values.put(COLUMN_EVENT_FEE, event.getFee());
        values.put(COLUMN_EVENT_CATEGORY_ID, event.getCategoryId());
        values.put(COLUMN_EVENT_DATE, event.getDate());
        values.put(COLUMN_EVENT_TIME, event.getTime());

        long id = db.insert(TABLE_EVENTS, null, values);
        db.close();
        return id;
    }

    public long insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAT_NAME, category.getName());
        values.put("description", category.getDescription());
        long id = db.insert(TABLE_CATEGORIES, null, values);
        db.close();
        return id;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES,
                new String[]{COLUMN_CAT_ID, COLUMN_CAT_NAME, "description"},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAT_ID)));
                category.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAT_NAME)));
                category.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EVENT_ID)));
                event.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_NAME)));
                event.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DESCRIPTION)));
                event.setFee(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EVENT_FEE)));
                event.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EVENT_CATEGORY_ID)));
                event.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DATE)));
                event.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_TIME)));
                events.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, event.getName());
        values.put(COLUMN_EVENT_DESCRIPTION, event.getDescription());
        values.put(COLUMN_EVENT_FEE, event.getFee());
        values.put(COLUMN_EVENT_CATEGORY_ID, event.getCategoryId());
        values.put(COLUMN_EVENT_DATE, event.getDate());
        values.put(COLUMN_EVENT_TIME, event.getTime());

        int rows = db.update(TABLE_EVENTS, values, COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(event.getId())});
        db.close();
        return rows;
    }
}
