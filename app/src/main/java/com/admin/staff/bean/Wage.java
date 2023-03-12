package com.admin.staff.bean;

import java.io.Serializable;

/**
 * 基本情况
 * 员工号 姓名 迟到次数 基本工资 绩效奖励 扣除工资 实发工资
 */
public class Wage implements Serializable {
    private int id;
    private String work_num;
    private String name;
    private String late_num;
    private String base_wages;
    private String perform_wages;
    private String deduct_wages;
    private String actual;
    //private String department;

    public Wage() {
    }

    public Wage(String work_num, String name, String late_num, String base_wages, String perform_wages, String deduct_wages, String actual) {
        this.work_num = work_num;
        this.name = name;
        this.late_num = late_num;
        this.base_wages = base_wages;
        this.perform_wages = perform_wages;
        this.deduct_wages = deduct_wages;
        this.actual = actual;
        //this.department = department;
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

    public String getLate_num() {
        return late_num;
    }

    public void setLate_num(String late_num) {
        this.late_num = late_num;
    }

    public String getBase_wages() {
        return base_wages;
    }

    public void setBase_wages(String base_wages) {
        this.base_wages = base_wages;
    }

    public String getPerform_wages() {
        return perform_wages;
    }

    public void setPerform_wages(String perform_wages) {
        this.perform_wages = perform_wages;
    }

    public String getDeduct_wages() {
        return deduct_wages;
    }

    public void setDeduct_wages(String deduct_wages) {
        this.deduct_wages = deduct_wages;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

   /* public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }*/
}
