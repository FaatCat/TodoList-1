package com.example.TodoList.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TaskDBHelper extends SQLiteOpenHelper {

    public TaskDBHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT, %s TEXT, %s INTEGER DEFAULT 0, " +
                                "%s TEXT, %s TEXT, %s INTEGER DEFAULT 0)", TaskContract.TABLE,
                        TaskContract.Columns.ID,
                        TaskContract.Columns.TASK,
                        TaskContract.Columns.description,
                        TaskContract.Columns.isDone,
                        TaskContract.Columns.created,
                        TaskContract.Columns.due,
                        TaskContract.Columns.showNotification);

        Log.e("TaskDBHelper", "Query to form table: " + sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE);
        onCreate(sqlDB);
    }
}
