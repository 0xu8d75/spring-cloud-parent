package com.u8d75.core.constant;

/**
 * EnvironmentEnum
 */
public enum EnvironmentEnum {
    DEV("dev", "开发环境"),
    UAT("uat", "预发布环境"),
    PRD("prd", "生产环境");

    private String code;
    private String value;
    EnvironmentEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * getByCode
     *
     * @param code code
     * @return EnvironmentEnum
     */
    public static EnvironmentEnum getByCode(String code) {
        for (EnvironmentEnum environmentEnum : EnvironmentEnum.values()) {
            if (environmentEnum.code.equals(code)) {
                return environmentEnum;
            }
        }
        return EnvironmentEnum.DEV;
    }

    public String getCode() {
        return code;
    }

    void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }

}
