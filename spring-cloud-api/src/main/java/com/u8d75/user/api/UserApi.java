package com.u8d75.user.api;

import com.u8d75.core.base.result.RpcResult;
import com.u8d75.user.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UserApi
 */
@FeignClient(name = "spring-cloud-user-service", path = "/")
public interface UserApi {

    /**
     * findUserById
     *
     * @param userId userId
     * @return RpcResult<User>
     */
    @PostMapping("/findUserById")
    RpcResult<User> findUserById(@RequestParam(value = "userId", required = true) Long userId);

}


