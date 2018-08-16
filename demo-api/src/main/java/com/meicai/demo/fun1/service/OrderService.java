package com.meicai.demo.fun1.service;

import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun1.entity.Order;

/**
 * 订单服务
 */
public interface OrderService {

    /**
     * 添加订单
     *
     * @param order 订单信息
     * @return 添加新新订单
     */
    Order addOrder(Order order);

    /**
     * 删除订单
     *
     * @param id: 订单id
     */
    boolean deleteOrder(long id);

    /**
     * 分页获得订单信息
     *
     * @param condition 分页信息
     * @return 订单列表
     */
    Page<Order> queryOrders(PageCondition condition);

    /**
     * 获取订单
     *
     * @param id
     * @return
     */
    Order queryOrder(long id);

    /**
     * 编辑订单信息
     *
     * @param order: 订单信息
     */
    void editOrder(Order order);

}
