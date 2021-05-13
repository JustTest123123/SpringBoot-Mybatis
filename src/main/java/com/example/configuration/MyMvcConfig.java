package com.example.configuration;

import com.example.interceptor.InteceptorNoLogin;
import org.springframework.beans.factory.annotation.Autowired;
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
//    采用注入的方式注入拦截器，这样拦截器就可以用spring上下文的相关资源
    @Autowired
    private InteceptorNoLogin inteceptorNoLogin;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(inteceptorNoLogin).addPathPatterns("/**");
    }
}
    