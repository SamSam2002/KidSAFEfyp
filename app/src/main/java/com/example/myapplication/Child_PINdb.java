package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Child_PINdb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Child_pin.db";
    public static final String TABLE_NAME = "Child";
    public static final String COL_2 = "name";
    public static final String COL_3 = "Pin";

    public Child_PINdb(@Nullable Context context) {
        super(context, "Child_pin.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , Pin TEXT   )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Register PIN
    public boolean insertData(String name, String pin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_2, name);
        contentvalues.put(COL_3, pin);
        long result = db.insert(TABLE_NAME, null, contentvalues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean checkname(String COL_2) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from child where name = ?", new String[]{COL_2});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checknamepin(String COL_2, String COL_3) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from child where pin = ?", new String[]{COL_3});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Cursor login_child(String name, String pin){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME+ " where name=' "+name+" 'AND pin=' "+pin+" ' ",null);
        return res;
    }
}
