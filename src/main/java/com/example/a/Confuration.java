package com.example.a;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/5/13 下午7:18
 */
@Configuration
public class Confuration {
    @Bean
    @ConditionalOnBean(After.class)
    public Before getA() {
        return new Before();
    }
}
    