package com.meicai.demo.fun2.manager;

import java.util.List;

import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun2.entity.Address;

/**
 * Created by zhaoyukai on 2015/10/15.
 */
public interface AddressManager {
	Address addAddress(Address address);

	void addAddresses(List<Address> addressList);

	void editAddress(Address address);

	boolean deleteAddress(long addressId);

	Page<Address> queryAddressList(PageCondition condition);

	Address queryAddress(long id);
}
