package com.meicai.demo.fun1.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun1.entity.OrderDetail;
import com.meicai.demo.fun1.enums.Gender;
import com.meicai.demo.fun2.entity.Address;

import com.meicai.demo.fun1.entity.Order;import com.meicai.demo.fun1.service.OrderService;

/**
 * ServiceTest generate test stub only
 *
 * Created by zhaoyukai on 2015/10/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-fun1-service.xml" })
public class OrderServiceTest {
	@Qualifier("orderServiceImpl")
	@Autowired
	private OrderService orderService;

	@Test
	public void addOrderTest() throws Exception {
		Address address = new Address();
		address.setId(100);
		Order order1 = new Order();
		order1.setCode("test14");
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
		Order order2 = orderService.addOrder(order1);
		Assert.assertEquals(order1.getId(), order2.getId());
	}

	@Test
	public void deleteOrderTest() {
		boolean result = orderService.deleteOrder(1);
		Assert.assertEquals(result, true);
	}

	@Test
	public void queryOrderTest() {
		Order order = orderService.queryOrder(1);
		Assert.assertNotNull(order);
	}

	@Test
	public void queryOrderPageTest() {
		PageCondition condition = new PageCondition(20, 1);
		Page<Order> page = orderService.queryOrders(condition);
		Assert.assertTrue(page.getPageNumber() == 1);
	}
}
