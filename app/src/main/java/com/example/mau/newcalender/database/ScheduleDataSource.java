package com.example.mau.newcalender.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by mau on 9/3/2015.
 */
public class ScheduleDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_SUBJECT_NAME,
            MySQLiteHelper.COLUMN_TIME, MySQLiteHelper.COLUMN_DAY,
            MySQLiteHelper.COLUMN_ROOM_NO
    };

    public ScheduleDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void openWrite() throws  SQLException{
        database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE from " + MySQLiteHelper.TABLE_SCHEDULE);

    }

    public void close(){
        dbHelper.close();
    }

    public void addSchedule(String subject_name, String day, String time, String room_no){
        ContentValues values =  new ContentValues();

        values.put(MySQLiteHelper.COLUMN_SUBJECT_NAME, subject_name);
        values.put(MySQLiteHelper.COLUMN_ROOM_NO, room_no);
        values.put(MySQLiteHelper.COLUMN_DAY, day);
        values.put(MySQLiteHelper.COLUMN_TIME, time);

        database.insert(MySQLiteHelper.TABLE_SCHEDULE, null, values);
    }

    public Cursor fullSchedule(){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SCHEDULE, allColumns, null, null, null, null,null);
        return  cursor;
    }


}
