package com.example.prashant.habittrackerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.prashant.habittrackerapp.data.HabitTrackerContract;
import com.example.prashant.habittrackerapp.data.HabitTrackerDbHelper;

public class MainActivity extends AppCompatActivity {

  private final String LOG_TAG = MainActivity.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    HabitTrackerDbHelper mDbHelper = new HabitTrackerDbHelper(this);
    insertIntoDB(mDbHelper.getWritableDatabase());
    readFromDB(mDbHelper.getReadableDatabase());
    updateEntry(mDbHelper.getReadableDatabase(), "morning", true);
    readFromDB(mDbHelper.getReadableDatabase());
    deleteEntries(mDbHelper.getReadableDatabase());
    readFromDB(mDbHelper.getReadableDatabase());
  }

  private void insertIntoDB(SQLiteDatabase db) {
    ContentValues values = new ContentValues();
    values.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_HABIT, "Waking up early in morning");
    values.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_IS_GOOD_HABIT, false);
    db.insert(HabitTrackerContract.HabitEntry.TABLE_NAME, null, values);

    values = new ContentValues();
    values.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_HABIT, "Mocking otthers");
    values.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_IS_GOOD_HABIT, false);
    db.insert(HabitTrackerContract.HabitEntry.TABLE_NAME, null, values);
  }

  private void readFromDB(SQLiteDatabase db) {
    String[] projection = {
        HabitTrackerContract.HabitEntry._ID, HabitTrackerContract.HabitEntry.COLUMN_NAME_HABIT,
        HabitTrackerContract.HabitEntry.COLUMN_NAME_IS_GOOD_HABIT
    };
    String sortOrder = HabitTrackerContract.HabitEntry._ID + " ASC";
    Cursor cursor =
        db.query(HabitTrackerContract.HabitEntry.TABLE_NAME, projection, null, null, null, null,
            sortOrder);
    if (cursor.moveToFirst()) {
      do {
        String habit = cursor.getString(
            cursor.getColumnIndexOrThrow(HabitTrackerContract.HabitEntry.COLUMN_NAME_HABIT));
        boolean isGoodHabit = cursor.getInt(
            cursor.getColumnIndexOrThrow(HabitTrackerContract.HabitEntry.COLUMN_NAME_IS_GOOD_HABIT))
            > 0;
        Log.d(LOG_TAG, "My habit :" + habit + " is a good habit : " + isGoodHabit);
      } while (cursor.moveToNext());
    } else {
      Log.d(LOG_TAG, "No data");
    }
    cursor.close();
  }

  private void deleteEntries(SQLiteDatabase db) {
    db.delete(HabitTrackerContract.HabitEntry.TABLE_NAME, null, null);
  }

  private void updateEntry(SQLiteDatabase db, String habit, Boolean habitType) {
    ContentValues values = new ContentValues();
    values.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_IS_GOOD_HABIT, habitType);

    String selection = HabitTrackerContract.HabitEntry.COLUMN_NAME_HABIT + " LIKE ?";
    String[] selectionArgs = { "%" + habit + "%" };

    int count =
        db.update(HabitTrackerContract.HabitEntry.TABLE_NAME, values, selection, selectionArgs);

    Log.d(LOG_TAG, "Rows updated: " + count);
  }
}
