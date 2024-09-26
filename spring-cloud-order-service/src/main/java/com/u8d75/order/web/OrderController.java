package com.u8d75.order.web;

import com.u8d75.core.base.controller.BaseController;
import com.u8d75.core.base.result.BaseResult;
import com.u8d75.core.mybatis.Where;
import com.u8d75.order.domain.Order;
import com.u8d75.order.service.OrderServiceImpl;
import com.u8d75.user.api.UserApi;
import com.u8d75.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * OrderController
 */
@RestController
@RequestMapping("/com/u8d75/order")
@Tag(name = "订单")
@Log4j2
public class OrderController extends BaseController {

    @Autowired
    private UserApi userApi;
    @Autowired
    private OrderServiceImpl orderService;

    /**
     * findByUserId
     *
     * @param userId userId
     * @return BaseResult<HashMap < String, Object>>
     */
    @PostMapping(value = "findByUserId")
    @Operation(summary = "根据用户查找运单列表")
    public BaseResult<HashMap<String, Object>> findByUserId(
            @RequestParam Long userId
    ) {
        HashMap<String, Object> data = new HashMap<>();
        User user = userApi.findUserById(userId).getData();
        List<Order> list = orderService.findListByParams(new Where<Order>().eq("userId", userId));
        data.put("user", user);
        data.put("orders", list);
        return BaseResult.ok(data);
    }

}
