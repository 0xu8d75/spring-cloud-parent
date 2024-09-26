package com.u8d75.order.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单
 * author u8d75
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id; // 主键
    private Long userId; // 用户id
    private Long goodsId; // 商品id
    private Long createTime; // 创建时间

}
