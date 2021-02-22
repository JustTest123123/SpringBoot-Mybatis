package com.example.com.example.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @Author didi
 * @Date 2021/2/21
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})}
)
@Component
public class SqlPrintPlugin implements Interceptor {
    private int MIN_SIZE = 0;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();

        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = invocation.proceed();
        } finally {
            try {
                long sqlCostTime = System.currentTimeMillis() - startTime;
                String sql = getSql(configuration, boundSql);
                formatSqlLog(mappedStatement.getSqlCommandType(), sqlId, sql, sqlCostTime, result);
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    private String getSql(Configuration configuration, BoundSql boundSql) {
        // 输入sql字符串空判断
        String sql = boundSql.getSql();
        if (StringUtils.isEmpty(sql)) {
            return "";
        }

        //美化sql
        sql = beautifySql(sql);

        //填充占位符, 目前基本不用mybatis存储过程调用,故此处不做考虑
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (!parameterMappings.isEmpty() && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = this.replacePlaceholder(sql, parameterObject);
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = replacePlaceholder(sql, obj);
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = replacePlaceholder(sql, obj);
                    }
                }
            }
        }
        return sql;
    }

    private String beautifySql(String sql) {
        return sql.replaceAll("[\\s\n ]+", " ");
    }

    private String replacePlaceholder(String sql, Object parameterObject) {
        String result;
        if (parameterObject instanceof String) {
            result = "'" + parameterObject.toString() + "'";
        } else if (parameterObject instanceof Date) {
            result = "'" + getDate2String((Date) parameterObject) + "'";
        } else {
            result = parameterObject.toString();
        }
        return sql.replaceFirst("\\?", result);
    }

    private String getDate2String(Date parameterObject) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parameterObject);
    }

    private void formatSqlLog(SqlCommandType sqlCommandType, String sqlId, String sql, long costTime, Object obj) {
        String log = String.format("DAO [%s]\n[%dms] ===> %s\n", sqlId, costTime, sql);

        if (sqlCommandType == SqlCommandType.UPDATE || sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.DELETE) {
            log += "Count ===> " + obj;
        }
        if (costTime > MIN_SIZE) {
            SqlPrintPlugin.log.warn(log);
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
    