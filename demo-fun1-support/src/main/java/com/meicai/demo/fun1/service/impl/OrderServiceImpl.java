package com.meicai.demo.fun1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.sprucetec.bone.common.Constants;
import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.sprucetec.bone.common.redis.RedisService;
import com.sprucetec.bone.ump.annotation.JProfiler;
import com.sprucetec.bone.ump.annotation.JProfilerEnum;

import com.meicai.demo.fun1.entity.Order;
import com.meicai.demo.fun1.manager.OrderManager;
import com.meicai.demo.fun1.service.OrderService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 发MQ消息有两个级别
 * 1、保证消息都可以发送到MQ中（系统之间）
 * 2、保证消息都可以发送到MQ中，同时也保证能收到消息（系统内部） 本例中是解决第一种，保证消息发到MQ中
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
    public static final String ORDERKEY = "order_";

    @Value("${mq.addOrderTopic}")
    private String addOrderTopic;// 新增订单的topic
    @Value("${mq.deleteOrderTopic}")
    private String deleteOrderTopic; // 取消订单的topic

    @Value("${application.orderCacheSeconds}")
    private int orderCacheSeconds; // 订单缓存时间


    @Autowired
    private OrderManager orderManager;
    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private RedisService redisService;

    @JProfiler(key = "addOrder", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public Order addOrder(Order order) {
        if (order.getDetails() != null) {
            int count = order.getDetails().size();
            if (count > Constants.MAX_LIST_SIZE) {
                throw new RuntimeException("order detail list size great than limit, when add order");
            }
        }
        Order order1 = orderManager.addOrder(order);
        this.sendMQ(order1, this.addOrderTopic);
        return order1;
    }

    @JProfiler(key = "deleteOrder", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public boolean deleteOrder(long id) {
        boolean result = orderManager.deleteOrder(id);
        Order order1 = new Order();
        order1.setId(id);
        this.sendMQ(order1, this.deleteOrderTopic);
        this.redisService.delete(ORDERKEY + id);
        return result;
    }

    @JProfiler(key = "queryOrders", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public Page<Order> queryOrders(PageCondition condition) {
        return orderManager.getOrderList(condition);
    }

    @JProfiler(key = "editOrder", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public void editOrder(Order order) {
        orderManager.editOrder(order);
        this.redisService.setex(ORDERKEY + order.getId(), this.orderCacheSeconds, order);
    }

    @JProfiler(key = "queryOrder", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public Order queryOrder(long id) {
        Order order = this.redisService.getJSONToObject(Order.class, ORDERKEY + id);
        if (order == null) {
            order = orderManager.getOrder(id);
            this.redisService.setex(ORDERKEY + order.getId(), this.orderCacheSeconds, order);
        }
        return order;
    }

    private void sendMQ(Order order, String topic) {
        // 发MQ消息
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", order.getId());
            jsonObj.put("code", order.getCode());
            Message msg = new Message(topic, "211", String.valueOf(order.getId()), jsonObj.toJSONString().getBytes());
            msg.setFlag(1001); // flag标志系统
            this.mqProducer.send(msg);
            orderManager.updateSync(order.getId(), 1); // 发送完成
        } catch (Exception e) {
            orderManager.updateSync(order.getId(), 2); // 失败
            logger.error("order service[" + order.getId() + "] send mq error:" + e.getMessage(), e);
        }
    }
}
