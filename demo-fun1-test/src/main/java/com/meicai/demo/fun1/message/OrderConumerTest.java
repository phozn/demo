package com.meicai.demo.fun1.message;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.sprucetec.bone.common.message.ConsumerItem;
import com.sprucetec.bone.common.message.PushConsumerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-fun1-service.xml" })
public class OrderConumerTest {
	@Value("${mq.addOrderTopic}")
	private String addOrderTopic;
	@Value("${mq.addOrderSubExp}")
	private String addOrderSubExp;
	private PushConsumerService consumerService;

	@Autowired
	private DefaultMQPushConsumer pushConsumer;
	
	@Qualifier("orderConsumerListener")
	@Autowired
	private OrderConsumerListener messageLisenter;

	@Test
	public void consumer() throws Exception {
		ConsumerItem item = new ConsumerItem();
		item.setTopic(addOrderTopic);
		item.setSubExpression(addOrderSubExp);
		List<ConsumerItem> items = new ArrayList<ConsumerItem>();
		items.add(item);
		consumerService = new PushConsumerService();
		consumerService.setItems(items);
		consumerService.setMqConsumer(pushConsumer);
		consumerService.setMessageListener(messageLisenter);
		consumerService.start();
		Thread.sleep(10*1000);
		System.out.println("Consumer Started.");
	}
}
