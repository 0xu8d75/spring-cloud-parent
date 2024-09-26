package com.u8d75.core.codegen.template;

import com.u8d75.core.codegen.matadata.TableInfo;
import com.u8d75.core.codegen.utils.FreemarkerUtil;
import com.u8d75.core.codegen.utils.NameConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TemplateUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateUtils {
    private static final String TEMPLATE = "template";
    private static FreemarkerUtil freemarkerUtil = new FreemarkerUtil();

    /**
     * createEntity
     *
     * @param retList        retList
     * @param entityTemplate entityTemplate
     */
    public static void createEntity(List<TableInfo> retList, EntityTemplate entityTemplate) {
        for (TableInfo tableInfo : retList) {
            Map<String, Object> map = new HashMap<>();
            String tablename = tableInfo.getTableName();
            tablename = StringUtils.removeStart(tablename, entityTemplate.getTablePrefix());
            String className = NameConverter.toJavaCase(tablename);
            entityTemplate.setClassName(StringUtils.capitalize(className));
            entityTemplate.setRemarks(tableInfo.getRemarks());
            entityTemplate.setHasBigDecimal(tableInfo.getHasBigDecimal());
            entityTemplate.setVariableList(tableInfo.getAttributeList());
            map.put(TEMPLATE, entityTemplate);
            String path = entityTemplate.getBaseFilePath() + entityTemplate.getDomainPackageName().replace(".", "/");
            freemarkerUtil.fprint("entity.ftl", map, path, StringUtils.capitalize(className).concat(".java"));
        }
    }

    /**
     * createMybatisXml
     *
     * @param retList        retList
     * @param entityTemplate entityTemplate
     */
    public static void createMybatisXml(List<TableInfo> retList, EntityTemplate entityTemplate) {
        for (TableInfo tableInfo : retList) {
            tableInfo.setPrimaryKey(tableInfo.getPrimaryKey());
            tableInfo.setPrimaryVariableKey(NameConverter.toJavaCase(tableInfo.getPrimaryKey()));
            Map<String, Object> map = new HashMap<>();
            String tablename = tableInfo.getTableName();
            tablename = StringUtils.removeStart(tablename, entityTemplate.getTablePrefix());
            String className = NameConverter.toJavaCase(tablename);
            entityTemplate.setClassName(StringUtils.capitalize(className));
            entityTemplate.setRemarks(tableInfo.getRemarks());

            entityTemplate.setVariableList(tableInfo.getAttributeList());
            map.put(TEMPLATE, entityTemplate);
            map.put("tableInfo", tableInfo);
            String path = entityTemplate.getBaseFilePath() + "mappers/";
            freemarkerUtil.fprint("mybatis.ftl", map, path, StringUtils.capitalize(className).concat("Mapper.xml"));
        }
    }

    /**
     * createDao
     *
     * @param retList        retList
     * @param entityTemplate entityTemplate
     */
    public static void createDao(List<TableInfo> retList, EntityTemplate entityTemplate) {
        for (TableInfo tableInfo : retList) {
            Map<String, Object> map = new HashMap<>();
            String tablename = tableInfo.getTableName();
            tablename = StringUtils.removeStart(tablename, entityTemplate.getTablePrefix());
            String className = NameConverter.toJavaCase(tablename);
            entityTemplate.setClassName(StringUtils.capitalize(className));
            entityTemplate.setRemarks(tableInfo.getRemarks());

            entityTemplate.setVariableList(tableInfo.getAttributeList());
            map.put(TEMPLATE, entityTemplate);
            String path = entityTemplate.getBaseFilePath() + entityTemplate.getDaoPackageName().replace(".", "/");
            freemarkerUtil.fprint("dao.ftl", map, path, StringUtils.capitalize(className).concat("Dao.java"));
        }
    }

    /**
     * createServiceImpl
     *
     * @param retList        retList
     * @param entityTemplate entityTemplate
     */
    public static void createServiceImpl(List<TableInfo> retList, EntityTemplate entityTemplate) {
        for (TableInfo tableInfo : retList) {
            Map<String, Object> map = new HashMap<>();
            String tablename = tableInfo.getTableName();
            tablename = StringUtils.removeStart(tablename, entityTemplate.getTablePrefix());
            String className = NameConverter.toJavaCase(tablename);
            entityTemplate.setClassName(StringUtils.capitalize(className));
            entityTemplate.setRemarks(tableInfo.getRemarks());

            entityTemplate.setVariableList(tableInfo.getAttributeList());
            map.put(TEMPLATE, entityTemplate);
            String path = entityTemplate.getBaseFilePath() + entityTemplate.getServicePackageName().replace(".", "/");
            freemarkerUtil.fprint("service_impl.ftl", map, path, StringUtils.capitalize(className) + "ServiceImpl".concat(".java"));
        }
    }

}