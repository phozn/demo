package com.meicai.demo.fun1.manager.impl;

import com.alibaba.dubbo.schedule.util.ScheduleUtil;
import com.sprucetec.bone.common.BeanUtils;
import com.sprucetec.bone.common.Constants;
import com.sprucetec.bone.common.ListCompareResult;
import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;

import com.meicai.demo.fun1.dao.OrderDao;
import com.meicai.demo.fun1.dao.OrderDetailDao;
import com.meicai.demo.fun1.entity.Order;
import com.meicai.demo.fun1.entity.OrderDetail;
import com.meicai.demo.fun1.manager.OrderManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Order addOrder(Order order) {
        if (order.getRegionNo() == null) {
            order.setRegionNo(ScheduleUtil.getRegionNo());
        }
        orderDao.insert(order);
        for (OrderDetail detail : order.getDetails()) {
            detail.setOrderId(order.getId());
        }
        if (order.getDetails() != null) {
            orderDetailDao.insertBatch(order.getDetails());
        }
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void editOrder(Order order) {
        List<OrderDetail> oldDetails = orderDetailDao.queryDetails(order.getId());
        List<OrderDetail> updateDetails = order.getDetails();

        ListCompareResult<OrderDetail> compareResult = BeanUtils.compareList(oldDetails, updateDetails, "id");

        if (compareResult.hasDeleted()) {
            orderDetailDao.deleteByDetails(compareResult.getDeletedItems());
        }

        if (compareResult.hasNew()) {
            orderDetailDao.insertBatch(compareResult.getNewItems());
        }

        // loop in manager？？？？
        if (compareResult.hasUpdated()) {
            if (compareResult.getUpdatedItems().size() > Constants.MAX_LIST_SIZE) {
                throw new RuntimeException("database option too large");
            }

            for (OrderDetail detail : compareResult.getUpdatedItems()) {
                orderDetailDao.update(detail);
            }
        }
        orderDao.update(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean deleteOrder(long orderId) {
        // delete order detail firstly
        orderDetailDao.deleteByOrderId(orderId);

        int effectedRows = orderDao.delete(orderId);
        return effectedRows == 1;
    }

    public Page<Order> getOrderList(PageCondition condition) {
        List<Order> rows = orderDao.queryOrders(condition);
        Page<Order> page = new Page<Order>(condition.getPageSize(), condition.getPageNumber());
        page.setItems(rows);

        return page;
    }

    public Order getOrder(long id) {
        Order order = orderDao.queryOrderById(id);
        order.setDetails(orderDetailDao.queryDetails(id));
        return order;
    }

    public Order getOrder(String code) {
        Order order = orderDao.queryOrderByCode(code);
        order.setDetails(orderDetailDao.queryDetails(order.getId()));
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int updateSync(long orderId, int sync) {
        return orderDao.updateSync(orderId, sync);
    }

    public List<Order> queryNeedSyncOrders(List<Integer> regions, int limit) {
        return orderDao.queryNeedSyncOrders(regions, limit);
    }
}
