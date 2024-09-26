package com.u8d75.order.service;

import com.u8d75.core.base.service.impl.BaseService;
import com.u8d75.order.dao.OrderDao;
import com.u8d75.order.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单
 * author u8d75
 */
@Service
public class OrderServiceImpl extends BaseService<Order> {

    @Autowired
    private OrderDao orderDao;

    @SuppressWarnings("unchecked")
    @Override
    public OrderDao getDao() {
        return orderDao;
    }
}
