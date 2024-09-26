package com.u8d75.core.interceptor;

import com.u8d75.core.utils.BeanUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.MDC;

import java.sql.Connection;

/**
 * SqlAnnotationInterceptor
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class SqlAnnotationInterceptor implements Interceptor {

    private static final int DAO_DOT_LENGTH = 4;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) statementHandler.getParameterHandler();
        String mapperId = ((MappedStatement) BeanUtils.getPropertyValue(defaultParameterHandler, "mappedStatement")).getId();

        String sql = boundSql.getSql();
        String annotation = String.format("/* [%s][%s][%s] */", MDC.get("userId"), MDC.get("uri"), mapperId.substring(mapperId.indexOf("dao.") + DAO_DOT_LENGTH));
        sql = sql + annotation;
        BeanUtils.setPropertyValue(boundSql, "sql", sql);
        return invocation.proceed();
    }

}
