package vn.homemade.todotask.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.homemade.todotask.model.TaskModel;

/**
 * Created by TanLe on 2/23/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "todo";
    private static final String TABLE_TASK = "task";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_TIME = "time";
    private static final String KEY_PRIORITY = "priority";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT,"
                + KEY_TIME + " LONG,"
                + KEY_PRIORITY + " INTEGER"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        // Create tables again
        onCreate(db);
    }

    public void addTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getId());
        values.put(KEY_NAME, task.getName());
        values.put(KEY_DESC, task.getDesc());
        values.put(KEY_TIME, task.getTime());
        values.put(KEY_PRIORITY, task.getPriority());

        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    public List<TaskModel> getAllTasks() {
        List<TaskModel> result = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();
                task.setId(cursor.getString(0));
                task.setName(cursor.getString(1));
                task.setDesc(cursor.getString(2));
                task.setTime(cursor.getLong(3));
                task.setPriority(cursor.getInt(4));
                result.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return result;
    }

    public void updateTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_DESC, task.getDesc());
        values.put(KEY_TIME, task.getTime());
        values.put(KEY_PRIORITY, task.getPriority());

        // updating row
        db.update(TABLE_TASK, values, KEY_ID + " = ?", new String[]{task.getId()});
        db.close();
    }

    public void deleteTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, KEY_ID + " = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }
}
