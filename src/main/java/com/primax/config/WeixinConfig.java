package com.primax.config;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class WeixinConfig {

    public static String GET_ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";//网页授权凭证access_token
    public static String GET_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";//获取用户信息
    public static String GET_INFO_USERID = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";//获取通讯录成员信息
    public static String OPENID_TO_USEID ="https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token=ACCESS_TOKEN";
    //=======东莞地区=======================

    //企业的CorpId
    public static String APPID = "ww24261f29f0d4837c";
    //员工推荐
    public static String CROPSECRET = "CQbSPgpGxO3RVvtTIXEO2fjb52CmUMdFwzo73_NVzos";

    public static String AGENTID = "1000040";


    //=======重庆地区=======================

    //企业的CorpId
    public static String APPID_CQ = "ww2e7c364465a181ad";
    //员工推荐
    public static String CROPSECRET_CQ = "xnDrvO-lUIX17NOJTVuryPFXmNkiBQWpdwU7NWL21PU";

    public static String AGENTID_CQ = "1000018";

    //=======昆山地区=======================
    //企业的CorpId
    public static String APPID_KS = "ww6702157cf83bd8ff";
    //员工推荐
    public static String CROPSECRET_KS = "oBZJW055Xr7xotI7HtkvL3_g7TN4iqFDBFG6yb7B-qk";

    public static String AGENTID_KS = "1000014";
}
