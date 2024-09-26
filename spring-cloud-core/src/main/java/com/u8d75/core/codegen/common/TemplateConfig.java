package com.u8d75.core.codegen.common;

import lombok.Data;

/**
 * TemplateConfig
 */
@Data
public class TemplateConfig {
    private String author = "back-end team";
    private String baseFilePath = "/";
    private String basePackageName = "com.u8d75.user";
    private String domainPackageName = "domain";
    private String daoPackageName = "dao";
    private String servicePackageName = "service";
    private String controllerPackageName = "controller";
    private String tablePrefix = "";
    private String tableName = "%";

    /**
     * Builder
     */
    public static class Builder {
        private final TemplateConfig config;

        public Builder() {
            this.config = new TemplateConfig();
        }

        /**
         * build
         *
         * @return TemplateConfig
         */
        public TemplateConfig build() {
            return this.config;
        }

        /**
         * author
         *
         * @param author author
         * @return Builder
         */
        public Builder author(String author) {
            this.config.author = author;
            return this;
        }

        /**
         * baseFilePath
         *
         * @param baseFilePath baseFilePath
         * @return Builder
         */
        public Builder baseFilePath(String baseFilePath) {
            this.config.baseFilePath = baseFilePath;
            return this;
        }

        /**
         * basePackageName
         *
         * @param basePackageName basePackageName
         * @return Builder
         */
        public Builder basePackageName(String basePackageName) {
            this.config.basePackageName = basePackageName;
            return this;
        }

        /**
         * domainPackageName
         *
         * @param domainPackageName domainPackageName
         * @return Builder
         */
        public Builder domainPackageName(String domainPackageName) {
            this.config.domainPackageName = domainPackageName;
            return this;
        }

        /**
         * daoPackageName
         *
         * @param daoPackageName daoPackageName
         * @return Builder
         */
        public Builder daoPackageName(String daoPackageName) {
            this.config.daoPackageName = daoPackageName;
            return this;
        }

        /**
         * servicePackageName
         *
         * @param servicePackageName servicePackageName
         * @return Builder
         */
        public Builder servicePackageName(String servicePackageName) {
            this.config.servicePackageName = servicePackageName;
            return this;
        }

        /**
         * tablePrefix
         *
         * @param tablePrefix tablePrefix
         * @return Builder
         */
        public Builder tablePrefix(String tablePrefix) {
            this.config.tablePrefix = tablePrefix;
            return this;
        }

        /**
         * tableName
         *
         * @param tableName tableName
         * @return Builder
         */
        public Builder tableName(String tableName) {
            this.config.tableName = tableName;
            return this;
        }

    }
}
