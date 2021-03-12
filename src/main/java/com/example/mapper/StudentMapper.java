package com.example.mapper;

import com.example.entity.Student;

import java.util.List;

/**
 * @Author didi
 * @Date 2021/2/3
 */
public interface StudentMapper {

    List<Student> getStudents2();

    Student getById(Integer id);
}
