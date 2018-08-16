package com.meicai.demo.fun1.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单信息
 * <p>
 * 订单和订单明细之间是一对多关系
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private Date createTime;
    private int year;
    private String code;
    private Integer regionNo;
    private int sync;
    private Date lastTime;
    private int yn = 1;
    private List<OrderDetail> details = new ArrayList<OrderDetail>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRegionNo() {
        return regionNo;
    }

    public void setRegionNo(Integer regionNo) {
        this.regionNo = regionNo;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public int getYn() {
        return yn;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }
}
