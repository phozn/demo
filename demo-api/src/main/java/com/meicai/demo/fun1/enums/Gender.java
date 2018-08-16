package com.meicai.demo.fun1.enums;

/**
 * 性别 枚举需要i18n支持
 */
public enum Gender {
    /**
     * 男士
     */
    MAN((byte) 1, "男士"),

    /**
     * 女士
     */
    WOMAN((byte) 2, "女士");

    private Gender(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    private byte value;
    private String name;

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
