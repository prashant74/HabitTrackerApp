package com.example.prashant.habittrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prashant on 6/25/16.
 */
public class HabitTrackerDbHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  static final String DATABASE_NAME = "habittracker.db";
  private static final String CREATE_HABIT_TRACKER_TABLE =
      "CREATE TABLE " + HabitTrackerContract.HabitEntry.TABLE_NAME + " (" +
          HabitTrackerContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          HabitTrackerContract.HabitEntry.COLUMN_NAME_HABIT + " TEXT NOT NULL," +
          HabitTrackerContract.HabitEntry.COLUMN_NAME_IS_GOOD_HABIT + " BOOLEAN NOT NULL);";
  private static final String DELETE_HABIT_TRACKER_TABLE =
      "DROP TABLE IF EXISTS" + HabitTrackerContract.HabitEntry.TABLE_NAME;

  private Context context;

  public HabitTrackerDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_HABIT_TRACKER_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL(DELETE_HABIT_TRACKER_TABLE);
    onCreate(sqLiteDatabase);
  }

  public void deleteDatabase() {
    context.deleteDatabase(DATABASE_NAME);
  }
}
