package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class password_db extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Login.db";
    public static final String TABLE_NAME= "user";
    public static final String COL_2 = "email";
    public static final  String COL_3 = "password";

    public password_db(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT , password TEXT   )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
     db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
     onCreate(db);
    }
    //registration handler
    public boolean insertData(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_2, email);
        contentvalues.put(COL_3, password);
        long result = db.insert(TABLE_NAME , null, contentvalues);
        if(result == -1)
        return false;
        else
            return true;
    }
    public boolean checkusername(String COL_2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email = ?", new String[]{COL_2});
        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }
    public boolean checkusernamepassword(String COL_2, String COL_3 ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where password = ?", new String[]{COL_3});
        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }
    //login handler
    public Cursor login_user(String email, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " where EMAIL=' "+email+" 'AND PASSWORD=' "+password+" ' ",null);
        return res;
    }


}
