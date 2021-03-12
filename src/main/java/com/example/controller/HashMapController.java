package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/8 下午10:35
 */
@RestController
public class HashMapController {
    HashMap<String ,String> map = new HashMap<>();
    @RequestMapping("/hashmap")
    public Object get() {
        String key = UUID.randomUUID().toString();
        return key;
    }


}
    