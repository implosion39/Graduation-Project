package com.admin.staff.db;

import com.admin.staff.bean.FeedBack;

import java.util.List;

public interface FeedBackDao {

    int insert(FeedBack feedBack);

    int update(FeedBack feedBack);

    int delete(int id);

    List<FeedBack> query(String workNum);
}
