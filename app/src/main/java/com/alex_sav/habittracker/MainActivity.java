package com.alex_sav.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alex_sav.habittracker.data.HabitContract.HabitEntry;
import com.alex_sav.habittracker.data.HabitDbHelper;

import static com.alex_sav.habittracker.data.HabitContract.HabitEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabase();
    }

    private Cursor read() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_SUBTITLE,
                HabitEntry.COLUMN_START_DATE,
                HabitEntry.COLUMN_REPEAT
        };

        return db.query(TABLE_NAME, projection, null, null, null, null, null);
    }

    private void displayDatabase() {
        Cursor cursor = read();

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("The habit table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_NAME + " - " +
                    HabitEntry.COLUMN_SUBTITLE + " - " +
                    HabitEntry.COLUMN_START_DATE + " - " +
                    HabitEntry.COLUMN_REPEAT + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
            int subtitleColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_SUBTITLE);
            int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_START_DATE);
            int numberOfTimesColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_REPEAT);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentSubtitle = cursor.getString(subtitleColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                int currentNumberOfTimes = cursor.getInt(numberOfTimesColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentSubtitle + " - " +
                        currentDate + " - " +
                        currentNumberOfTimes));
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insert();
                displayDatabase();
                return true;
            case R.id.action_delete_all_entries:
                delete();
                displayDatabase();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insert() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        values1.put(HabitEntry.COLUMN_NAME, "Eat Food");
        values1.put(HabitEntry.COLUMN_SUBTITLE, "Healthy");
        values1.put(HabitEntry.COLUMN_REPEAT, 3);
        values2.put(HabitEntry.COLUMN_NAME, "Drink Water");
        values2.put(HabitEntry.COLUMN_SUBTITLE, "Mineral");
        values2.put(HabitEntry.COLUMN_REPEAT, 2);
        db.insert(TABLE_NAME, null, values1);
        db.insert(TABLE_NAME, null, values2);
    }

    private void delete() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
