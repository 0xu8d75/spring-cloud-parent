package com.u8d75.core.codegen;

import com.u8d75.core.codegen.db.DatabaseMetaDataApplication;

import java.sql.SQLException;

/**
 * CodeGenerator
 */
public class CodeGenerator {

    /**
     * run this main method to generate code
     *
     * @param args args
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseMetaDataApplication.create("jdbc:mysql://127.0.0.1:3306/test", "root", "123456")
                .config(builder -> builder.baseFilePath("E:/code/")
                        .basePackageName("com.u8d75.order")
                        .domainPackageName("domain")
                        .daoPackageName("dao")
                        .servicePackageName("service")
                        .tablePrefix("")
                        .tableName("order")
                        .author("author u8d75"))
                .run();
    }
}
