package com.example.controller.aspect;

import com.example.controller.anno.LimitAnno;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/19 下午4:03
 */
@Component
@Aspect
public class LimitAnnoAspect {
    @Autowired
    private HttpServletResponse response;
    RateLimiter rateLimiter = RateLimiter.create(1);

    @Pointcut("@annotation(com.example.controller.anno.LimitAnno)")
    public void serviceLimit() {
    }

    @Around(value = "serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Boolean flag = rateLimiter.tryAcquire();
        Signature signature = joinPoint.getSignature();
        System.out.println("方法签名" + signature);
        MethodSignature methodSignature = (MethodSignature) signature;
        LimitAnno annotation = methodSignature.getMethod().getAnnotation(LimitAnno.class);
        System.out.println(annotation);
        System.out.println("annotation value()" + annotation.value());
        Object obj = null;
        try {
            if (flag) {
                obj = joinPoint.proceed();
            } else {
                output(response, "限流");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("flag=" + flag + ",obj=" + obj);
        return obj;
    }

    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }
}
    