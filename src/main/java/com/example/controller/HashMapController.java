package com.example.controller;

import com.example.controller.anno.LimitAnno;
import com.example.entity.Book;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/8 下午10:35
 */
@RestController
@Import(Book.class)
public class HashMapController {
    @LimitAnno
    HashMap<String, String> map = new HashMap<>();

    @RequestMapping("/hashmap")
    public Object get() {
        map.get("");
        String key = UUID.randomUUID().toString();
        return key;
    }

    @RequestMapping("/hashmap404")
    public void get1(HttpServletResponse response) throws IOException {
        response.setHeader("1","3");
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }


}
    