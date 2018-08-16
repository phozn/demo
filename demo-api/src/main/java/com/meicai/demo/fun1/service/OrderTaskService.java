package com.meicai.demo.fun1.service;

import com.alibaba.dubbo.schedule.dto.TaskResponse;
import com.meicai.demo.fun1.entity.Order;

/**
 * 订单任务服务
 *
 * @author sylink
 */
public interface OrderTaskService {
    /**
     * 增加订单任务
     *
     * @param order
     * @return
     */
    TaskResponse<Order> addOrderTask(Order order);

    /**
     * 删除订单任务
     *
     * @param taskId
     */
    void deleteOrderTask(long taskId);

    /**
     * 查询订单任务
     *
     * @param taskId
     * @return
     */
    TaskResponse<Order> queryOrderTask(long taskId);
}
