package com.example.TodoList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.TodoList.db.TaskContract;
import com.example.TodoList.db.TaskDBHelper;

import java.util.Date;

/**
 * Created by Cryos on 7/13/2015.
 */
public class Task {
    private int _id;
    private String _name;
    private String _description;
    private java.util.Date _createdDate;
    private java.util.Date _dueDate;
    private boolean _showNotification;
    //private Color _color;

    public Task() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Date get_createdDate() {
        return _createdDate;
    }

    public void set_createdDate(Date _createdDate) {
        this._createdDate = _createdDate;
    }

    public Date get_dueDate() {
        return _dueDate;
    }

    public void set_dueDate(Date _dueDate) {
        this._dueDate = _dueDate;
    }

    public static int addTask(Context context, Task newTask) {
        TaskDBHelper dbHelper = new TaskDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.clear();
        values.put(TaskContract.Columns.TASK, newTask.get_name());

        long result = db.insertOrThrow(TaskContract.TABLE, null, values);
        db.close();

        if (result != -1) {
//            INSERT SUCCESSFUL - RESULT: ROWID OF NEW ROW
            return 0;
        } else {
//            ERROR IN INSERT
            return -1;
        }
    }
}
