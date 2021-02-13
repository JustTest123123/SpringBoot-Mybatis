package com.dao;

import com.example.DemoApplication;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class TeacherTest {
    @Autowired
    private TeacherMapper teacherMapper;
    @Test
    public void testGetById() {
        System.out.println(teacherMapper.getById(1));
        System.out.println(teacherMapper.getById(2));
    }


}
