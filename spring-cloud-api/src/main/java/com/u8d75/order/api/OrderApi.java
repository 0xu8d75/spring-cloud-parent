package com.u8d75.order.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * OrderApi
 */
@FeignClient(name = "spring-cloud-order-service", path = "/")
public interface OrderApi {

}


