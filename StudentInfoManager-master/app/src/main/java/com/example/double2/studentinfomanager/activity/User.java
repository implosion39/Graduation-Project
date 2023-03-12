package com.example.double2.studentinfomanager.activity;

public class User {
    private String name;            //用户名
    private String password;        //密码
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
}
