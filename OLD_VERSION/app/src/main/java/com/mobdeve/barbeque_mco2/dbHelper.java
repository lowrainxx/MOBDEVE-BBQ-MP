package com.mobdeve.barbeque_mco2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "EVENTS";

    public static final String _ID = "_id";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_DATE = "event_date";
    public static final String EVENT_TIME = "event_time";

    static final String DB_NAME = "EVENTS_DB";
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVENT_NAME + " TEXT NOT NULL, " +
                    EVENT_DATE + " TEXT NOT NULL, " +
                    EVENT_TIME + " TEXT NOT NULL);";

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

