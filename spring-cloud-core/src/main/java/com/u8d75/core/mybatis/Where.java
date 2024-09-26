package com.u8d75.core.mybatis;

import com.u8d75.core.exception.BizException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.u8d75.core.mybatis.SqlHelper.*;

/**
 * Where
 *
 * @param <T>
 */
public class Where<T> {

    List<Condition> conditions = new ArrayList<>();
    List<Condition> sorts = new ArrayList<>();

    /**
     * eq
     *
     * @param field field
     * @param value value
     * @return Where<T>
     */
    public Where<T> eq(String field, Object value) {
        conditions.add(new Condition(OperatorEnum.EQ, field, value));
        return this;
    }

    /**
     * neq
     *
     * @param field field
     * @param value value
     * @return Where<T>
     */
    public Where<T> neq(String field, Object value) {
        conditions.add(new Condition(OperatorEnum.NEQ, field, value));
        return this;
    }

    /**
     * in
     *
     * @param field field
     * @param value value
     * @return Where<T>
     */
    public Where<T> in(String field, List<?> value) {
        conditions.add(new Condition(OperatorEnum.IN, field, value));
        return this;
    }

    /**
     * nin
     *
     * @param field field
     * @param value value
     * @return Where<T>
     */
    public Where<T> nin(String field, List<?> value) {
        conditions.add(new Condition(OperatorEnum.NIN, field, value));
        return this;
    }

    /**
     * gt
     *
     * @param field field
     * @param value value
     * @return Where<T>
     */
    public Where<T> gt(String field, Object value) {
        conditions.add(new Condition(OperatorEnum.GT, field, value));
        return this;
    }

    /**
     * gte
     *
     * @param field field
     * @param value value
     * @return Where<T>
     */
    public Where<T> gte(String field, Object value) {
        conditions.add(new Condition(OperatorEnum.GTE, field, value));
        return this;
    }

    /**
     * lt
     *
     * @param field field
     * @param value value
     * @return Where<T>
     */
    public Where<T> lt(String field, Object value) {
        conditions.add(new Condition(OperatorEnum.LT, field, value));
        return this;
    }

    /**
     * lte
     *
     * @param field field
     * @param value value
     * @return Where<T>
     */
    public Where<T> lte(String field, Object value) {
        conditions.add(new Condition(OperatorEnum.LTE, field, value));
        return this;
    }

    /**
     * bt
     *
     * @param field  field
     * @param value  value
     * @param value1 value1
     * @return Where<T>
     */
    public Where<T> bt(String field, Object value, Object value1) {
        conditions.add(new Condition(OperatorEnum.BT, field, value, value1));
        return this;
    }

    /**
     * orderByAsc
     *
     * @param field field
     * @return Where<T>
     */
    public Where<T> orderByAsc(String field) {
        sorts.add(new Condition(OperatorEnum.ORDER_BY_ASC, field, "asc"));
        return this;
    }

    /**
     * orderByDesc
     *
     * @param field field
     * @return Where<T>
     */
    public Where<T> orderByDesc(String field) {
        sorts.add(new Condition(OperatorEnum.ORDER_BY_DESC, field, "desc"));
        return this;
    }

