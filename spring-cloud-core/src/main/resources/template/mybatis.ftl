<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${template.daoPackageName}.${template.className}Dao">
   <resultMap id="BaseResultMap" type="${template.domainPackageName}.${template.className}">
   <#list template.variableList as column>
      <result column="${column.fileName}" property="${column.name}" jdbcType="${column.targetType}"/>
   </#list>
   </resultMap>

   <sql id="Base_Column_List">
      <#list template.variableList as column><#if column_has_next>${column.fileName},<#else>${column.fileName}</#if></#list>
   </sql>

</mapper>
