package com.example.multitool.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.multitool.data.database.DatabaseHelper;
import com.example.multitool.model.ChecklistItem;
import java.util.ArrayList;
import java.util.List;

public class ChecklistItemDao extends DatabaseHelper {
    public final String TABLE_NAME = "checklist_items";    // テーブル名
    public final String COLUMN_ID = "id";      // ID
    public final String COLUMN_NAME = "name";  // 名前
    public final String COLUMN_IS_CHECK = "is_check";      // チェック済みかどうか
    public final String COLUMN_IS_DELETE = "is_delete";    // 削除済みかどうか

    public ChecklistItemDao(Context context){ super(context); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_IS_CHECK + " INTEGER, "
                + COLUMN_IS_DELETE + " INTEGER, "
                + COLUMN_CREATED_AT + " TEXT, "
                + COLUMN_UPDATED_AT + " TEXT)";

        Runnable action = () -> db.execSQL(CREATE_TABLE);
        execTransaction(db, action);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertData(String name, int is_check, int is_delete, String current_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        Runnable action = () -> {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_IS_CHECK, is_check);
            values.put(COLUMN_IS_DELETE, is_delete);
            values.put(COLUMN_CREATED_AT, current_time);
            values.put(COLUMN_UPDATED_AT, current_time);
            db.insert(TABLE_NAME, null, values);
        };

        execTransaction(db, action);
    }

    public void updateItem(int id, String name, int is_check, int is_delete, String current_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        Runnable action = () -> {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_IS_CHECK, is_check);
            values.put(COLUMN_IS_DELETE, is_delete);
            values.put(COLUMN_UPDATED_AT, current_time);
            db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        };

        execTransaction(db, action);
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Runnable action = () -> {
            db.delete(TABLE_NAME, null, null);
        };

        execTransaction(db, action);
    }

    public List<ChecklistItem> getData(String[] columns, String selection, String[] selectionArgs){
        List<ChecklistItem> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            while (cursor.moveToNext()) {
                ChecklistItem checklistItem = new ChecklistItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getColumnIndexOrThrow(COLUMN_IS_CHECK),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT))
                );

                dataList.add(checklistItem);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            db.close();
        }

        return dataList;
    }
}
