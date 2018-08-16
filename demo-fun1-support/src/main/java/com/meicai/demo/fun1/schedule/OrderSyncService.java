package com.meicai.demo.fun1.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.schedule.ScheduleParam;
import com.alibaba.dubbo.schedule.support.AbstractScheduleTaskProcess;
import com.alibaba.dubbo.schedule.util.ScheduleUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.sprucetec.bone.ump.annotation.JProfiler;
import com.sprucetec.bone.ump.annotation.JProfilerEnum;
import com.meicai.demo.fun1.entity.Order;
import com.meicai.demo.fun1.manager.OrderManager;

/**
 * 订单同步服务
 * <p>
 * 处理MQ订阅失败的消息
 */
@Service
public class OrderSyncService extends AbstractScheduleTaskProcess<Order> {
    @Value("${mq.addOrderTopic}")
    private String addOrderTopic;// 新增订单的topic
    @Value("${mq.deleteOrderTopic}")
    private String deleteOrderTopic; // 取消订单的topic

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private MQProducer mqProducer;

    @Override
    @JProfiler(key = "OrderSyncService.execute", states = {JProfilerEnum.TP, JProfilerEnum.FunctionError})
    public int execute(ScheduleParam param, Integer curServer) {
        return super.execute(param, curServer);
    }

    @Override
    protected List<Order> selectTasks(ScheduleParam param, Integer curServer) {
        return orderManager.queryNeedSyncOrders(ScheduleUtil.getTaskRegions(param.getServerCount(), curServer),
                param.getFetchCount());
    }

    @Override
    protected void executeTasks(List<Order> tasks) {
        for (Order order : tasks) {
            if (order.getYn() == 1) {
                this.sendMQ(order, this.addOrderTopic);
            } else {
                this.sendMQ(order, this.deleteOrderTopic);
            }
        }
    }

    private void sendMQ(Order order, String topic) {
        // 发MQ消息
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", order.getId());
            jsonObj.put("code", order.getCode());
            Message msg = new Message(topic, "211", String.valueOf(order.getId()), jsonObj.toJSONString().getBytes());
            msg.setFlag(1001);  //设置系统标记
            this.mqProducer.send(msg);
            orderManager.updateSync(order.getId(), 1); // 发送完成
        } catch (Exception e) {
            orderManager.updateSync(order.getId(), 2); // 失败
            logger.error("order schedule[" + order.getId() + "] send mq error:" + e.getMessage(), e);
        }
    }
}
