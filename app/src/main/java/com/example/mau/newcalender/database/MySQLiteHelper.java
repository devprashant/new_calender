package com.example.mau.newcalender.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mau on 9/3/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{
    public static final String COLUMN_TIME = "time_slot";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_SUBJECT_NAME = "subject_name";
    public static final String COLUMN_ID = "_id";
    public static final String TABLE_SCHEDULE = "schedule";
    public static final String COLUMN_ROOM_NO = "room_no";
    public static final String DATABASE_TABLE_CREATE =
            "create table " +   TABLE_SCHEDULE
                    + "(" + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_SUBJECT_NAME + " text not null, "
                    + COLUMN_DAY + " text not null, "
                    + COLUMN_TIME + " text not null, "
                    + COLUMN_ROOM_NO + " text not null); "
            ;
    public static final String DATABASE_NAME = "schedule.db";
    public static final int DATABASE_VERSION = 1;

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion +
                " to version " + newVersion + " which will destroy the old data ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        onCreate(db);
    }
}
