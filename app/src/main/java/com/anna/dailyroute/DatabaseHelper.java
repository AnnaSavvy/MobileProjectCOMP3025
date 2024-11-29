package com.anna.dailyroute;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RoutineChecker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ROUTINE = "routine";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COMPLETED = "completed";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ROUTINE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_COMPLETED + " INTEGER" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINE);
        onCreate(db);
    }

    public void addRoutineItem(RoutineItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_COMPLETED, item.isCompleted() ? 1 : 0);
        db.insert(TABLE_ROUTINE, null, values);
        db.close();
    }

    public ArrayList<RoutineItem> getAllRoutineItems() {
        ArrayList<RoutineItem> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ROUTINE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                RoutineItem item = new RoutineItem();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setCompleted(cursor.getInt(2) == 1);
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }
}