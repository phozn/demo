package com.meicai.demo.fun2.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.meicai.demo.fun2.dao.AddressDao;
import com.meicai.demo.fun2.entity.Address;

/**
 * DAO test example
 *
 * Created by zhaoyukai on 2015/10/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-fun2-dao.xml" })
public class AddressDaoTest {
    @Autowired
    private AddressDao addressDAO;

    @Test
    public void insertTest() {
        Address address = new Address();
        address.setAddress("HELLO Street");
        address.setZip("000001");
        address.setYn((byte)0);
        addressDAO.insert(address);
    }
}
