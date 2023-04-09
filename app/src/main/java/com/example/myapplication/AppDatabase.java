package com.example.myapplication;


import static org.litepal.util.Const.TableSchema.COLUMN_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.AppItem;

import java.util.ArrayList;
import java.util.List;

public class AppDatabase extends SQLiteOpenHelper {

    // Database information
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "my_app_database";

    // Table information
    private static final String TABLE_NAME = "selected_apps";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PACKAGE_NAME = "package_name";

    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PACKAGE_NAME + " TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);

        // Create the table again
        onCreate(db);
    }

    public void addApp(String packageName) {
        // Get the writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object with the app data
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACKAGE_NAME, packageName);

        // Insert the app data into the table
        db.insert(TABLE_NAME, null, values);

        // Close the database connection
        db.close();
    }

    public void removeApp(String packageName) {
        // Get the writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the app from the table
        db.delete(TABLE_NAME, COLUMN_PACKAGE_NAME + " = ?", new String[]{packageName});

        // Close the database connection
        db.close();
    }

    public List<String> getAllApps() {
        List<String> appList = new ArrayList<>();

        // Select all the apps from the table
        String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_QUERY, null);

        // Loop through the cursor and add each app to the list
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String packageName = cursor.getString(cursor.getColumnIndex(COLUMN_PACKAGE_NAME));
                appList.add(packageName);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return appList;
    }
}
