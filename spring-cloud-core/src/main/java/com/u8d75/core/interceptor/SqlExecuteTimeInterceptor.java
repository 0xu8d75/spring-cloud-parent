package com.u8d75.core.interceptor;

import com.u8d75.core.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * SqlExecuteTimeInterceptor
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
@Slf4j
public class SqlExecuteTimeInterceptor implements Interceptor {

    public static final Long PRINT_THRESHOLD_MS = 0L;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        String path = "";
        BoundSql boundSql = null;
        Configuration configuration = null;
        try {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            boundSql = statementHandler.getBoundSql();
            DefaultParameterHandler parameterHandler = (DefaultParameterHandler) statementHandler.getParameterHandler();
            path = ((MappedStatement) BeanUtils.getPropertyValue(parameterHandler, "mappedStatement")).getId();
            configuration = (Configuration) BeanUtils.getPropertyValue(parameterHandler, "configuration");
        } catch (Exception e) {
            log.error("SqlExecuteTimeInterceptor error", e);
        }
        Object result = null;
        try {
            result = invocation.proceed();
        } finally {
            try {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= PRINT_THRESHOLD_MS && LoggerFactory.getLogger(path).isInfoEnabled()) {
                    LoggerFactory.getLogger(path).info("[{}ms][{}] => {}", elapsedTime, formatResult(result), formatSql(boundSql, configuration).concat(";"));
                }
            } catch (Exception e) {
                log.error("SqlExecuteTimeInterceptor error", e);
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // empty
    }

    /**
     * formatResult
     *
     * @param result result
     * @return Integer
     */
    private Integer formatResult(Object result) {
        int unwarpResult = 0;
        if (result instanceof Integer r) {
            unwarpResult = r;
        } else if (result instanceof Collection<?> l) {
            unwarpResult = l.size();
        }
        return unwarpResult;
    }

    /**
     * formatSql
     *
     * @param boundSql      boundSql
     * @param configuration configuration
     * @return String
     */
    private String formatSql(BoundSql boundSql, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        if (!parameterMappings.isEmpty()) {
            for (ParameterMapping parameterMapping : parameterMappings) {
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value = getValue(parameterMapping, parameterObject, boundSql, configuration);
                    sql = sql.replaceFirst("\\?", (value instanceof String) ? ("'" + value + "'") : value + "");
                }
            }
        }
        return sql;
    }

    /**
     * getValue
     *
     * @param parameterMapping parameterMapping
     * @param parameterObject  parameterObject
     * @param boundSql         boundSql
     * @param configuration    configuration
     * @return Object
     */
    private Object getValue(ParameterMapping parameterMapping, Object parameterObject, BoundSql boundSql, Configuration configuration) {
        String propertyName = parameterMapping.getProperty();
        if (boundSql.hasAdditionalParameter(propertyName)) {
            return boundSql.getAdditionalParameter(propertyName);
        } else if (parameterObject == null) {
            return null;
        } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
            return parameterObject;
        } else {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            return metaObject.getValue(propertyName);
        }
    }

}