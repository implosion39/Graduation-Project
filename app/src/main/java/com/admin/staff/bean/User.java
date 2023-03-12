package com.admin.staff.bean;

import java.io.Serializable;

/**
 * 员工表
 */
public class User implements Serializable {

    private int id;//主键
    private String work_num;//工号
    private String name;//姓名
    private String sex;//性别
    private String birthday;//生日
    private String work_years;//工龄
    private String post;//工号
    private String department;//工号
    private String phone;//电话
    private String education;//工号
    private String password;//密码
    private String type;// 管理员 /员工

    public User() {
    }

    public User(String work_num, String name, String sex, String birthday, String work_years, String post, String department, String phone, String education, String password, String type) {
        this.work_num = work_num;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.work_years = work_years;
        this.post = post;
        this.department = department;
        this.phone = phone;
        this.education = education;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWork_years() {
        return work_years;
    }

    public void setWork_years(String work_years) {
        this.work_years = work_years;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
