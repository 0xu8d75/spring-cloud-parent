package com.u8d75.core.rpc;

import java.io.Serializable;

/**
 * RpcInfo
 */
public class RpcInfo implements Serializable {

    Long userId = 0L; // 登录用户id
    String userName = ""; // 登录用户姓名

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
