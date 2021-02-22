package com.example.entity;

import com.example.com.example.plugin.Tuomin;
import com.example.com.example.plugin.TuominStrategy;
import lombok.Data;

/**
 * @Author didi
 * @Date 2021/2/3
 */
@Data
public class Student {
    private int id;
    private String name;
    private int tid;
    //多个学生可以是同一个老师，即多对一
    private Teacher teacher;
}