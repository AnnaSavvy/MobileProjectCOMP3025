package com.anna.dailyroute;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RoutineChecker.db";
    private static final int DATABASE_VERSION = 2; // Incremented version
    private static final String TABLE_ROUTINE = "routine";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COMPLETED = "completed";
    private static final String COLUMN_DATE_COMPLETED = "date_completed";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ROUTINE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_COMPLETED + " INTEGER,"
                + COLUMN_DATE_COMPLETED + " TEXT" + ")";
        db.execSQL(createTable);
        Log.d("DatabaseHelper", "Table " + TABLE_ROUTINE + " created successfully.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_ROUTINE + " ADD COLUMN completion_date TEXT");
            Log.d("DatabaseHelper", "Table " + TABLE_ROUTINE + " upgraded to version " + newVersion);
        }
    }

    public void addRoutineItem(RoutineItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_COMPLETED, item.isCompleted() ? 1 : 0);
        values.put(COLUMN_DATE_COMPLETED, item.getCompletionDate());
        db.insert(TABLE_ROUTINE, null, values);
        db.close();
    }

    public void updateRoutineItem(RoutineItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_COMPLETED, item.isCompleted() ? 1 : 0);
        values.put(COLUMN_DATE_COMPLETED, item.getCompletionDate());
        db.update(TABLE_ROUTINE, values, COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public ArrayList<RoutineItem> getAllRoutineItems() {
        ArrayList<RoutineItem> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ROUTINE;
        Log.d("DatabaseHelper", "Executing query: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                RoutineItem item = new RoutineItem();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setCompleted(cursor.getInt(2) == 1);
                item.setCompletionDate(cursor.getString(3)); // Pega a data de conclusão
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}