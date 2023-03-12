package com.admin.staff.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.admin.staff.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工操作类
 */
public class UserDaoImpl implements UserDao {
    private DBHelper dbHelper;

    public UserDaoImpl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    @Override
    public long insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("work_num", user.getWork_num());
        values.put("name", user.getName());
        values.put("sex", user.getSex());
        values.put("birthday", user.getBirthday());
        values.put("work_years", user.getWork_years());
        values.put("post", user.getPost());
        values.put("department", user.getDepartment());
        values.put("phone", user.getPhone());
        values.put("education", user.getEducation());
        values.put("password", user.getPassword());
        values.put("type", user.getType());
        long row = db.insert("tb_user", null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        return row;
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        db.execSQL("Delete from tb_user where id=" + id);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public int update(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("work_num", user.getWork_num());
        values.put("name", user.getName());
        values.put("sex", user.getSex());
        values.put("birthday", user.getBirthday());
        values.put("work_years", user.getWork_years());
        values.put("post", user.getPost());
        values.put("department", user.getDepartment());
        values.put("phone", user.getPhone());
        values.put("education", user.getEducation());
        values.put("password", user.getPassword());
        values.put("type", user.getType());
        return db.update("tb_user", values, "id=?", new String[]{user.getId() + ""});
    }


    @Override
    public List<User> selectById(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql;
        if (id <= 0) {
            sql = "select * from tb_user where type !='管理员'  order by id desc";
        } else {
            sql = "select * from tb_user where  id=" + id + "  order by id desc";
        }
        List<User> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setWork_num(cursor.getString(cursor.getColumnIndex("work_num")));
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                user.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
                user.setWork_years(cursor.getString(cursor.getColumnIndex("work_years")));
                user.setPost(cursor.getString(cursor.getColumnIndex("post")));
                user.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
                user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                user.setEducation(cursor.getString(cursor.getColumnIndex("education")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setType(cursor.getString(cursor.getColumnIndex("type")));
                list.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    @Override
    public User login(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from tb_user where work_num = '" + username + "' and password = '" + password + "'";
        Cursor cursor = db.rawQuery(sql, null);
        boolean result = cursor.moveToFirst();
        if (result) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setWork_num(cursor.getString(cursor.getColumnIndex("work_num")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            user.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
            user.setWork_years(cursor.getString(cursor.getColumnIndex("work_years")));
            user.setPost(cursor.getString(cursor.getColumnIndex("post")));
            user.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            user.setEducation(cursor.getString(cursor.getColumnIndex("education")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setType(cursor.getString(cursor.getColumnIndex("type")));
            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }
    }

    @Override
    public List<String> selectAllWorkIds(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql;
        if (id <= 0) {
            sql = "select work_num from tb_user where type !='管理员'  order by id desc";
        } else {
            sql = "select work_num from tb_user where  id=" + id + "  order by id desc";
        }
        List<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("work_num")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    @Override
    public boolean isExistUser(String work_num) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from tb_user where work_num = '" + work_num + "'";
        Cursor cursor = db.rawQuery(sql, null);
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }
}
