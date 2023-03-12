package com.example.company;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class CompanyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="company.db";
    private static final int DB_VER=1;
    private SQLiteDatabase db;

    public CompanyDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VER);
        db = getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //建每日讯息表
        db.execSQL("CREATE TABLE NEWS(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TITLE TEXT," +
                "IMAGE_RESOURCE_ID INTEGER," +
                "CONTENT TEXT);");
        //建立登录表
        db.execSQL("CREATE TABLE  USER( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT);");
        //建立管理员登录表
        db.execSQL("CREATE TABLE  GUSER( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "gname TEXT," +
                "password TEXT);");
        db.execSQL("INSERT INTO guser (gname,password) VALUES('zzz','123')");
        //建立用户信息表
        db.execSQL("CREATE TABLE  YUANGONG( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "sex TEXT,"+
                "tel TEXT,"+
                "position TEXT,"+
                "TIME DATE);");

        //插入每日讯息
        insertNews(db,"通知1",R.drawable.news01,"aaaaaaaaaaaaaaaaaaaaaaaa");
        insertNews(db,"通知2",R.drawable.news02,"bbbbbbbbbbbbbbbbbbbbbbbbbb");
    }
    private static void insertNews(SQLiteDatabase db,String title,int resourceId,String content){
        ContentValues newsValues = new ContentValues();
        newsValues.put("TITLE",title);
        newsValues.put("IMAGE_RESOURCE_ID",resourceId);
        newsValues.put("CONTENT",content);

        long result = db.insert("NEWS",null,newsValues);
        //日志插入结果
        Log.d("sqlite","insert"+title+"_id"+result);
    }
//登录表的相关操作
    public void login_add(String name,String password){
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public void login_delete(String name,String password){
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void login_updata(String password){
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }

    //管理员登录表的操作
    public void glogin_add(String name,String password){
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public void glogin_delete(String name,String password){
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void glogin_updata(String password){
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }

    public ArrayList<User> getAllData2(){

        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("guser",null,null,null,null,null,"gname DESC");
        while(cursor.moveToNext()){
            String gname = cursor.getString(cursor.getColumnIndex("gname"));
            String password = cursor.getString(cursor.getColumnIndex("password"));

            list.add(new User(gname,password));
        }
        cursor.close();
        return list;
    }

    public ArrayList<User> getAllData(){

        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("user",null,null,null,null,null,"name DESC");
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(name,password));
        }
        cursor.close();
        return list;
    }

    //员工表的相关操作
    public void yuangong_add(String name,String sex,String tel,String position,String time){
        db.execSQL("INSERT INTO yuangong (name,sex,tel,position,time) VALUES(?,?,?,?,?)",new Object[]{name,sex,tel,position,time});
    }
    public void yuangong_delete(String name){
        db.execSQL("DELETE FROM yuangong WHERE name = ? ", new Object[]{name});
    }
    public void yuangong_updata(String name,String tel,String position){

        db.execSQL("update yuangong set tel=?,position=? where name=?", new Object[]{tel,position,name});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
