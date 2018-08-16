package com.meicai.demo.fun2.schedule;

/**
 * 订单同步的dto
 */
public class AddressSyncDto {
    private long addressId;
    private int dataOp; // 1C 2R 3U 4D

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public int getDataOp() {
        return dataOp;
    }

    public void setDataOp(int dataOp) {
        this.dataOp = dataOp;
    }
}
