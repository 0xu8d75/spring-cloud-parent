package com.u8d75.core.base.controller;

import com.u8d75.core.base.result.BaseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import java.io.Serializable;

/**
 * BaseController
 */
@ControllerAdvice
@Slf4j
public abstract class BaseController {

    @Autowired
    Environment environment;

    /**
     * @param req HttpServletRequest
     * @param e   Exception
     * @return BaseResult
     */
    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class, MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResult<Serializable> handlePathError(HttpServletRequest req, Exception e) {
        log.error(getMessagePayload(req), e);
        if (e instanceof NoHandlerFoundException e1) {
            return BaseResult.errorWithMessage("path not found:" + e1.getRequestURL());
        }
        if (e instanceof HttpRequestMethodNotSupportedException e1) {
            return BaseResult.errorWithMessage("method not support:" + e1.getMethod());
        }
        if (e instanceof MissingServletRequestParameterException e1) {
            return BaseResult.errorWithMessage("missing parameter:" + e1.getParameterName());
        }
        if (e instanceof MethodArgumentTypeMismatchException e1) {
            return BaseResult.errorWithMessage("mismatch parameter:key[" + e1.getName() + "]value[" + e1.getValue() + "]");
        }
        return BaseResult.error();
    }

    /**
     * @param req HttpServletRequest
     * @param e   Exception
     * @return BaseResult
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResult<Serializable> handleGlobalError(HttpServletRequest req, Exception e) {
        log.error(getMessagePayload(req), e);
        return BaseResult.innerErrorWithMessage(e.getMessage());
    }

    /**
     * @param request HttpServletRequest
     * @return message payload
     */
    protected String getMessagePayload(HttpServletRequest request) {
        try {
            String messagePayload = (null == request.getQueryString()) ? "" : request.getQueryString();
            ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            if (wrapper != null) {
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    return messagePayload + " " + new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                }
            }
            return messagePayload;
        } catch (Exception e) {
            return "";
        }
    }

}
