package com.meicai.demo.fun2.service.impl;

import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.sprucetec.bone.ump.annotation.JProfiler;
import com.sprucetec.bone.ump.annotation.JProfilerEnum;

import com.meicai.demo.fun2.entity.Address;
import com.meicai.demo.fun2.manager.AddressManager;
import com.meicai.demo.fun2.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyukai on 2015/10/12.
 */
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressManager addressManager;

	@JProfiler(key = "addAddress", states = { JProfilerEnum.TP, JProfilerEnum.FunctionError })
	public Address addAddress(Address address) {
		return addressManager.addAddress(address);
	}
	
	@JProfiler(key = "queryAddress", states = { JProfilerEnum.TP, JProfilerEnum.FunctionError })
	public Address queryAddress(long id) {
		return addressManager.queryAddress(id);
	}

	@JProfiler(key = "editAddress", states = { JProfilerEnum.TP, JProfilerEnum.FunctionError })
	public void editAddress(Address address) {
		addressManager.editAddress(address);
	}
	
	@JProfiler(key = "deleteAddress", states = { JProfilerEnum.TP, JProfilerEnum.FunctionError })
	public boolean deleteAddress(long id) {
		return addressManager.deleteAddress(id);
	}

	public Page<Address> queryAddresses(PageCondition condition) {
		return addressManager.queryAddressList(condition);
	}
}
