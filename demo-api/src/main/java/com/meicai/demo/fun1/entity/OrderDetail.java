package com.meicai.demo.fun1.entity;

import java.io.Serializable;
import java.util.Date;
import com.meicai.demo.fun1.enums.Gender;
import com.meicai.demo.fun2.entity.Address;

/**
 * 订单明细
 * <p>
 * 学生和地址之间是一对一关系
 */
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private long orderId;
    private String name;
    private Gender gender;
    private Date birthday;
    private byte yn = 1;

    // private int addressId;


    private Address address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public byte getYn() {
        return yn;
    }

    public void setYn(byte yn) {
        this.yn = yn;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
