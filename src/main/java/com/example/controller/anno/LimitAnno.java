package com.example.controller.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/19 下午4:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface LimitAnno {
    String value() default "LimitAnno";
}
    