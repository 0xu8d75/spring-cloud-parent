package com.u8d75.core.base.result;

import com.u8d75.core.constant.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * RpcResult
 *
 * @param <T>
 */
@Data
public class RpcResult<T extends Serializable> extends BaseResult<T> {

    public RpcResult() {
        super();
    }

    public RpcResult(T data) {
        super(data);
    }

    public RpcResult(int code, String msg) {
        super(code, msg);
    }

    public RpcResult(int code, String msg, T data) {
        super(code, msg, data);
    }

    public RpcResult(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    /**
     * build success result
     *
     * @param <T> type
     * @return success result
     */
    public static <T extends Serializable> RpcResult<T> ok() {
        return new RpcResult<>(ResultCodeEnum.GLOBAL_OK);
    }

    /**
     * build success result with data
     *
     * @param data data
     * @param <T>  type
     * @return success result with data
     */
    public static <T extends Serializable> RpcResult<T> ok(T data) {
        return new RpcResult<>(data);
    }

    /**
     * build error result 400
     *
     * @param <T> type
     * @return error result
     */
    public static <T extends Serializable> RpcResult<T> error() {
        return new RpcResult<>(ResultCodeEnum.GLOBAL_BAD_REQUEST);
    }

    /**
     * build error result 400 with message
     *
     * @param message message
     * @param <T>     type
     * @return error result with message
     */
    public static <T extends Serializable> RpcResult<T> errorWithMessage(String message) {
        return new RpcResult<>(ResultCodeEnum.GLOBAL_BAD_REQUEST.getCode(), message);
    }

    /**
     * build error result 500
     *
     * @param <T> type
     * @return error result
     */
    public static <T extends Serializable> RpcResult<T> innerError() {
        return new RpcResult<>(ResultCodeEnum.GLOBAL_INTERNAL_SERVER_ERROR);
    }

    /**
     * build error result 500 with message
     *
     * @param message message
     * @param <T>     type
     * @return error result with message
     */
    public static <T extends Serializable> RpcResult<T> innerErrorWithMessage(String message) {
        return new RpcResult<>(ResultCodeEnum.GLOBAL_INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public boolean isSuccess() {
        return ResultCodeEnum.GLOBAL_OK.getCode() == this.code;
    }
}
