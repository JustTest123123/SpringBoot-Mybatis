package com.example.filter;

import com.example.configuration.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/11 上午12:07
 */
@Component
public class MyFilter implements Filter {
    @Autowired
    private A a;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter 前置");
        System.out.println(a.getB());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 处理中");
        // 执行servlet方法（如拦截请求，不执行Servlet，可不执行此方法）
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

        System.out.println("Filter 后置");
    }
}