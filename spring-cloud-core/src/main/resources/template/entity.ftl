package ${template.domainPackageName};

import lombok.Data;

import java.io.Serializable;
<#if template.hasBigDecimal>
import java.math.BigDecimal;
</#if>

/**
 * ${template.remarks}
 * ${template.author!}
 */
@Data
public class ${template.className} implements Serializable {
    private static final long serialVersionUID = 1L;
    <#list  template.variableList as column>
    private ${column.wrapperType} ${column.name}; // ${column.remarks}
    </#list>

}
