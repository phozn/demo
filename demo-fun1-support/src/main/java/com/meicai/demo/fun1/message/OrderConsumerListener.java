package com.meicai.demo.fun1.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.schedule.dto.TaskResponse;
import com.alibaba.dubbo.schedule.manager.ScheduleTaskManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.meicai.demo.fun1.entity.Order;
import com.meicai.demo.fun1.manager.OrderManager;
import com.meicai.demo.fun1.service.impl.OrderTaskServiceImpl;

/**
 * 消息消费Listener
 */
@Component
public class OrderConsumerListener implements MessageListenerConcurrently {
    @Autowired
    private ScheduleTaskManager scheduleTaskManager;

    @Autowired
    private OrderManager orderManager;

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println(Thread.currentThread().getName() + " Receive New Order from ["
                + context.getMessageQueue().getTopic() + "]: " + msgs);
        for (MessageExt message : msgs) {
            if (message.getFlag() == 1001) {
                // 处理订单的逻辑
                JSONObject jsonObj = JSON.parseObject(new String(message.getBody()));
                if (jsonObj.containsKey("taskId")) {
                    TaskResponse<Order> task = scheduleTaskManager.queryTask(OrderTaskServiceImpl.ORDERTASKTYPE,
                            jsonObj.getLong("taskId"));
                    try {
                        this.orderManager.addOrder(task.getTaskObject());
                        // 更新状态
                        this.scheduleTaskManager.doneTask(task);
                    } catch (Exception e) {
                        this.scheduleTaskManager.errorTask(task, e);
                    }
                }
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
