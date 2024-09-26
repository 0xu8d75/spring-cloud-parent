package com.u8d75.user.api;

import com.u8d75.core.base.result.RpcResult;
import com.u8d75.user.domain.User;
import com.u8d75.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserRpc
 */
@RestController
public class UserRpc implements UserApi {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public RpcResult<User> findUserById(Long userId) {
        return RpcResult.ok(userService.findById(userId));
    }
}
