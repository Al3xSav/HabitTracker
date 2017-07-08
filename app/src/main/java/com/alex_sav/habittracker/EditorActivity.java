package com.alex_sav.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alex_sav.habittracker.data.HabitContract.HabitEntry;
import com.alex_sav.habittracker.data.HabitDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {

    public String mCurrentDate;
    @BindView(R.id.edit_text_title)
    EditText mNameEditText;
    @BindView(R.id.edit_text_subtitle)
    EditText mSubtitleEditText;
    @BindView(R.id.text_view_date)
    TextView mDateTextView;
    @BindView(R.id.edit_text_counter)
    EditText mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mCurrentDate = simpleDateFormat.format(calendar.getTime());
        Log.d("Edit", mCurrentDate);
        mDateTextView.setText(mCurrentDate);

    }

    private void insertHabit() {

        String nameString = mNameEditText.getText().toString().trim();
        String subtitleString = mSubtitleEditText.getText().toString().trim();
        String counterString = mCounter.getText().toString().trim();
        int counter = 0;

        if (!"".equals(counterString)) {
            counter = Integer.parseInt(counterString);
        }

        HabitDbHelper habitDbHelper = new HabitDbHelper(this);
        SQLiteDatabase db = habitDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitEntry.COLUMN_NAME, nameString);
        contentValues.put(HabitEntry.COLUMN_SUBTITLE, subtitleString);
        contentValues.put(HabitEntry.COLUMN_START_DATE, mCurrentDate);
        contentValues.put(HabitEntry.COLUMN_REPEAT, counter);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, contentValues);

        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onSave(MenuItem item) {
        if ("".equals(mNameEditText.getText().toString())) {
            Toast.makeText(this, "Name required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        insertHabit();
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
}