    /**
     * builderSqlAndSetParams
     *
     * @param params params
     * @return sql
     */
    public String builderSqlAndSetParams(Map<String, Object> params) {
        StringBuilder where = new StringBuilder();
        if (!conditions.isEmpty()) {
            for (Condition condition : conditions) {
                switch (condition.operator) {
                    case EQ:
                        where.append(String.format(WHERE_STATEMENT_EQ, condition.field, condition.field)).append(WHERE_AND);
                        params.put(condition.field, condition.value);
                        break;
                    case NEQ:
                        where.append(String.format(WHERE_STATEMENT_NEQ, condition.field, condition.field)).append(WHERE_AND);
                        params.put(condition.field, condition.value);
                        break;
                    case IN:
                        where.append(String.format(WHERE_STATEMENT_IN, condition.field, buildInSql(condition))).append(WHERE_AND);
                        params.put(condition.field, condition.value);
                        break;
                    case NIN:
                        where.append(String.format(WHERE_STATEMENT_NIN, condition.field, buildInSql(condition))).append(WHERE_AND);
                        params.put(condition.field, condition.value);
                        break;
                    case GT:
                        where.append(String.format(WHERE_STATEMENT_GT, condition.field, condition.field)).append(WHERE_AND);
                        params.put(condition.field, condition.value);
                        break;
                    case GTE:
                        where.append(String.format(WHERE_STATEMENT_GTE, condition.field, condition.field)).append(WHERE_AND);
                        params.put(condition.field, condition.value);
                        break;
                    case LT:
                        where.append(String.format(WHERE_STATEMENT_LT, condition.field, condition.field)).append(WHERE_AND);
                        params.put(condition.field, condition.value);
                        break;
                    case LTE:
                        where.append(String.format(WHERE_STATEMENT_LTE, condition.field, condition.field)).append(WHERE_AND);
                        params.put(condition.field, condition.value);
                        break;
                    case BT:
                        where.append(String.format(WHERE_STATEMENT_BT, condition.field, condition.field, condition.field)).append(WHERE_AND);
                        params.put(condition.field, List.of(condition.value, condition.value1));
                        break;
                    default:
                        throw new BizException("operator error");
                }
            }
            where.delete(where.length() - WHERE_LENGTH, where.length());
        }
        if (!sorts.isEmpty()) {
            where.append(SPACE);
            where.append(ORDER_BY);
            for (Condition condition : sorts) {
                switch (condition.operator) {
                    case ORDER_BY_ASC:
                        where.append(String.format(ORDER_BY_ASC, condition.field)).append(COMMA);
                        params.put(condition.field + ORDER_BY_PARAMS_EXTEND, condition.field);
                        break;
                    case ORDER_BY_DESC:
                        where.append(String.format(ORDER_BY_DESC, condition.field)).append(COMMA);
                        params.put(condition.field + ORDER_BY_PARAMS_EXTEND, condition.field);
                        break;
                    default:
                        throw new BizException("not support where operator error");
                }
            }
            where.deleteCharAt(where.length() - 1);
        }
        where.append(SPACE);
        return where.toString();
    }

    /**
     * buildInSql
     *
     * @param condition condition
     * @return in sql
     */
    public String buildInSql(Condition condition) {
        StringBuilder inSql = new StringBuilder();
        for (int i = 0; i < ((List) condition.value).size(); i++) {
            inSql.append(String.format(IN_STATEMENT, condition.field, i));
        }
        inSql.deleteCharAt(inSql.length() - 1);
        return inSql.toString();
    }

    /**
     * OperatorEnum
     */
    public enum OperatorEnum {
        EQ, // =
        NEQ, // !=
        IN, // in
        NIN, // not in
        GT, // >
        GTE, // >=
        LT, // <
        LTE, // <=
        BT, // between and
        ORDER_BY_ASC, // order by asc
        ORDER_BY_DESC; // orderByDesc
    }

    /**
     * Condition
     */
    @Data
    public class Condition {

        private static final String FIELD_REGEXP = "^[a-zA-Z][a-zA-Z0-9]*$";
        OperatorEnum operator;
        String field;
        Object value;
        Object value1; // for between

        public Condition(OperatorEnum operator, String field, Object value) {
            if (operator.equals(OperatorEnum.BT)) {
                throw new BizException("between must have two value" + field);
            }
            if (!Pattern.matches(FIELD_REGEXP, field)) {
                throw new BizException("illegal where field name:" + field);
            }
            this.operator = operator;
            this.field = camelCaseToSnakeCase(field);
            this.value = value;
        }

        public Condition(OperatorEnum operator, String field, Object value, Object value1) {
            if (!Pattern.matches(FIELD_REGEXP, field)) {
                throw new BizException("illegal where field name");
            }
            this.operator = operator;
            this.field = camelCaseToSnakeCase(field);
            this.value = value;
            this.value1 = value1;
        }

    }

}
