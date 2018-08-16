package com.meicai.demo.fun2.manager.impl;

import com.alibaba.dubbo.schedule.dto.TaskRequest;
import com.alibaba.dubbo.schedule.manager.ScheduleTaskManager;
import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun2.dao.AddressDao;
import com.meicai.demo.fun2.entity.Address;
import com.meicai.demo.fun2.manager.AddressManager;
import com.meicai.demo.fun2.schedule.AddressSyncDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AddressManager实现
 * <p>
 * Created by sylink on 2015/10/15. 地址保存到业务表的同时，插入的任务表中
 * 异步采用纯worker的方式，插入任务由AddressSyncService进行处理
 */
@Component
public class AddressManagerImpl implements AddressManager {
    private final String addressSyncTask = "addressSync";
    @Autowired
    private AddressDao addressDao;

    @Autowired
    private ScheduleTaskManager scheduleTaskManager;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Address addAddress(Address address) {
        int count = addressDao.insert(address);
        if (count > 0) {
            this.insertSyncTask(address.getId(), 1);
            return address;
        }
        return null;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addAddresses(List<Address> addressList) {
        addressDao.insertBatch(addressList);
        for (Address address : addressList) {
            this.insertSyncTask(address.getId(), 1);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void editAddress(Address address) {
        addressDao.update(address);
        this.insertSyncTask(address.getId(), 3);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean deleteAddress(long addressId) {
        int count = addressDao.delete(addressId);
        if (count > 0) {
            this.insertSyncTask(addressId, 4);
        }
        return count > 0;
    }

    public Page<Address> queryAddressList(PageCondition condition) {
        List<Address> addresses = addressDao.queryAddresses(condition);
        Page<Address> page = new Page<Address>(condition.getPageSize(), condition.getPageNumber());
        page.setItems(addresses);
        return page;
    }

    public Address queryAddress(long id) {
        return addressDao.queryAddress(id);
    }

    /**
     * 插入同步任务
     *
     * @param addressId
     * @param dataOp
     */
    private void insertSyncTask(long addressId, int dataOp) {
        TaskRequest<AddressSyncDto> request = new TaskRequest<AddressSyncDto>();
        AddressSyncDto dto = new AddressSyncDto();
        dto.setAddressId(addressId);
        dto.setDataOp(dataOp);
        request.setTaskObject(dto);
        request.setTaskKey1(String.valueOf(addressId));
        request.setTaskKey2(String.valueOf(dataOp));
        request.setTaskType(addressSyncTask);
        request.setFingerPrint(request.getTaskKey1() + "," + request.getTaskKey2());
        this.scheduleTaskManager.submitTask(request);
    }
}
