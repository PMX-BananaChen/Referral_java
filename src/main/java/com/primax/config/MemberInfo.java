package com.primax.config;

import lombok.Data;

/**
 * 通过code和access_token获取的成员信息，userId或者openid
 */
@Data
public class MemberInfo {
    //返回码
    private Integer errCode;
    //对返回码描述内容
    private String errMsg;

    private String UserId;

    private String OpenId;
    //手机设备号
    private String DeviceId;

}
