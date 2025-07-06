package com.example.localloop.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.localloop.model.Category;

import java.util.ArrayList;
import java.util.List;

/** Handles the 'categories' table only. */
public class CategoryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "localloop.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE = "categories";
    private static final String COL_ID   = "id";
    private static final String COL_NAME = "name";
    private static final String COL_DESC = "description";

    public CategoryDatabaseHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT UNIQUE NOT NULL," +
                COL_DESC + " TEXT NOT NULL)");
    }
    @Override public void onUpgrade(SQLiteDatabase db,int o,int n){}

    /* ---------- CRUD ---------- */
    public long insert(Category c){
        ContentValues v = new ContentValues();
        v.put(COL_NAME, c.getName());
        v.put(COL_DESC, c.getDescription());
        return getWritableDatabase().insert(TABLE, null, v);
    }
    public int update(Category c){
        ContentValues v = new ContentValues();
        v.put(COL_NAME, c.getName());
        v.put(COL_DESC, c.getDescription());
        return getWritableDatabase().update(TABLE, v, COL_ID+"=?",
                new String[]{String.valueOf(c.getId())});
    }
    public int remove(Category c) {
        return getWritableDatabase().delete(TABLE, COL_ID + "=?",
                new String[]{String.valueOf(c.getId())});
    }
    public Category get(int id){
        Cursor cur = getReadableDatabase().query(TABLE, null,
                COL_ID+"=?", new String[]{String.valueOf(id)},
                null,null,null);
        if(!cur.moveToFirst()) { cur.close(); return null; }
        Category c = new Category(
                cur.getInt(cur.getColumnIndexOrThrow(COL_ID)),
                cur.getString(cur.getColumnIndexOrThrow(COL_NAME)),
                cur.getString(cur.getColumnIndexOrThrow(COL_DESC)));
        cur.close();
        return c;
    }
    public List<Category> getAll(){
        List<Category> list = new ArrayList<>();
        Cursor cur = getReadableDatabase().query(TABLE, null,
                null,null,null,null, COL_NAME);
        while(cur.moveToNext()){
            list.add(get(cur.getInt(cur.getColumnIndexOrThrow(COL_ID))));
        }
        cur.close();
        return list;
    }

    /* ---------- validation ---------- */
    public boolean nameExists(String name,int excludeId){
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT 1 FROM "+TABLE+" WHERE "+COL_NAME+"=? AND "+COL_ID+"!=?",
                new String[]{name, String.valueOf(excludeId)});
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }
}
