package com.example.controller.lister;

import com.example.entity.User;
import com.example.listenner.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/30 下午9:01
 */
@RestController
public class Listener {
    @Autowired
    private ApplicationContext applicationContext;
    @RequestMapping("/listener")
    public String get() {
        User user = new User();
        user.setId(12);
        user.setPassWord("d");
        applicationContext.publishEvent(new TestEvent(this,user));
        return "";
    }

}
    