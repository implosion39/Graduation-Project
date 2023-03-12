package com.admin.staff.db;

import android.content.ContentValues;

import com.admin.staff.bean.User;

import java.util.List;

public interface UserDao {
    /**
     * 插入
     *
     * @param user
     * @return 记录的id，失败为-1
     */
    public long insertUser(User user);

    /**
     * 删除
     *
     * @param id
     */
    public void delete(int id);

    /**
     * 更新
     *
     * @param user
     */
    public int update(User user);

    /**
     * 查询
     *
     * @return
     */
    public List<User> selectById(int id);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password);

    /**
     * 查询工号，用于检索
     * @param id
     * @return
     */
    public List<String> selectAllWorkIds(int id);

    public boolean isExistUser(String work_num);

}
