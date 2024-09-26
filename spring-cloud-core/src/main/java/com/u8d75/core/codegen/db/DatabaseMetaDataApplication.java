package com.u8d75.core.codegen.db;

import com.u8d75.core.codegen.common.TemplateConfig;
import com.u8d75.core.codegen.matadata.Attribute;
import com.u8d75.core.codegen.matadata.TableInfo;
import com.u8d75.core.codegen.template.EntityTemplate;
import com.u8d75.core.codegen.template.TemplateUtils;
import com.u8d75.core.exception.BizException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * DatabaseMetaDataApplication
 */
public final class DatabaseMetaDataApplication {

    private String jdbc = "";
    private String user = "";
    private String password = "";

    private List<TableInfo> tableInfoList = new ArrayList<>();
    private DatabaseMetaData dbMetaData = null;
    private Connection connection = null;

    private TemplateConfig.Builder config = new TemplateConfig.Builder();

    private DatabaseMetaDataApplication(String jdbc, String user, String password) {
        this.jdbc = jdbc;
        this.user = user;
        this.password = password;
    }

    /**
     * create DatabaseMetaDataApplication instance
     *
     * @param jdbc     jdbc
     * @param user     user
     * @param password password
     * @return DatabaseMetaDataApplication
     */
    public static DatabaseMetaDataApplication create(String jdbc, String user, String password) {
        return new DatabaseMetaDataApplication(jdbc, user, password);
    }

    /**
     * template config
     *
     * @param consumer consumer
     * @return DatabaseMetaDataApplication
     */
    public DatabaseMetaDataApplication config(Consumer<TemplateConfig.Builder> consumer) {
        consumer.accept(this.config);
        return this;
    }

    /**
     * run generate code
     *
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public void run() throws SQLException, ClassNotFoundException {
        getDatabaseMetaData();
        getAllTableList();

        EntityTemplate entityTemplate = new EntityTemplate();
        entityTemplate.setPackageName(this.config.build().getBasePackageName());
        entityTemplate.setDomainPackageName(this.config.build().getBasePackageName() + "." + this.config.build().getDomainPackageName());
        entityTemplate.setDaoPackageName(this.config.build().getBasePackageName() + "." + this.config.build().getDaoPackageName());
        entityTemplate.setServicePackageName(this.config.build().getBasePackageName() + "." + this.config.build().getServicePackageName());
        entityTemplate.setControllerPackageName(this.config.build().getBasePackageName() + "." + this.config.build().getControllerPackageName());
        entityTemplate.setAuthor(this.config.build().getAuthor());
        entityTemplate.setTablePrefix(this.config.build().getTablePrefix());
        entityTemplate.setBaseFilePath(this.config.build().getBaseFilePath());

        TemplateUtils.createEntity(this.tableInfoList, entityTemplate);
        TemplateUtils.createMybatisXml(this.tableInfoList, entityTemplate);
        TemplateUtils.createDao(this.tableInfoList, entityTemplate);
        TemplateUtils.createServiceImpl(this.tableInfoList, entityTemplate);
        this.closeConnection();
    }

    /**
     * getDatabaseMetaData
     *
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public void getDatabaseMetaData() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("remarks", "true");
        props.setProperty("useInformationSchema", "true");
        this.connection = DriverManager.getConnection(jdbc, props);
        this.dbMetaData = this.connection.getMetaData();
    }

    /**
     * closeConnection
     *
     * @throws SQLException SQLException
     */
    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    /**
     * getAllTableList
     *
     * @return List<TableInfo>
     * @throws SQLException SQLException
     */
    public List<TableInfo> getAllTableList() throws SQLException {
        List<TableInfo> result = new ArrayList<>();
        String[] types = {"TABLE"};
        ResultSet rs = this.dbMetaData.getTables(this.connection.getCatalog(), null, this.config.build().getTableName(), types);
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String remarks = rs.getString("REMARKS");
            TableInfo tableInfo = new TableInfo(tableName, remarks, getTableColumns(tableName), getAllPrimaryKeys(tableName));
            result.add(tableInfo);
        }
        this.tableInfoList = result;
        return result;
    }

    /**
     * getTableColumns
     *
     * @param tn table name
     * @return List<Attribute>
     * @throws SQLException SQLException
     */
    public List<Attribute> getTableColumns(String tn) throws SQLException {
        List<Attribute> ret = new ArrayList<>();
        ResultSet rs = this.dbMetaData.getColumns(this.connection.getCatalog(), null, tn, "%");
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String columnName = rs.getString("COLUMN_NAME");
            String dataTypeName = rs.getString("TYPE_NAME");
            String remarks = rs.getString("REMARKS");
            if (!columnName.equals(columnName.toLowerCase())) {
                throw new BizException(tableName + "-" + columnName + "大小写错误！");
            }
            Attribute attribute = new Attribute(columnName, dataTypeName, remarks);
            ret.add(attribute);
        }
        return ret;
    }

    /**
     * getAllPrimaryKeys
     *
     * @param tableName tableName
     * @return primary key
     * @throws SQLException SQLException
     */
    public String getAllPrimaryKeys(String tableName) throws SQLException {
        ResultSet rs = this.dbMetaData.getPrimaryKeys(this.connection.getCatalog(), null, tableName);
        if (!(rs.next())) {
            return null;
        }
        return rs.getString("COLUMN_NAME");
    }

}
