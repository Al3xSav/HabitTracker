package com.alex_sav.habittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {

    /*public static final String CONTENT_AUTHORITY = "com.alex_sav.habittracker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_HABIT = "habits";*/

    public static final class HabitEntry implements BaseColumns {

        //public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HABIT);

        public final static String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_SUBTITLE = "subtitle";
        public final static String COLUMN_START_DATE = "start_date";
        public final static String COLUMN_REPEAT = "number_of_times";
    }
}
