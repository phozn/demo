package com.meicai.demo.fun1.dao;

import com.meicai.demo.fun1.dao.OrderDetailDao;
import com.meicai.demo.fun1.entity.OrderDetail;
import com.meicai.demo.fun1.enums.Gender;
import com.meicai.demo.fun2.entity.Address;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * DAO test
 *
 * Created by zhaoyukai on 2015/10/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-fun1-dao.xml" })
public class OrderDetailDaoTest {

	@Autowired
	private OrderDetailDao orderDetailDAO;
	private Address testAddress;

	@Before
	public void setup() {
		testAddress = new Address();
		testAddress.setId(1);
	}

	@Test
	public void insertTest() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setName("Bush");
		Calendar c = Calendar.getInstance();
		c.set(1970, 2, 2);
		Date birthday = c.getTime();
		orderDetail.setBirthday(birthday);
		orderDetail.setOrderId(1);

		orderDetail.setAddress(testAddress);
		orderDetail.setGender(Gender.MAN);
		orderDetail.setYn((byte) 0);
		int rows = orderDetailDAO.insert(orderDetail);
		Assert.assertTrue(rows == 1);
	}

	@Test
	public void insertBatchTest() {

		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		OrderDetail detail = new OrderDetail();
		detail.setName("Bush");
		Calendar c = Calendar.getInstance();
		c.set(1970, 2, 2);
		Date birthday = c.getTime();
		detail.setBirthday(birthday);
		detail.setOrderId(1);
		detail.setGender(Gender.MAN);
		detail.setAddress(testAddress);
		detail.setYn((byte) 0);
		orderDetailList.add(detail);

		detail = new OrderDetail();
		detail.setName("Bob");
		c = Calendar.getInstance();
		c.set(1980, 2, 2);
		birthday = c.getTime();
		detail.setBirthday(birthday);
		detail.setOrderId(1);
		detail.setGender(Gender.MAN);
		detail.setAddress(testAddress);
		detail.setYn((byte) 0);
		orderDetailList.add(detail);
		int rows = orderDetailDAO.insertBatch(orderDetailList);
		Assert.assertTrue(rows == 2);
	}

	@Test
	public void deleteOrderDetailsTest() {
		OrderDetail deletedA = new OrderDetail();
		deletedA.setId(4);

		OrderDetail deleteB = new OrderDetail();
		deleteB.setId(5);

		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>(2);
		orderDetails.add(deleteB);
		orderDetails.add(deletedA);

		int rows = orderDetailDAO.deleteByDetails(orderDetails);
		Assert.assertTrue(rows == 2);
	}

	@Test
	public void updateTest() {
		OrderDetail detail = new OrderDetail();
		detail.setId(1);
		detail.setName("new Name");
		detail.setGender(Gender.WOMAN);
		detail.setBirthday(new Date());
		detail.setYn((byte) 0);
		int rows = orderDetailDAO.update(detail);
		Assert.assertTrue(rows == 1);
	}
}
