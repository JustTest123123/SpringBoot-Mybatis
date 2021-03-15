package com.example.mapper;

import com.example.entity.Student;
import com.example.entity.Teacher;

import java.util.List;

/**
 * @Author didi
 * @Date 2021/2/3
 */
public interface TeacherMapper {
    Teacher getById(int id);
    Teacher getTeacher(int id);
    List<Teacher> getAllTeacher();
    List<Teacher> getAllTeacherWithLazy();
    Student findStudentById(int id);
}
    