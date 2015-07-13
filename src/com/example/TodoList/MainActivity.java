package com.example.TodoList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TodoList.db.TaskContract;
import com.example.TodoList.db.TaskDBHelper;

public class MainActivity extends ListActivity {
	private ListAdapter listAdapter;
	private TaskDBHelper helper;
    private UIFunctions UI = new UIFunctions();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		UI.updateUI(this);
//        UI.toast(TaskContract.Columns._ID);
        Log.d("mainactivity","onCreate");
        }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add_task:
                UI.addTask(this);
				return true;

			default:
				return false;
		}
	}

	public void onDoneButtonClick(View view) {
		Log.d("mainactivity","doneclicked");
        View v = (View) view.getParent();
		TextView taskIdView = (TextView) v.findViewById(R.id.taskId);
        String taskId = taskIdView.getText().toString();
//		String taskName = taskTextView.getText().toString();

		String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                        TaskContract.TABLE,
						"_id",
                        taskId
						);
        Log.d("mainactivity","done2");
		helper = new TaskDBHelper(MainActivity.this);
		SQLiteDatabase sqlDB = helper.getWritableDatabase();
		sqlDB.execSQL(sql);
		UI.updateUI(this);
        Log.d("mainactivity","done3");
	}

    private class UIFunctions {
        public boolean addTask(final Context context) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Add a task");
            builder.setMessage("What do you want to do?");
            final EditText inputField = new EditText(context);
            builder.setView(inputField);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String task = inputField.getText().toString();
                    Task newTask = new Task();
                    newTask.set_name(task);
                    addTaskError(Task.addTask(context, newTask));
                    updateUI(context);
                }
            });
            builder.setNegativeButton("Cancel",null);
            builder.create().show();
            return true;
        }

        private void addTaskError(int errorCode) {
            if (errorCode!=0) {
                toast("DB: Error adding task!");
            }
        }


        public void toast(String message) {
            toast(message,true);
        }
        public void toast(String message, boolean isDurationLong) {
            Toast.makeText(getApplicationContext(), message, (isDurationLong?Toast.LENGTH_LONG:Toast.LENGTH_SHORT)).show();
        }

        public void updateUI(final Context context) {
            Log.d("UI","updateUI");
            helper = new TaskDBHelper(MainActivity.this);
            SQLiteDatabase sqlDB = helper.getReadableDatabase();
            Cursor cursor = sqlDB.query(TaskContract.TABLE,
                    new String[]{TaskContract.Columns.ID, TaskContract.Columns.TASK},
                    null, null, null, null, null);
            listAdapter = new SimpleCursorAdapter(
                    context,
                    R.layout.task_view,
                    cursor,
                    new String[]{TaskContract.Columns.TASK},
                    new int[]{R.id.taskTextView},
                    0
            );
            MainActivity.this.setListAdapter(listAdapter);
            sqlDB.close();
        }
    }
}
