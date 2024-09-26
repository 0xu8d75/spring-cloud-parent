package com.u8d75.core.mybatis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SqlHelper
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlHelper {

    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SPACE = " ";
    public static final String BRACKET_LEFT = "(";
    public static final String BRACKET_RIGHT = ")";

    public static final String PK = "id";
    public static final String PARAMS = "params";
    public static final String DOMAIN = "domain";

    public static final String SELECT_STATEMENT = "SELECT %s FROM `%s` WHERE %s %s";
    public static final String SELECT_STATEMENT_COUNT = "count(1)";
    public static final String SELECT_STATEMENT_LIMIT = "LIMIT 1";
    public static final String INSERT_STATEMENT = "INSERT INTO `%s` (%s) VALUES %s";
    public static final String UPDATE_STATEMENT = "UPDATE `%s` SET %s WHERE %s";
    public static final String DELETE_STATEMENT = "DELETE FROM `%s` WHERE %s";

    public static final String WHERE = "WHERE";
    public static final String WHERE_AND = "AND ";
    public static final String WHERE_STATEMENT_EQ = "%s = #{%s} ";
    public static final String WHERE_STATEMENT_NEQ = "%s != #{%s} ";
    public static final String WHERE_STATEMENT_IN = "%s in (%s) ";
    public static final String WHERE_STATEMENT_NIN = "%s not in (%s) ";
    public static final String WHERE_STATEMENT_GT = "%s > #{%s} ";
    public static final String WHERE_STATEMENT_GTE = "%s >= #{%s} ";
    public static final String WHERE_STATEMENT_LT = "%s < #{%s} ";
    public static final String WHERE_STATEMENT_LTE = "%s <= #{%s} ";
    public static final String WHERE_STATEMENT_BT = "%s BETWEEN #{%s[0]} AND #{%s[1]} ";
    public static final String ORDER_BY_PARAMS_EXTEND = "_order_by";
    public static final String ORDER_BY = "ORDER BY ";
    public static final String ORDER_BY_DESC = "${%s" + ORDER_BY_PARAMS_EXTEND + "} DESC ";
    public static final String ORDER_BY_ASC = "${%s" + ORDER_BY_PARAMS_EXTEND + "} ASC ";

    public static final String VALUE_STATEMENT = "#{%s.%s},";
    public static final String VALUE_STATEMENT_LIST = "#{%s[%s].%s},";
    public static final String SET_STATEMENT = "%s = #{%s.%s},";
    public static final String IN_STATEMENT = "#{%s[%s]},";

    public static final boolean NO_WHERE = true;
    public static final int WHERE_LENGTH = 5;

    /**
     * camelCaseToSnakeCase
     *
     * @param camelCase camelCase
     * @return String
     */
    public static String camelCaseToSnakeCase(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                snakeCase.append('_');
            }
            snakeCase.append(Character.toLowerCase(c));
        }
        return snakeCase.toString();
    }
}
