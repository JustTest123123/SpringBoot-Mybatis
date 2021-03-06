package com.dao;

import com.example.DemoApplication;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testcopyProperties() {
//        使用BeanUtils.copyProperties进行对象之间的属性赋值
        Employee ee1 = new Employee("A", 21, "it");
        Employee ee2 = new Employee("B", 23, "account");
        User user = new User();
        BeanUtils.copyProperties(ee1, user);
        System.out.println(user);
        System.out.println("-------------分割线--------------");
        List<User> output = new ArrayList<>();
        List<Employee> source = Arrays.asList(ee1, ee2);
        /*output = BeanUtil.convertList2List(source, User.class);
        for (User str : output) {
            System.out.println(str);
        }*/
    }


}

class User {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Employee {
    private String name;
    private Integer age;
    private String dept;

    public Employee(String name, Integer age, String dept) {
        this.name = name;
        this.age = age;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", dept='" + dept + '\'' +
                '}';
    }
}
