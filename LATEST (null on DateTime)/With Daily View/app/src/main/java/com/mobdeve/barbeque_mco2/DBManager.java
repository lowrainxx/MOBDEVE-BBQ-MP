package com.mobdeve.barbeque_mco2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private dbHelper dbHelper;
    private Context context;
    private  SQLiteDatabase db;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new dbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String date, String time) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.EVENT_NAME, name);
        contentValue.put(dbHelper.EVENT_DATE, date);
        contentValue.put(dbHelper.EVENT_TIME, time);
        db.insert(dbHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { dbHelper._ID, dbHelper.EVENT_NAME, dbHelper.EVENT_DATE, dbHelper.EVENT_TIME };
        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String date, String time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.EVENT_NAME, name);
        contentValues.put(dbHelper.EVENT_DATE, date);
        contentValues.put(dbHelper.EVENT_TIME, time);
        int i = db.update(dbHelper.TABLE_NAME, contentValues, dbHelper._ID + " = " + _id, null);
        return i;
    }
    public void delete(long _id) {
        db.delete(dbHelper.TABLE_NAME, dbHelper._ID + "=" + _id, null);
    }

}
