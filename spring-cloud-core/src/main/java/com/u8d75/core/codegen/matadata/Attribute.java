package com.u8d75.core.codegen.matadata;

import com.u8d75.core.codegen.utils.NameConverter;
import com.u8d75.core.codegen.utils.SqlType2JavaType;
import lombok.Data;

import java.io.Serializable;

/**
 * database table attribute
 */
@Data
public class Attribute implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String name;
    private String type;
    private String remarks;
    private String targetType;
    private String wrapperType;

    public Attribute(String name, String type, String remarks) {
        this.targetType = type;
        if (this.targetType.contains("UNSIGNED")) {
            this.targetType = this.targetType.replace("UNSIGNED", "").trim();
        }
        if ("DATETIME".equals(this.targetType)) {
            this.targetType = "VARCHAR";
        }
        if ("BIT".equals(this.targetType)) {
            this.targetType = "INTEGER";
        }
        if ("INT".equals(this.targetType)) {
            this.targetType = "INTEGER";
        }
        this.name = NameConverter.toJavaCase(name);
        this.type = SqlType2JavaType.sqlType2JavaType(type);
        this.remarks = remarks;
        this.fileName = name;

        if ("long".equals(this.type)) {
            this.wrapperType = "Long";
        } else if ("byte".equals(this.type)) {
            this.wrapperType = "Byte";
        } else if ("int".equals(this.type)) {
            this.wrapperType = "Integer";
        } else if ("Date".equals(this.type)) {
            this.wrapperType = "String";
        } else {
            this.wrapperType = this.type;
        }
    }

}
