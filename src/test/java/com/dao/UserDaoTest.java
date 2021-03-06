package com.dao;

import com.example.DemoApplication;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserDaoTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            userMapper.insertAll(  new User("1","21","22"));
        }
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
