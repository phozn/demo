package com.meicai.demo.fun1.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.schedule.dto.TaskRequest;
import com.alibaba.dubbo.schedule.dto.TaskResponse;
import com.alibaba.dubbo.schedule.manager.ScheduleTaskManager;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.sprucetec.bone.ump.annotation.JProfiler;
import com.sprucetec.bone.ump.annotation.JProfilerEnum;

import com.meicai.demo.fun1.entity.Order;
import com.meicai.demo.fun1.service.OrderTaskService;

/**
 * worker+mq 模式，必须把消息发送到MQ，同时保证必须被消费掉 订单任务服务实现，把订单插入任务表，同时发送消息
 */
@Service
public class OrderTaskServiceImpl implements OrderTaskService {
    private static final Logger logger = Logger.getLogger(OrderTaskServiceImpl.class);
    public final static String ORDERTASKTYPE = "orderTask";

    @Value("${mq.addOrderTopic}")
    private String addOrderTopic;// 新增订单的topic

    @Value("${mq.deleteOrderTopic}")
    private String deleteOrderTopic; // 取消订单的topic

    @Autowired
    private ScheduleTaskManager scheduleTaskManager;

    @Autowired
    private MQProducer mqProducer;

    @JProfiler(key = "addOrderTask", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public TaskResponse<Order> addOrderTask(Order order) {
        TaskRequest<Order> request = new TaskRequest<Order>();
        request.setTaskType(ORDERTASKTYPE);
        request.setTaskObject(order);
        request.setTaskKey1(order.getCode());
        request.setFingerPrint(order.getCode());
        TaskResponse<Order> response = scheduleTaskManager.submitTask(request);
        this.sendMQ(response, this.addOrderTopic);
        return response;
    }

    @JProfiler(key = "deleteOrderTask", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public void deleteOrderTask(long taskId) {
        this.scheduleTaskManager.deleteTask(ORDERTASKTYPE, taskId);
        TaskResponse<Order> task = this.scheduleTaskManager.queryTask(ORDERTASKTYPE, taskId);
        this.sendMQ(task, this.deleteOrderTopic);
    }

    @JProfiler(key = "queryOrderTask", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public TaskResponse<Order> queryOrderTask(long taskId) {
        return this.scheduleTaskManager.queryTask(ORDERTASKTYPE, taskId);
    }

    private void sendMQ(TaskResponse<Order> task, String topic) {
        // 发MQ消息
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", task.getTaskObject().getId());
            jsonObj.put("code", task.getTaskObject().getCode());
            jsonObj.put("taskId", task.getId());
            Message msg = new Message(topic, "211", String.valueOf(task.getTaskObject().getId()), jsonObj.toJSONString().getBytes());
            msg.setFlag(1001); // flag标志系统
            this.mqProducer.send(msg);
            this.scheduleTaskManager.lockTask(task);// 发送完成
        } catch (Exception e) {
            this.scheduleTaskManager.errorTask(task, e);
            logger.error("order task service[" + task.getTaskObject().getId() + "] send mq error:" + e.getMessage(), e);
        }
    }
}
