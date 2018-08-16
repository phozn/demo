package com.meicai.demo.fun2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sprucetec.bone.common.PageCondition;
import com.meicai.demo.fun2.entity.Address;

/**
 * Created by zhaoyukai on 2015/10/15.
 */
public interface AddressDao {
	int insert(Address address);

	int insertBatch(@Param("list") List<Address> addressList);

	int update(Address address);

	/**
	 * 根据id删除对应地址
	 * 
	 * @param id
	 *            id
	 * @return 影响的行数
	 */
	int delete(@Param("id") long id);

	Address queryAddress(@Param("id") long id);

	/**
	 * 分页查询地址， 根据
	 * 
	 * @param pageParam
	 *            分页信息
	 * @return
	 */
	List<Address> queryAddresses(@Param("condition") PageCondition condition);
}
