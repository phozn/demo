package com.meicai.demo.fun1.dao;

import org.apache.ibatis.annotations.Param;
import com.meicai.demo.fun1.entity.OrderDetail;
import java.util.List;

/**
 * 订单明细表相关操作
 */
public interface OrderDetailDao {
    int insert(OrderDetail student);

    int insertBatch(@Param("list") List<OrderDetail> orderDetailList);

    void deleteByOrderId(long orderId);

    List<OrderDetail> queryDetails(long orderId);

    int deleteByDetails(List<OrderDetail> details);

    int update(OrderDetail detail);
}
