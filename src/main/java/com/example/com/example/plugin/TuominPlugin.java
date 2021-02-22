package com.example.com.example.plugin;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @Author didi
 * @Date 2021/2/21
 */
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class))
@Component
public class TuominPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> res = (List<Object>) invocation.proceed();
        res.forEach(this::tuomin);
        return res;
    }

    private void tuomin(Object source) {
        Class<?> sourceClass = source.getClass();
        MetaObject metaObject = SystemMetaObject.forObject(source);
        Arrays.stream(sourceClass.getDeclaredFields())
                .filter(s -> s.isAnnotationPresent(Tuomin.class))
                .forEach(s -> doTuomin(metaObject, s));
    }

    private void doTuomin(MetaObject metaObject, Field field) {
        String name = field.getName();
        Object value = metaObject.getValue(name);
        if(field.getType() == String.class && value != null) {
            Tuomin annotation = field.getAnnotation(Tuomin.class);
            TuominStrategy stargegy = annotation.stargegy();
            Desenitize desenitize = stargegy.getDesenitize();
            String apply = desenitize.apply((String) value);
            metaObject.setValue(name,apply);
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
    