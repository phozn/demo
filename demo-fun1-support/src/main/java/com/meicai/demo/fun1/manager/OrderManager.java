package com.meicai.demo.fun1.manager;

import java.util.List;
import com.sprucetec.bone.common.Page;
import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun1.entity.Order;

public interface OrderManager {
    Order addOrder(Order order);

    void editOrder(Order order);

    boolean deleteOrder(long orderId);

    Page<Order> getOrderList(PageCondition condition);

    Order getOrder(long id);

    Order getOrder(String code);

    int updateSync(long orderId, int sync);

    List<Order> queryNeedSyncOrders(List<Integer> regions, int limit);
}
