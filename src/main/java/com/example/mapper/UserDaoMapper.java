package com.example.mapper;

import com.example.entity.Book;
import com.example.entity.Category;

import java.util.List;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/14 下午6:28
 */
public interface UserDaoMapper {
    List<Category> findCategoryWithLazingload();
    Book findBookWithLazy(int id);
}

    