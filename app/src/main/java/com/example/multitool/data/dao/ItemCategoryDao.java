package com.example.multitool.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.multitool.data.database.DatabaseHelper;
import com.example.multitool.data.model.ItemCategory;

import java.util.ArrayList;
import java.util.List;

public class ItemCategoryDao extends DatabaseHelper {
    private final String TABLE_NAME = "item_categories";
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_COLOR_ID = "color_id";
    private final String COLUMN_SORT_ORDER = "sort_order";

    public ItemCategoryDao(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_COLOR_ID + " INTEGER, "
                + COLUMN_SORT_ORDER + " INTEGER, "
                + COLUMN_CREATED_ID + " TEXT, "
                + COLUMN_UPDATED_AT + " TEXT)";

        String DEFAULT_DATA = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_NAME + ", "
                + COLUMN_COLOR_ID + ", "
                + COLUMN_SORT_ORDER + ", "
                + COLUMN_CREATED_ID + ", "
                + COLUMN_UPDATED_AT
                + ") VALUES ('未分類', 1, 0, datetime('now'), datetime('now'))";

        Runnable action = () -> {
            db.execSQL(CREATE_TABLE);
            db.execSQL(DEFAULT_DATA);
        };

        execTransaction(db, action);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertData(String name, int color_id, int sort_order) {
        SQLiteDatabase db = this.getWritableDatabase();
        Runnable action = () -> {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_COLOR_ID, color_id);
            values.put(COLUMN_SORT_ORDER, sort_order);
            values.put(COLUMN_CREATED_ID, getCurrentDate());
            values.put(COLUMN_UPDATED_AT, getCurrentDate());
            db.insert(TABLE_NAME, null, values);
        };

        execTransaction(db, action);
    }

    public void updateItem(int id, String name, int color_id, int sort_order) {
        SQLiteDatabase db = this.getWritableDatabase();
        Runnable action = () -> {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_COLOR_ID, color_id);
            values.put(COLUMN_SORT_ORDER, sort_order);
            values.put(COLUMN_UPDATED_AT, getCurrentDate());
            db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        };

        execTransaction(db, action);
    }

    public List<ItemCategory> getAllData() {
        List<ItemCategory> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_SORT_ORDER);
        while (cursor.moveToNext()) {
            ItemCategory itemCategory = new ItemCategory();
            itemCategory.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            itemCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            itemCategory.setColorId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COLOR_ID)));
            itemCategory.setSortOrder(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SORT_ORDER)));
            itemCategory.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_ID)));
            itemCategory.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT)));
            dataList.add(itemCategory);
        }

        cursor.close();
        db.close();
        return dataList;
    }
}
