package sg.edu.rp.c346.id19011785.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.core.database.CursorWindowCompat;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "tasks.db";

    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        // Create table(s) again
        onCreate(db);
    }

    public void insertTask(String description, String date){
        // Get an instance of database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Use ContentValues object to store values for the db operation
        ContentValues values = new ContentValues();
        // Store column name as key and the DESCRIPTION as value
        values.put(COLUMN_DESCRIPTION, description);
        // Store column name as key and DATE as value
        values.put(COLUMN_DATE, date);
        // Insert row into the TABLE_TASK
        db.insert(TABLE_TASK, null, values);
        // Close database connection
        db.close();
    }

    public ArrayList<String> getTaskContent() {
        // Create an ArrayList that holds String objects
        ArrayList<String> tasks = new ArrayList<String>();
        // Select all the tasks' description
        String selectQuery = "SELECT " + COLUMN_DESCRIPTION +
                " FROM " + TABLE_TASK;

        // Get instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        // Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);

        // moveToFirst() moves to first row, null if no records
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row & returns true; moveToNext() return false
            // when no more next row to move to
            do { // Add task content to ArrayList object; getString(0) retrieves first column data
                // getString(1) return 2nd column data ; getInt(0) if data is an integer value
                tasks.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close the connection
        cursor.close();
        db.close();

        return tasks;
    }

    public ArrayList<Task> getTasks(boolean asc) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_DESCRIPTION + ", "
                + COLUMN_DATE
                + " FROM " + TABLE_TASK
                + " ORDER BY " + COLUMN_DESCRIPTION; // Section K from Worksheet

        if (asc) {
            selectQuery += " ASC";
        }
        else{
            selectQuery += " DESC";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                String date = cursor.getString(2);
                Task obj = new Task(id, description, date);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
