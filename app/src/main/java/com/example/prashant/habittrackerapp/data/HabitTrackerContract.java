package com.example.prashant.habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by prashant on 6/25/16.
 */
public class HabitTrackerContract {
  private HabitTrackerContract() {
  }

  public static abstract class HabitEntry implements BaseColumns {
    public static final String TABLE_NAME = "habitEntry";
    public static final String COLUMN_NAME_HABIT = "habit";
    public static final String COLUMN_NAME_IS_GOOD_HABIT = "isGoodHabit";
  }
}
