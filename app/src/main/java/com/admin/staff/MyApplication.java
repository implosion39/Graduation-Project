package com.admin.staff;

import android.app.Application;

import com.admin.staff.bean.User;
import com.admin.staff.bean.Wage;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.UserDao;
import com.admin.staff.db.UserDaoImpl;
import com.admin.staff.db.WageDao;
import com.admin.staff.db.WageDaoImpl;
import com.admin.staff.util.SpUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SpUtil.init(this);
        boolean isFirst = SpUtil.getBoolean("isFirst", true);
        if (isFirst) {
            SpUtil.put("isFirst", false);
            insertUser();
            insertWage();
        }
    }


    /**
     * 插入员工
     */
    private void insertUser() {
        //程序第一次启动，插入 管理员账号，账号admin 密码: passwd
        UserDao userDao = new UserDaoImpl(new DBHelper(this));
        userDao.insertUser(new User("admin", "管理员", "男", "1983/9/21", "6", "总监", "财务部", "152233", "硕士", "passwd", "管理员"));
        //公司员工
        userDao.insertUser(new User("10012", "陈顺天", "男", "1983/9/21", "6", "经理", "财务部", "13296369569", "硕士", "123456", "员工"));
        userDao.insertUser(new User("30164", "王礼堂", "男", "1994/10/10", "1", "普通员工", "财务部", "15585693620", "本科", "123456", "员工"));
        userDao.insertUser(new User("30096", "刘原", "男", "1994/12/12", "2", "普通员工", "销售部", "15896366595", "本科", "123456", "员工"));
        userDao.insertUser(new User("30085", "上官庆守", "女", "1992/7/18", "3", "普通员工", "物流部", "18569369656", "大专", "123456", "员工"));
        userDao.insertUser(new User("20039", "慕容初晴", "女", "1991/2/9", "2", "主管", "行政部", "15896365201", "本科", "123456", "员工"));
        userDao.insertUser(new User("20030", "楚天丰", "男", "1984/2/16", "5", "主管", "行政部", "18856963656", "硕士", "123456", "员工"));
        userDao.insertUser(new User("20046", "Smith", "男", "1992/2/16", "1", "普通员工", "技术部", "18669633652", "本科", "123456", "员工"));

    }

    /**
     * 插入工资员工本月基本情况
     */

    private void insertWage() {
        WageDao wageDao = new WageDaoImpl(new DBHelper(this));

        wageDao.insert(new Wage("10012" , "陈顺天" , "0" , "12000" , "1000" , "0" , "13000" ));
        wageDao.insert(new Wage("30164" , "王礼堂" , "3" , "8000" , "1200" , "300" , "5900" ));
        wageDao.insert(new Wage("30096" , "刘原" , "1" , "6000" , "3000" , "100" , "8900" ));
        wageDao.insert(new Wage("30085" , "上官庆守" , "0" , "4000" , "600" , "0" , "4600" ));
        wageDao.insert(new Wage("20039" , "慕容初晴" , "1" , "7000" , "600" , "100" , "7500" ));
        wageDao.insert(new Wage("20030" , "楚天丰" , "2" , "7000" , "800" , "200" , "7600" ));
        wageDao.insert(new Wage("20046" , "Smith" , "1" , "10000" , "1500" , "100" , "11400" ));

    }
}
