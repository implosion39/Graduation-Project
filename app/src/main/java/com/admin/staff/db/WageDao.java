package com.admin.staff.db;

import com.admin.staff.bean.Wage;

import java.util.List;

public interface WageDao {
    /**
     * 插入
     *
     * @param wage
     * @return 记录的id，失败为-1
     */
    long insert(Wage wage);

    /**
     * 删除
     *
     * @param id
     */
    void delete(int id);

    /**
     * 更新
     *
     * @param wage
     */
    int update(Wage wage);

    /**
     * 查询
     *
     * @return
     */
    List<Wage> selectById(String workNum);

    boolean isExistUser(String work_num);

}
