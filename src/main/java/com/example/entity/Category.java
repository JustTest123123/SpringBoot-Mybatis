package com.example.entity;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/14 下午6:26
 */
@Data
public class Category {
    public static void main(String[] args) {
        String[] x = StringUtils.toStringArray(Arrays.asList("1,2,3,4", "2"));
        System.out.println(x);
    }
    private int cid;
    private String cname;
    private List<Book> books;
}