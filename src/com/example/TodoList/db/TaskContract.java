package com.example.TodoList.db;

public class TaskContract {
	public static final String DB_NAME = "com.example.TodoList.db.tasks";
	public static final int DB_VERSION = 4;
	public static final String TABLE = "tasks";


	public class Columns {
        public static final String ID = "taskID";
        public static final String TASK = "taskName";
        public static final String description = "taskDescription";
        public static final String created = "taskCreated";
        public static final String due = "taskDue";
        public static final String isDone = "taskIsDone";
        public static final String showNotification= "taskShowNotification";
		//public static final String _ID = BaseColumns._ID;
	}
}
