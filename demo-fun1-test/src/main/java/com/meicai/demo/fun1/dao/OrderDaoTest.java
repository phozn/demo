package com.meicai.demo.fun1.dao;

import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun1.dao.OrderDao;
import com.meicai.demo.fun1.entity.Order;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * DAO test
 *
 * Created by zhaoyukai on 2015/10/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-fun1-dao.xml" })
public class OrderDaoTest {

	@Autowired
	private OrderDao orderDao;

	@Test
	public void insertTest() {
		Order order = new Order();
		order.setCode("971");
		order.setYear(1997);
		int rows = orderDao.insert(order);
		Assert.assertEquals(rows, 1);
	}

	@Test
	public void queryOrderByIdTest() {
		final int orderId = 1;
		Order order = orderDao.queryOrderById(orderId);
		Assert.assertEquals(orderId, order.getId());
	}

	@Test
	public void selectOrderByCodeTest() {
		final String code = "121";
		Order order = orderDao.queryOrderByCode(code);
		Assert.assertEquals(code, order.getCode());
	}

	@Test
	public void updateTest() {
		String newCode = "122";
		Order order = new Order();
		order.setId(2);
		order.setCode(newCode);
		order.setYear(1997);
		int effectedRows = orderDao.update(order);
		Assert.assertEquals(1, effectedRows);
	}

	@Test
	public void selectOrdersTest() {
		int pageSize = 5;
		int pageNumber = 1;
		PageCondition condition = new PageCondition(pageSize, pageNumber);
		List<Order> orderList = orderDao.queryOrders(condition);
		Assert.assertTrue(orderList.size() > 0);
		Assert.assertTrue(orderList.size() <= pageSize);
	}
}
