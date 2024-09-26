package com.u8d75.core.mybatis;

import com.u8d75.core.exception.BizException;
import com.u8d75.core.utils.BeanUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.u8d75.core.mybatis.SqlHelper.*;

@Log4j2
public class BaseSqlProvider {

    /**
     * findById
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String findById(Map<String, Object> params, ProviderContext context) {
        params.put(PARAMS, new Where<>().eq(PK, params.get(PK)));
        return buildSql(params, context, SqlTypeEnum.SELECT, true, false, false);
    }

    /**
     * findByIds
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String findByIds(Map<String, Object> params, ProviderContext context) {
        params.put(PARAMS, new Where<>().in(PK, (List) params.get(PK)));
        return buildSql(params, context, SqlTypeEnum.SELECT, false, false, false);
    }

    /**
     * findByParams
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String findByParams(Map<String, Object> params, ProviderContext context) {
        return buildSql(params, context, SqlTypeEnum.SELECT, true, false, false);
    }

    /**
     * findListByParams
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String findListByParams(Map<String, Object> params, ProviderContext context) {
        return buildSql(params, context, SqlTypeEnum.SELECT, false, false, false);
    }

    /**
     * countByParams
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String countByParams(Map<String, Object> params, ProviderContext context) {
        return buildSql(params, context, SqlTypeEnum.SELECT, false, true, false);
    }

    /**
     * deleteById
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String deleteById(Map<String, Object> params, ProviderContext context) {
        params.put(PARAMS, new Where<>().eq(PK, params.get(PK)));
        return buildSql(params, context, SqlTypeEnum.DELETE, false, false, false);
    }

    /**
     * updateById
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String updateById(Map<String, Object> params, ProviderContext context) {
        params.put(PARAMS, new Where<>().eq(PK, BeanUtils.getPropertyValue(params.get(DOMAIN), PK)));
        return buildSql(params, context, SqlTypeEnum.UPDATE, false, false, false);
    }

    /**
     * updateByIdSelective
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String updateByIdSelective(Map<String, Object> params, ProviderContext context) {
        params.put(PARAMS, new Where<>().eq(PK, BeanUtils.getPropertyValue(params.get(DOMAIN), PK)));
        return buildSql(params, context, SqlTypeEnum.UPDATE, false, false, true);
    }

    /**
     * insert
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String insert(Map<String, Object> params, ProviderContext context) {
        return buildSql(params, context, SqlTypeEnum.INSERT, false, false, false);
    }

    /**
     * insertBatch
     *
     * @param params  params
     * @param context context
     * @return sql
     */
    public String insertBatch(Map<String, Object> params, ProviderContext context) {
        return buildSql(params, context, SqlTypeEnum.INSERT, false, false, false);
    }

    /**
     * buildSql
     *
     * @param params      params
     * @param context     context
     * @param sqlTypeEnum sqlTypeEnum
     * @param limit       limit
     * @param count       count
     * @param selective   selective
     * @return sql
     */
    public String buildSql(Map<String, Object> params, ProviderContext context, SqlTypeEnum sqlTypeEnum, boolean limit, boolean count, boolean selective) {
        TableInfo tableInfo = getTableInfo(context);
        String sql = "";

        switch (sqlTypeEnum) {
            case SELECT:
                sql = String.format(SELECT_STATEMENT,
                        (count ? SELECT_STATEMENT_COUNT : columnBuilder(tableInfo, false, null)),
                        tableInfo.getTableName(),
                        ((Where<?>) params.get(PARAMS)).builderSqlAndSetParams(params),
                        limit ? SELECT_STATEMENT_LIMIT : "");
                break;
            case INSERT:
                sql = String.format(INSERT_STATEMENT,
                        tableInfo.getTableName(),
                        columnBuilder(tableInfo, selective, params.get(DOMAIN)),
                        valueBuilder(tableInfo, selective, params.get(DOMAIN)));
                break;
            case UPDATE:
                sql = String.format(UPDATE_STATEMENT,
                        tableInfo.getTableName(),
                        setBuilder(tableInfo, selective, params.get(DOMAIN)),
                        ((Where<?>) params.get(PARAMS)).builderSqlAndSetParams(params));
                break;
            case DELETE:
                sql = String.format(DELETE_STATEMENT,
                        tableInfo.getTableName(),
                        ((Where<?>) params.get(PARAMS)).builderSqlAndSetParams(params));
                break;
            default:
                throw new BizException("not support operator error");
        }
        if (NO_WHERE && sql.trim().endsWith(WHERE)) {
            sql = sql.replaceAll(WHERE, "");
        }
        log.info(sql);
        return sql;
    }

