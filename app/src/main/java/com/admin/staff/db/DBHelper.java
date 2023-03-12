package com.admin.staff.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库工具类
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "my_db";
    private final static int DB_VERSION = 2;


    public DBHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //员工表
        //工号 姓名 性别 生日 工龄 职务 部门 联系电话 学历，登录密码，类型
        String sql_user = "create table tb_user(" +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "work_num CHAR(50)," +
                "name CHAR(20)," +
                "sex CHAR(20)," +
                "birthday CHAR(50)," +
                "work_years INT," +
                "post CHAR(50)," +
                "department CHAR(50)," +
                "phone CHAR(50)," +
                "education CHAR(50)," +
                "password CHAR(50)," +
                "type CHAR(50) )";
        db.execSQL(sql_user);

        //工号 姓名 迟到次数 基本工资 绩效奖励 扣除工资 实发工资
        String sql_manage = "create table tb_manage (" +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "work_num CHAR(50)," +
                "name CHAR(20)," +
                "late_num CHAR(20)," +
                "base_wages  CHAR(20)," +
                "perform_wages  CHAR(50)," +
                "deduct_wages CHAR(50),"+
                "actual  CHAR(50))" ;
        db.execSQL(sql_manage);

        //反馈表
        String sql_back = "create table tb_back(" +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "work_num CHAR(50)," +
                "name CHAR(20)," +
                "content CHAR(20)," +
                "time BIGINT," +
                "state  INT )" ;
        db.execSQL(sql_back);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //用户表
        db.execSQL("DROP TABLE IF EXISTS tb_user");
        //员工表
        String sql_user = "create table tb_user(" +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "work_num CHAR(50)," +
                "name CHAR(20)," +
                "sex CHAR(20)," +
                "birthday CHAR(50)," +
                "work_years INT," +
                "post CHAR(50)," +
                "department CHAR(50)," +
                "phone CHAR(50)," +
                "education CHAR(50)," +
                "password CHAR(50)," +
                "type CHAR(50) )";
        db.execSQL(sql_user);

        //工作信息表
        String sql_manage = "create table tb_manage(" +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "work_num CHAR(50)," +
                "name CHAR(20)," +
                "late_num CHAR(20)," +
                "base_wages  CHAR(20)," +
                "perform_wages  CHAR(50)," +
                "deduct_wages CHAR(50),"+
                "actual  CHAR(50))" ;
        db.execSQL(sql_manage);

        //反馈表
        String sql_back = "create table tb_back(" +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "work_num CHAR(50)," +
                "name CHAR(20)," +
                "content CHAR(20)," +
                "time BIGINT," +
                "state  INT )" ;
        db.execSQL(sql_back);

    }

}
