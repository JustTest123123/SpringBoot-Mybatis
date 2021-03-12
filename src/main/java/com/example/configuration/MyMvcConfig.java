package com.example.configuration;

import com.example.interceptor.InteceptorNoLogin;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/10 下午8:17
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InteceptorNoLogin()).addPathPatterns("/**");
    }
}
    