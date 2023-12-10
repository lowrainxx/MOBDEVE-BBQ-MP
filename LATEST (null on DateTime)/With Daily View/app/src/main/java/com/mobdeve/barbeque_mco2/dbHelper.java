package com.mobdeve.barbeque_mco2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "EVENTS";

    public static final String _ID = "_id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_TIME = "time";

    static final String DB_NAME = "EVENT_LIST.DB";

    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENT_NAME + " TEXT NOT NULL, "
            + EVENT_DATE + " TEXT NOT NULL, " + EVENT_TIME + " TEXT);";

    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
