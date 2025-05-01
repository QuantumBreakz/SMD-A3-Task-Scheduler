package com.example.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "taskmanager.db";
    private static final int DATABASE_VERSION = 1;

    // Tasks table
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK_ID = "id";
    private static final String COLUMN_TASK_TITLE = "title";
    private static final String COLUMN_TASK_DESCRIPTION = "description";
    private static final String COLUMN_TASK_DATETIME = "datetime";
    private static final String COLUMN_TASK_STATUS = "status";

    // Notifications table
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String COLUMN_NOTIFICATION_ID = "id";
    private static final String COLUMN_NOTIFICATION_MESSAGE = "message";
    private static final String COLUMN_NOTIFICATION_DATETIME = "datetime";

    // Create Tasks Table Query
    private static final String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
            + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TASK_TITLE + " TEXT NOT NULL, "
            + COLUMN_TASK_DESCRIPTION + " TEXT, "
            + COLUMN_TASK_DATETIME + " TEXT NOT NULL, "
            + COLUMN_TASK_STATUS + " TEXT NOT NULL)";

    // Create Notifications Table Query
    private static final String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
            + COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NOTIFICATION_MESSAGE + " TEXT NOT NULL, "
            + COLUMN_NOTIFICATION_DATETIME + " TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASKS_TABLE);
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);
        // Add some dummy notifications
        addInitialNotifications(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    // Task CRUD Operations
    public long addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTitle());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_DATETIME, task.getDateTime());
        values.put(COLUMN_TASK_STATUS, task.getStatus());
        long id = db.insert(TABLE_TASKS, null, values);
        db.close();
        return id;
    }

    public Task getTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, null,
                COLUMN_TASK_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        Task task = null;
        if (cursor != null && cursor.moveToFirst()) {
            task = new Task(
                cursor.getLong(cursor.getColumnIndex(COLUMN_TASK_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TASK_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DATETIME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TASK_STATUS))
            );
            cursor.close();
        }
        db.close();
        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " ORDER BY " + COLUMN_TASK_DATETIME + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TASK_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DATETIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_STATUS))
                );
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public List<Task> getFutureTasks() {
        List<Task> tasks = new ArrayList<>();
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + 
                " WHERE " + COLUMN_TASK_DATETIME + " > ?" +
                " ORDER BY " + COLUMN_TASK_DATETIME + " ASC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{currentDateTime});

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TASK_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DATETIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_STATUS))
                );
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public List<Task> getPastTasks() {
        List<Task> tasks = new ArrayList<>();
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + 
                " WHERE " + COLUMN_TASK_DATETIME + " <= ?" +
                " ORDER BY " + COLUMN_TASK_DATETIME + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{currentDateTime});

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TASK_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DATETIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_STATUS))
                );
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTitle());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_DATETIME, task.getDateTime());
        values.put(COLUMN_TASK_STATUS, task.getStatus());

        int rowsAffected = db.update(TABLE_TASKS, values,
                COLUMN_TASK_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();
        return rowsAffected;
    }

    public void deleteTask(long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_TASK_ID + "=?", new String[]{String.valueOf(taskId)});
        db.close();
    }

    // Notification CRUD Operations
    public long addNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTIFICATION_MESSAGE, notification.getMessage());
        values.put(COLUMN_NOTIFICATION_DATETIME, notification.getDateTime());
        long id = db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();
        return id;
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATIONS + 
                " ORDER BY " + COLUMN_NOTIFICATION_DATETIME + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_NOTIFICATION_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_MESSAGE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_DATETIME))
                );
                notifications.add(notification);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notifications;
    }

    private void addInitialNotifications(SQLiteDatabase db) {
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTIFICATION_MESSAGE, "Welcome to Task Manager!");
        values.put(COLUMN_NOTIFICATION_DATETIME, currentDateTime);
        db.insert(TABLE_NOTIFICATIONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_NOTIFICATION_MESSAGE, "You can manage your tasks here");
        values.put(COLUMN_NOTIFICATION_DATETIME, currentDateTime);
        db.insert(TABLE_NOTIFICATIONS, null, values);

        values = new ContentValues();
        values.put(COLUMN_NOTIFICATION_MESSAGE, "Don't forget to check your upcoming tasks");
        values.put(COLUMN_NOTIFICATION_DATETIME, currentDateTime);
        db.insert(TABLE_NOTIFICATIONS, null, values);
    }
} 