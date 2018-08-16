package com.meicai.demo.fun1.dao;

import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun1.entity.Order;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by zhaoyukai on 2015/10/12.
 */
public interface OrderDao {
    /**
     * 插入新订单
     *
     * @param order 订单信息
     * @return 新订单
     */
    int insert(Order order);

    /**
     * 编辑订单信息
     *
     * @param order 订单信息
     * @return 影响行数
     */
    int update(Order order);

    /**
     * 根据id删除对应订单
     *
     * @param id id
     * @return 影响的行数
     */
    int delete(@Param("id") long id);

    /**
     * 分页查询订单， 根据
     *
     * @param pageParam 分页信息
     * @return
     */
    List<Order> queryOrders(@Param("condition") PageCondition condition);

    /**
     * 根据订单id获得订单信息
     *
     * @param id 订单id
     * @return 订单信息
     */
    Order queryOrderById(@Param("id") long id);

    /**
     * 根据代号获得订单信息
     *
     * @param code 订单代号
     * @return 订单信息
     */
    Order queryOrderByCode(@Param("code") String code);

    /**
     * 根据订单ID来更新同步状态
     *
     * @param orderId
     * @param sync
     * @return
     */
    int updateSync(@Param("orderId") long orderId, @Param("sync") int sync);

    /**
     * 根据同步状态来取订单
     *
     * @param limit
     * @return
     */
    List<Order> queryNeedSyncOrders(@Param("regions") List<Integer> regions, @Param("limit") int limit);
}
