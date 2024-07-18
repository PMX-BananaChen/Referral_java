package com.primax.config;

import lombok.Data;

import java.util.Date;

/**
 * 微信权限实体类
 */
@Data
public class Wxoauth2token {
    private String userId;      //如果是内部成员，获取其userId
    private String openId;         //如果是外部成员获取其对应应用的唯一标识
    private String accessToken; //网页授权接口调用凭证
    private Integer expiresIn;  //凭证有效时长
    private String scope;
    private String refreshToken;        // 用于刷新凭证
    private Date updateTime;        // 开始日期
}
