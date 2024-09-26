package com.u8d75.core.codegen.utils;

import java.io.Serializable;

/**
 * SqlType2JavaType
 */
public class SqlType2JavaType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * sqlType2JavaType
     *
     * @param sqlType sqlType
     * @return String
     */
    public static String sqlType2JavaType(String sqlType) {
        sqlType = sqlType.replace("UNSIGNED", "").trim();
        if (sqlType.equalsIgnoreCase("bit")) {
            return "int";
        }
        if (sqlType.equalsIgnoreCase("tinyint")) {
            return "int";
        }
        if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        }
        if ((sqlType.equalsIgnoreCase("integer")) || (sqlType.equalsIgnoreCase("int"))) {
            return "int";
        }
        if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        }
        if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        }
        if ((sqlType.equalsIgnoreCase("double")) || (sqlType.equalsIgnoreCase("numeric")) || (sqlType.equalsIgnoreCase("real")) || (sqlType.equalsIgnoreCase("money")) || (sqlType.equalsIgnoreCase("smallmoney"))) {
            return "double";
        }
        if ((sqlType.equalsIgnoreCase("decimal"))) {
            return "BigDecimal";
        }
        if ((sqlType.equalsIgnoreCase("longtext")) || (sqlType.equalsIgnoreCase("varchar")) || (sqlType.equalsIgnoreCase("char")) || (sqlType.equalsIgnoreCase("nvarchar")) || (sqlType.equalsIgnoreCase("nchar")) || (sqlType.equalsIgnoreCase("text"))) {
            return "String";
        }
        if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        }
        if (sqlType.equalsIgnoreCase("timestamp")) {
            return "Date";
        }
        return "";
    }
}