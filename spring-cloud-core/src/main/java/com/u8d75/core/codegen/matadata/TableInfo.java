package com.u8d75.core.codegen.matadata;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * TableInfo
 */
@Data
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String tableName;
    private String remarks;
    private String primaryKey;
    private String primaryVariableKey;

    private Boolean hasBigDecimal = false;

    private List<Attribute> attributeList;

    public TableInfo(String tableName, String remarks, List<Attribute> attributeList, String primaryKey) {
        this.tableName = tableName;
        this.remarks = remarks;
        this.attributeList = attributeList;
        this.primaryKey = primaryKey;

        for (Attribute attribute : attributeList) {
            if (attribute.getType().equalsIgnoreCase("BigDecimal")) {
                this.hasBigDecimal = true;
            }
        }
    }

}