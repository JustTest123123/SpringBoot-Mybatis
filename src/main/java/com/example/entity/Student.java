package com.example.entity;

import lombok.Data;

/**
 * @Author didi
 * @Date 2021/2/3
 */
@Data
public class Student {
    private int id;
    private String name;
    //多个学生可以是同一个老师，即多对一
    private Teacher teacher;
}