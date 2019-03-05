package com.controller;

import com.dao.UserVoMapper;
import com.domain.vo.UserVo;
import com.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tangj
 * @date 2018/4/25 22:54
 */
@RestController
public class IndexController {

    @Autowired
    UserVoMapper userDao;

    @Autowired
    private IndexService indexService;

    @RequestMapping("/index")
    public UserVo index(@RequestParam(value="id" ,required =false ) int id){


        UserVo userVo = indexService.getinde(id);
        System.out.println(userVo.toString());
        return userVo;
    }
}
