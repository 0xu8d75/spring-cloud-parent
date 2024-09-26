package com.u8d75.core.base.result;

import com.u8d75.core.constant.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * BaseResult
 *
 * @param <T>
 */
@Data
public class BaseResult<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 7277594240016924327L;

    protected int code = ResultCodeEnum.GLOBAL_OK.getCode();
    protected String msg = ResultCodeEnum.GLOBAL_OK.getMsg();

    protected T data;

    public BaseResult() {
        super();
    }

    public BaseResult(T data) {
        super();
        this.data = data;
    }

    public BaseResult(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(int code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * @param resultCode resultCode
     */
    public BaseResult(ResultCodeEnum resultCode) {
        super();
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    /**
     * build success result
     *
     * @param <T> type
     * @return success result
     */
    public static <T extends Serializable> BaseResult<T> ok() {
        return new BaseResult<>(ResultCodeEnum.GLOBAL_OK);
    }

    /**
     * build success result with data
     *
     * @param data data
     * @param <T>  type
     * @return success result with data
     */
    public static <T extends Serializable> BaseResult<T> ok(T data) {
        return new BaseResult<>(data);
    }

    /**
     * build error result 400
     *
     * @param <T> type
     * @return error result
     */
    public static <T extends Serializable> BaseResult<T> error() {
        return new BaseResult<>(ResultCodeEnum.GLOBAL_BAD_REQUEST);
    }

    /**
     * build error result 400 with message
     *
     * @param message message
     * @param <T>     type
     * @return error result with message
     */
    public static <T extends Serializable> BaseResult<T> errorWithMessage(String message) {
        return new BaseResult<>(ResultCodeEnum.GLOBAL_BAD_REQUEST.getCode(), message);
    }

    /**
     * build error result 500
     *
     * @param <T> type
     * @return error result
     */
    public static <T extends Serializable> BaseResult<T> innerError() {
        return new BaseResult<>(ResultCodeEnum.GLOBAL_INTERNAL_SERVER_ERROR);
    }

    /**
     * build error result 500 with message
     *
     * @param message message
     * @param <T>     type
     * @return error result with message
     */
    public static <T extends Serializable> BaseResult<T> innerErrorWithMessage(String message) {
        return new BaseResult<>(ResultCodeEnum.GLOBAL_INTERNAL_SERVER_ERROR.getCode(), message);
    }

}
