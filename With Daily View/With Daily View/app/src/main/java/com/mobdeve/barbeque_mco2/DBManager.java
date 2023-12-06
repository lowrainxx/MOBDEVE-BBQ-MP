package com.mobdeve.barbeque_mco2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;


public class DBManager extends SQLiteOpenHelper {
    private static DBManager dbManager;
    private static final String DATABASE_NAME = "EventDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Events";
    private static final String COUNTER = "Counter";

    private static final String EVENT_ID = "id";
    private static final String EVENT_NAME = "name";
    private static final String EVENT_DATE = "date";
    private static final String EVENT_TIME = "time";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbManager = this;
    }

    public static DBManager instanceofDatabase(Context context) {
        if(dbManager == null)
            dbManager = new DBManager(context);
        return dbManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(EVENT_ID)
                .append(" INT, ")
                .append(EVENT_NAME)
                .append(" TEXT, ")
                .append(EVENT_DATE)
                .append(" TEXT, ")
                .append(EVENT_TIME)
                .append(" TEXT)");

        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        switch(oldVersion) {
//            case 1:
//                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " = NEW_COLUMN + " TEXT");
//            case 2:
//                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " = NEW_COLUMN + " TEXT");
//        }
    }

    public void addEventToDB(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID, event.getId());
        contentValues.put(EVENT_NAME, event.getName());
        contentValues.put(EVENT_DATE, getStringFromDate(event.getDate()));
        contentValues.put(EVENT_TIME, getStringFromTime(event.getTime()));

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void populateEventListArray() {
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if(result.getCount() != 0) {
                int idIndex = result.getColumnIndex(EVENT_ID);
                int nameIndex = result.getColumnIndex(EVENT_NAME);
                int dateIndex = result.getColumnIndex(EVENT_DATE);
                int timeIndex = result.getColumnIndex(EVENT_TIME);

                while (result.moveToNext()) {
                    int id = result.getInt(idIndex);
                    String name = result.getString(nameIndex);
                    String stringDate = result.getString(dateIndex);
                    String stringTime = result.getString(timeIndex);
                    LocalDate date = getDateFromString(stringDate);
                    LocalTime time = getTimeFromString(stringTime);
                    Event event = new Event(id, name, date, time);
                    Event.eventsList.add(event);

//                while (result.moveToNext()) {
//                    int id = result.getInt(1);
//                    String name = result.getString(2);
//                    String stringDate = result.getString(3);
//                    String stringTime = result.getString(4);
//                    LocalDate date = getDateFromString(stringDate);
//                    LocalTime time = getTimeFromString(stringTime);
//                    Event event = new Event(id, name, date, time);
//                    Event.eventsList.add(event);
                }
            }
        }
    }

    public void updateEventInDB(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID, event.getId());
        contentValues.put(EVENT_NAME, event.getName());
        contentValues.put(EVENT_DATE, getStringFromDate(event.getDate()));
        contentValues.put(EVENT_TIME, getStringFromTime(event.getTime()));

        db.update(TABLE_NAME, contentValues, EVENT_ID + " =? ", new String[]{String.valueOf(event.getId())});
    }
    private String getStringFromDate(LocalDate date) {
        if(date == null)
            return null;
        return date.format(dateFormatter);
    }

    private LocalDate getDateFromString(String string) {
        try {
            return LocalDate.parse(string);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }

    private String getStringFromTime(LocalTime time) {
        if (time == null)
            return null;
        return time.format(timeFormatter);
    }

    private LocalTime getTimeFromString(String string) {
        try {
            return LocalTime.parse(string);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }

}
