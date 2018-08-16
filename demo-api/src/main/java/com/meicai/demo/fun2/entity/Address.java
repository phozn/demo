package com.meicai.demo.fun2.entity;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Address 地址信息
 */
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    @NotEmpty(message = "{address.notEmpty.error}")
    private String address;
    @Pattern(regexp = "\\d{6}", message = "邮编长度必须为6!")
    private String zip;
    private byte yn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public byte getYn() {
        return yn;
    }

    public void setYn(byte yn) {
        this.yn = yn;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(address).append(zip).append(yn).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address))
            return false;
        if (obj == this)
            return true;

        Address target = (Address) obj;

        return new EqualsBuilder().append(id, target.id).append(address, target.address).append(zip, target.zip)
                .append(yn, target.yn).isEquals();
    }
}
