package com.primax.dbconfig;

public enum DBTypeEnum {
    //db1东莞企业微信
    //db2重庆
    //db3昆山
    db1("db1"), db2("db2") ,db3("db3");
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
