package com.u8d75.core.constant;

/**
 * ResultCodeEnum
 */
public enum ResultCodeEnum {

    GLOBAL_OK(200, "成功"),
    GLOBAL_BAD_REQUEST(400, "参数错误!"),
    GLOBAL_NOT_LOGIN(403, "未登录,请先登入!"),
    GLOBAL_PARAMS_ERROR(404, "参数错误!"),
    GLOBAL_LOGIN_ERROR(405, "登录失败!"),
    GLOBAL_INTERNAL_SERVER_ERROR(500, "内部错误");

    int code;
    String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
