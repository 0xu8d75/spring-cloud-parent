package com.u8d75.core.codegen.template;

import com.u8d75.core.codegen.matadata.Attribute;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * EntityTemplate
 */
@Data
public class EntityTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    private String packageName;
    private String className;
    private List<Attribute> variableList;
    private String remarks;
    private String author;
    private String tablePrefix = "";
    private String baseFilePath = "";

    private String daoPackageName;
    private String servicePackageName;
    private String controllerPackageName;
    private String domainPackageName;

    private Boolean hasBigDecimal = false;

}
