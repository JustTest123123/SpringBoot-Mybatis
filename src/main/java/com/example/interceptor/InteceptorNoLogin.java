package com.example.interceptor;

import com.example.configuration.A;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/10 下午8:12
 */
@Slf4j
@Component
public class InteceptorNoLogin implements HandlerInterceptor {
//    缺点是只能对controller请求进行拦截，对其他的一些比如直接访问静态资源的请求则没办法进行拦截处理。

//    这里会报错，因为拦截器会在springcontext前加载 ,因此加入拦截器之前使用注入的方式加入，就不会报错
    @Autowired
    private A a;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("test");
        System.out.println("前置拦截器");
        System.out.println(a.getB());
        return true;
    }
    @PostConstruct
    public void post() {
        System.out.println("PostConstruct");
    }
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("后置拦截器");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        System.out.println("拦截器完成");
    }

}
    