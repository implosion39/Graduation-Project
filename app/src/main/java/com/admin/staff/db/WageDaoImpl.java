package com.admin.staff.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.admin.staff.bean.User;
import com.admin.staff.bean.Wage;

import java.util.ArrayList;
import java.util.List;

/**
 * 工资表
 */
public class WageDaoImpl implements WageDao {

    private DBHelper dbHelper;

    public WageDaoImpl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    
    
    @Override
    public long insert(Wage wage) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("work_num", wage.getWork_num());
        values.put("name", wage.getName());
        values.put("late_num", wage.getLate_num());
        values.put("base_wages", wage.getBase_wages());
        values.put("perform_wages", wage.getPerform_wages());
        values.put("deduct_wages", wage.getDeduct_wages());
        values.put("actual", wage.getActual());
        //values.put("department", wage.getDepartment());
        long row = db.insert("tb_manage", null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        return row;
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        db.execSQL("Delete from tb_manage where id=" + id);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public int update(Wage wage) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("work_num", wage.getWork_num());
        values.put("name", wage.getName());
        values.put("late_num", wage.getLate_num());
        values.put("base_wages", wage.getBase_wages());
        values.put("perform_wages", wage.getPerform_wages());
        values.put("deduct_wages", wage.getDeduct_wages());
        values.put("actual", wage.getActual());
        //values.put("department", wage.getDepartment());
        return db.update("tb_manage", values, "id=?", new String[]{wage.getId() + ""});
    }

    @Override
    public List<Wage> selectById(String workNum) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql;
        if (workNum == null) {
            sql = "select * from tb_manage  order by id desc";
        } else {
            sql = "select * from tb_manage where  work_num='" + workNum + "'  order by id desc";
        }
        List<Wage> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Wage wage = new Wage();
                wage.setId(cursor.getInt(cursor.getColumnIndex("id")));
                wage.setWork_num(cursor.getString(cursor.getColumnIndex("work_num")));
                wage.setName(cursor.getString(cursor.getColumnIndex("name")));
                wage.setLate_num(cursor.getString(cursor.getColumnIndex("late_num")));
                wage.setBase_wages(cursor.getString(cursor.getColumnIndex("base_wages")));
                wage.setPerform_wages(cursor.getString(cursor.getColumnIndex("perform_wages")));
                wage.setDeduct_wages(cursor.getString(cursor.getColumnIndex("deduct_wages")));
                wage.setActual(cursor.getString(cursor.getColumnIndex("actual")));
                //wage.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
                list.add(wage);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    @Override
    public boolean isExistUser(String work_num) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from tb_manage where work_num = '" + work_num + "'";
        Cursor cursor = db.rawQuery(sql, null);
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }
}