    /**
     * columnBuilder
     *
     * @param tableInfo tableInfo
     * @param selective selective
     * @param domain    domain
     * @return column sql like id,name,age
     */
    public String columnBuilder(TableInfo tableInfo, boolean selective, Object domain) {
        StringBuilder columnSql = new StringBuilder();
        for (ColumnInfo columnInfo : tableInfo.getColumns()) {
            if (checkSelective(selective, domain, columnInfo)) {
                columnSql.append(columnInfo.getColumnName()).append(COMMA);
            }
        }
        columnSql.deleteCharAt(columnSql.length() - 1);
        return columnSql.toString();
    }

    /**
     * valueBuilder
     *
     * @param tableInfo tableInfo
     * @param selective selective
     * @param domain    domain
     * @return value sql like (#{domain.id},#{domain.name},#{domain.age}) or (#{domain[0].id},#{domain[0]}.name,#{domain[0].age})
     */
    public String valueBuilder(TableInfo tableInfo, boolean selective, Object domain) {
        StringBuilder columnValueSql = new StringBuilder();
        if (domain instanceof Collection<?> d) {
            for (int i = 0; i < d.size(); i++) {
                columnValueSql.append(BRACKET_LEFT);
                for (ColumnInfo columnInfo : tableInfo.getColumns()) {
                    columnValueSql.append(String.format(VALUE_STATEMENT_LIST, DOMAIN, i, columnInfo.getClassName()));
                }
                columnValueSql.deleteCharAt(columnValueSql.length() - 1);
                columnValueSql.append(BRACKET_RIGHT);
                if (i != d.size() - 1) {
                    columnValueSql.append(COMMA);
                }
            }
        } else {
            columnValueSql.append(BRACKET_LEFT);
            for (ColumnInfo columnInfo : tableInfo.getColumns()) {
                if (checkSelective(selective, domain, columnInfo)) {
                    columnValueSql.append(String.format(VALUE_STATEMENT, DOMAIN, columnInfo.getClassName()));
                }
            }
            columnValueSql.deleteCharAt(columnValueSql.length() - 1);
            columnValueSql.append(BRACKET_RIGHT);
        }
        return columnValueSql.toString();
    }

    /**
     * setBuilder
     *
     * @param tableInfo tableInfo
     * @param selective selective
     * @param domain    domain
     * @return set sql like id=#{domain.id},name=#{domain.name},age=#{domain.age}
     */
    public String setBuilder(TableInfo tableInfo, boolean selective, Object domain) {
        StringBuilder columnEqValueSql = new StringBuilder();
        for (ColumnInfo columnInfo : tableInfo.getColumns()) {
            if (checkSelective(selective, domain, columnInfo)) {
                columnEqValueSql.append(String.format(SET_STATEMENT, columnInfo.getColumnName(), DOMAIN, columnInfo.getClassName()));
            }
        }
        columnEqValueSql.deleteCharAt(columnEqValueSql.length() - 1);
        return columnEqValueSql.toString();
    }

    /**
     * getClassType
     *
     * @param context context
     * @return Class
     */
    public Class<?> getClassType(ProviderContext context) {
        return (Class) ((ParameterizedType) context.getMapperType().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    /**
     * checkSelective
     *
     * @param selective  selective
     * @param domain     domain
     * @param columnInfo columnInfo
     * @return is selective column
     */
    public boolean checkSelective(boolean selective, Object domain, ColumnInfo columnInfo) {
        return !selective || (selective && null != domain && (!(domain instanceof Collection<?>)) && null != BeanUtils.getPropertyValue(domain, columnInfo.getColumnName()));
    }

    /**
     * getTableInfo
     *
     * @param context context
     * @return TableInfo
     */
    public TableInfo getTableInfo(ProviderContext context) {
        TableInfo tableInfo = new TableInfo();
        Class<?> entityClass = getClassType(context);
        tableInfo.setTableName(camelCaseToSnakeCase(entityClass.getSimpleName()));
        for (Field field : entityClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            ColumnInfo column = new ColumnInfo();
            column.setClassName(field.getName());
            column.setColumnName(camelCaseToSnakeCase(field.getName()));
            column.setColumnClassType(field.getType());
            tableInfo.addColumn(column);
        }
        return tableInfo;
    }

    /**
     * SqlTypeEnum
     */
    public enum SqlTypeEnum {
        SELECT, INSERT, UPDATE, DELETE;
    }

    /**
     * TableInfo
     */
    @Data
    public class TableInfo {

        String tableName = "";
        List<ColumnInfo> columns = new ArrayList<>();

        /**
         * addColumn
         *
         * @param column column
         * @return List<ColumnInfo>
         */
        public List<ColumnInfo> addColumn(ColumnInfo column) {
            columns.add(column);
            return columns;
        }
    }

    /**
     * ColumnInfo
     */
    @Data
    public class ColumnInfo {

        String columnName = "";
        String className = "";
        Object value = "";
        Class<?> columnClassType;

    }

}
