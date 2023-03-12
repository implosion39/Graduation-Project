package com.admin.staff.bean;

public class FeedBack {
    private int id;
    private String work_num;
    private String name;
    private String content;
    private long time;
    private int state;//0 未处理

    public FeedBack() {
    }

    public FeedBack(String work_num, String name, String content, long time, int state) {
        this.work_num = work_num;
        this.name = name;
        this.content = content;
        this.time = time;
        this.state = state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWork_num() {
        return work_num;
    }

    public void setWork_num(String work_num) {
        this.work_num = work_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
