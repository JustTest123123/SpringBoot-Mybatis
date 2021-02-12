package com.dao;

import com.example.DemoApplication;
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
public class UserDaoTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testInsert() {

    }
    @Test
    public void testSelect() {
        System.out.println(userMapper.Sel(1));
    }
    @Test
    public void testUpdate() {
        Map<String,String> param = new HashMap<>();
        param.put("userName","abc");
        param.put("id","1");
        userMapper.update(param);

    }

}
