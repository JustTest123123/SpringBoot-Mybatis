package com.dao;

import com.example.DemoApplication;
import com.example.entity.Student;
import com.example.mapper.StudentMapper;
import com.example.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class StudentTest {
    @Autowired
    private StudentMapper studentMapper;
    @Test
    public void testInsert() {
        System.out.println(studentMapper.getStudents2());
    }


}
