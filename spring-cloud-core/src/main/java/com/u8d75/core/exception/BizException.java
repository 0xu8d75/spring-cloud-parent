package com.u8d75.core.exception;

/**
 * BizException
 */
public class BizException extends RuntimeException {

    public BizException() {
        super("系统异常,请联系管理员!");
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Exception e) {
        super(e);
    }

}
