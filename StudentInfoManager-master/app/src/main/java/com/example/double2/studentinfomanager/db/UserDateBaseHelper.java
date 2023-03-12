package com.example.double2.studentinfomanager.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.double2.studentinfomanager.activity.User;

import java.util.ArrayList;

public class UserDateBaseHelper extends SQLiteOpenHelper{

    private SQLiteDatabase mSQLiteDatabase;

    public static final String CreateUserInfo = "create table user ("
            + "username text primary key, "
            + "password text)";

    public UserDateBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mSQLiteDatabase=getReadableDatabase();
    }


    public ArrayList<User> getAllData2(){

        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = mSQLiteDatabase.query("user",null,null,null,null,null,"username DESC");
        while(cursor.moveToNext()){
            String Username = cursor.getString(cursor.getColumnIndex("username"));
            String Password = cursor.getString(cursor.getColumnIndex("password"));

            list.add(new User(Username,Password));
        }
        cursor.close();
        return list;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateUserInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
