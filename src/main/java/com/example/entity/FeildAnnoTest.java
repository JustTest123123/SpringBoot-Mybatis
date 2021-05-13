package com.example.entity;

import com.example.controller.anno.LimitAnno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/19 下午4:41
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeildAnnoTest {
    @LimitAnno(value = "fiildName")
    String name;
    @LimitAnno(value = "1")
    int id;

}
    