package com.alex_sav.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alex_sav.habittracker.data.HabitContract.HabitEntry;

public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habittracker.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HabitEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + HabitEntry.COLUMN_SUBTITLE + " TEXT NOT NULL,"
                + HabitEntry.COLUMN_START_DATE + " DATETIME NOT NULL DEFAULT CURRENT_DATE,"
                + HabitEntry.COLUMN_REPEAT + " INTEGER NOT NULL DEFAULT 0);";

        sqLiteDatabase.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
