package com.u8d75.core.interceptor;

import com.u8d75.core.rpc.RpcInfoHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * LogInterceptor
 */
public class LogInterceptor implements HandlerInterceptor {

    private static final String MDC_USER_ID = "userId";
    private static final String MDC_URI = "uri";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        MDC.put(MDC_USER_ID, String.valueOf(RpcInfoHolder.getRpcUserId()));
        MDC.put(MDC_URI, httpServletRequest.getServletPath());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        MDC.remove(MDC_USER_ID);
        MDC.remove(MDC_URI);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // empty
    }
}