package com.admin.staff.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.admin.staff.bean.FeedBack;

import java.util.ArrayList;
import java.util.List;

public class FeedBackDaoImpl implements FeedBackDao {
    private DBHelper dbHelper;

    public FeedBackDaoImpl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public int insert(FeedBack feedBack) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("work_num", feedBack.getWork_num());
        values.put("name", feedBack.getName());
        values.put("content", feedBack.getContent());
        values.put("time", feedBack.getTime());
        values.put("state", feedBack.getState());
        db.insert("tb_back", null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        return 1;
    }

    @Override
    public int update(FeedBack feedBack) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("work_num", feedBack.getWork_num());
        values.put("name", feedBack.getName());
        values.put("content", feedBack.getContent());
        values.put("time", feedBack.getTime());
        values.put("state", feedBack.getState());
        return db.update("tb_back", values, "id=?", new String[]{feedBack.getId() + ""});
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        db.execSQL("Delete from tb_back where id=" + id);
        db.setTransactionSuccessful();
        db.endTransaction();
        return 1;
    }

    @Override
    public List<FeedBack> query(String workNum) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql;
        if (workNum == null) {
            sql = "select * from tb_back  order by id desc";
        } else {
            sql = "select * from tb_back where  work_num='" + workNum + "'  order by id desc";
        }
        List<FeedBack> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                FeedBack feedBack = new FeedBack();
                feedBack.setId(cursor.getInt(cursor.getColumnIndex("id")));
                feedBack.setWork_num(cursor.getString(cursor.getColumnIndex("work_num")));
                feedBack.setName(cursor.getString(cursor.getColumnIndex("name")));
                feedBack.setContent(cursor.getString(cursor.getColumnIndex("content")));
                feedBack.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                feedBack.setState(cursor.getInt(cursor.getColumnIndex("state")));
                list.add(feedBack);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
