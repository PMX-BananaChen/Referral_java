package com.primax.config;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.primax.config.CommonUtil.httpsRequest;

//import com.thinkgem.jeesite.wx.entity.memberInfo;

/**
 * Created by admin on 2017/10/20.
 */
public class WeixinHelper {

    private static final Logger logger = LoggerFactory.getLogger(WeixinHelper.class);

    /**
     * 获取网页授权凭证
     *
     * @param appId     公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @return WeixinAouth2Token
     */
    public static Wxoauth2token getOauth2AccessToken(String appId, String appSecret) {
        Wxoauth2token wat = null;
        // 拼接请求地址
        String requestUrl = WeixinConfig.GET_ACCESS_TOKEN;
        requestUrl = requestUrl.replace("ID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);

        // 获取网页授权凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        logger.info("-----------------------------查询token唯一标识：++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.info("url<============url============>getOauth2AccessToken:"+requestUrl);
        logger.info("json<===========json============>getOauth2AccessToken：" + jsonObject.toString());
        if (null != jsonObject) {
            try {
                wat = new Wxoauth2token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(7200);
            } catch (Exception e) {
                wat = null;
            }
        }
        return wat;
    }


    /**
     * 通过网页授权获取成员信息
     *
     * @param access_token 网页授权接口调用凭证
     * @param code         登录code
     * @return memberInfo
     */
    public static MemberInfo getMemberInfo(String access_token, String code) {
        MemberInfo memberInfo = null;
        //拼接请求地址
        String requestUrl = WeixinConfig.GET_USER_INFO;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token);
        requestUrl = requestUrl.replace("CODE", code);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        logger.info("-----------------------------查询user唯一标识：++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.info(requestUrl);
        logger.info(jsonObject.toString());
        if (null != jsonObject) {
            try {
                memberInfo = new MemberInfo();
                if (Integer.parseInt(jsonObject.getString("errcode")) == 0 && jsonObject.getString("errmsg").equals("ok")) {
                    //返回码成功
                    memberInfo.setErrCode(0);
                    memberInfo.setErrMsg(jsonObject.getString("errmsg"));
                    memberInfo.setDeviceId(jsonObject.getString("DeviceId"));

                    if (StringUtils.isNotBlank(jsonObject.getString("UserId"))) {
                        logger.info("jsonObject.getString(UserId):" + jsonObject.getString("UserId"));

                        //说明是内部成员，可以通过userid获取成员信息
                        memberInfo.setUserId(jsonObject.getString("UserId"));
                        return memberInfo;
                    } else if (StringUtils.isNotBlank(jsonObject.getString("OpenId"))) {
                        logger.info("jsonObject.getString(OpenId):" + jsonObject.getString("OpenId"));
                        //说明返回的是第二种结果，不是内部成员
                        String userId = Openid2userId(access_token, jsonObject.getString("OpenId"));
                        memberInfo.setUserId(userId);
                        return memberInfo;
                    } else {
                        //返回出错
                        return memberInfo;
                    }
                } else {
                    return memberInfo;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return memberInfo;
        }
    }

    /**
     * openid与userId互换
     * Openid2userId
     *
     * @param OpenId       企业内的成员id
     * @param access_token 调用接口凭证
     * @return WeixinAouth2Token
     */
    public static String Openid2userId(String access_token, String OpenId) {
        String userid = "";
        String requestUrl = WeixinConfig.OPENID_TO_USEID;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token);
        //请求参数
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("openid", OpenId);
        // 获取网页授权凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "POST", jsonObject1.toString());
        logger.info("{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}");
        logger.info("获取网页凭证json数据的值是：" + jsonObject.toString());
        logger.info("获取网页凭证json数据的值是：" + jsonObject.toString());
        logger.info("获取网页凭证json数据的值是：" + jsonObject.toString());
        if (null != jsonObject) {
            try {
                userid = jsonObject.getString("userid");
            } catch (Exception e) {
                userid = null;
            }
        }
        return userid;
    }
}














