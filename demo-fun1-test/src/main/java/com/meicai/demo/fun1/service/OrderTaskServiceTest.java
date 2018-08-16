package com.meicai.demo.fun1.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.schedule.dto.TaskResponse;
import com.meicai.demo.fun1.entity.Order;
import com.meicai.demo.fun1.entity.OrderDetail;
import com.meicai.demo.fun1.enums.Gender;
import com.meicai.demo.fun2.entity.Address;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-fun1-service.xml" })
public class OrderTaskServiceTest {
	@Autowired
	private OrderTaskService taskService;

	@Test
	public void addOrderTest() {
		Address address = new Address();
		address.setId(100);
		Order order1 = new Order();
		order1.setCode("testTask17");
		order1.setYear(2013);
		OrderDetail detail = new OrderDetail();
		detail.setAddress(address);
		detail.setGender(Gender.MAN);
		detail.setBirthday(new Date());
		detail.setName(order1.getCode() + "_1");
		order1.getDetails().add(detail);
		detail = new OrderDetail();
		detail.setGender(Gender.WOMAN);
		detail.setAddress(address);
		detail.setBirthday(new Date());
		detail.setName(order1.getCode() + "_2");
		order1.getDetails().add(detail);
		TaskResponse<Order> task = this.taskService.addOrderTask(order1);
		Assert.assertNotNull(task);
	}
}
