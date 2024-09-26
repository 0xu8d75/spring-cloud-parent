package com.u8d75.core.rpc;

import com.u8d75.core.common.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;

/**
 * RpcInfoHolder
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RpcInfoHolder {

    /**
     * getRpcUserId
     *
     * @return Long
     */
    public static Long getRpcUserId() {
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != servletRequestAttributes) {
                return Long.parseLong(servletRequestAttributes.getRequest().getHeader(Constants.X_USER_ID));
            }
            return 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * getRpcUserName
     *
     * @return String
     */
    public static String getRpcUserName() {
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != servletRequestAttributes) {
                return URLDecoder.decode(servletRequestAttributes.getRequest().getHeader(Constants.X_USER_NAME), "utf-8");
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

}
