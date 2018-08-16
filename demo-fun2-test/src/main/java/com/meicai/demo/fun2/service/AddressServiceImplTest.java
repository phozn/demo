package com.meicai.demo.fun2.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.meicai.demo.fun2.entity.Address;
import com.meicai.demo.fun2.service.AddressService;

/**
 * ServiceTest generate test stub only
 *
 * Created by zhaoyukai on 2015/10/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-fun2-service.xml" })
public class AddressServiceImplTest {
	@Qualifier("addressServiceImpl")
	@Autowired
	private AddressService addressService;

	@Test
	public void addAddressTest() {
		Address address1 = new Address();
		address1.setId(1);
		address1.setAddress("this is a test!");
		address1.setZip("999999");
		Address address2 = addressService.addAddress(address1);
		Assert.assertEquals(address1.getId(), address2.getId());
	}

	@Test
	public void deleteAddressTest() {
		boolean result = addressService.deleteAddress(1);
		Assert.assertEquals(result, true);
	}
}
