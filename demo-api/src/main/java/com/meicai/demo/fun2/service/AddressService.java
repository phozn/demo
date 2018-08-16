package com.meicai.demo.fun2.service;

import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun2.entity.Address;

/**
 * 地址服务
 */
public interface AddressService {

    Address addAddress(Address address);

    Address queryAddress(long id);

    void editAddress(Address address);

    boolean deleteAddress(long id);

    Page<Address> queryAddresses(PageCondition condition);
}
