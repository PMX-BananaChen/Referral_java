package com.primax.controller;

import com.primax.config.*;
import com.primax.model.Employee;
import com.primax.model.Result;
import com.primax.model.WxEmployeeUser;
import com.primax.service.*;
import com.primax.service.referral.CodeDepartmentService;
import com.primax.service.referral.EmployeeService;
import com.primax.service.referral.WxEmployeeUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/pmx/referral/OAuth2")
@RestController
public class WxOAuth2Rest {

    private static final Logger logger = LoggerFactory.getLogger(WxOAuth2Rest.class);

    /**
     * 员工
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * 东莞企业微信员工service
     */
    @Autowired
    private WxEmployeeUserService wxEmployeeUserService;

    /**
     * 重庆企业微信员工service
     */
    @Autowired
    private WxEmployeeUserCQService wxEmployeeUserCQService;

    /**
     * 东莞企业微信员工service
     */
    @Autowired
    private WxEmployeeUserKSService wxEmployeeUserKSService;

    /**
     * 部门
     */
    @Autowired
    private CodeDepartmentService codeDepartmentService;

    /**
     * 通过登录code,access_token获取用户的信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/authorizeByDG")
    public Result authorizeByDG(HttpServletRequest request, HttpSession session) throws  Exception{
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);
        try {
            //获取登录code
            String code = request.getParameter("code");
            logger.info("authorizeByDG<===================================>authorizeByDG");
            String cropsecret = WeixinConfig.CROPSECRET;
            Wxoauth2token wat = WeixinHelper.getOauth2AccessToken(WeixinConfig.APPID,cropsecret);
            String access_token=wat.getAccessToken();

            //缓存accessToken
            EhcacheUtil.put("referralForm", "accessTokenPMX", wat.getAccessToken());

            //获取用户信息
            MemberInfo userInfo=WeixinHelper.getMemberInfo(access_token,code);
            logger.info("userInfo========>"+userInfo.toString());
            String userId = userInfo.getUserId();
            if (userId == null && userInfo.getOpenId() != null){
                r.setCode(Result.RESULT_NO_AUTHORITY);
                r.setMsg("请绑定企业微信后重试");
            }else if(userId != null && userInfo.getOpenId() == null){
                WxEmployeeUser user = wxEmployeeUserService.getWxEmployeeUserByPmx(userId);
                if (user == null) {
                    return new Result(Result.RESULT_FAILURE, "获取员工信息失败");
                }
                //获取员工工号
                Employee emp = employeeService.getEmployee(user.getEmployeeCode());

                emp.setUserId(userId);
                r.setData(emp);
                r.setCode(Result.RESULT_SUCCESS);
                r.setMsg(Result.RESULT_SUCCESS_MSG);
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return r;
    }


    /**
     * 重庆通过登录code,access_token获取用户的信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/authorizeByCQ")
    public Result authorizeByCQ(HttpServletRequest request, HttpSession session) throws  Exception{
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);
        try {
            //获取登录code
            String code = request.getParameter("code");
            logger.info("authorizeByCQ<===================================>authorizeByCQ");
            Wxoauth2token wat = WeixinHelper.getOauth2AccessToken(WeixinConfig.APPID_CQ,WeixinConfig.CROPSECRET_CQ);

            String access_token=wat.getAccessToken();

            //缓存accessToken
            EhcacheUtil.put("referralForm", "accessTokenCQ", wat.getAccessToken());

            //获取用户信息
            MemberInfo userInfo=WeixinHelper.getMemberInfo(access_token,code);
            logger.info("userInfo========>"+userInfo.toString());
            String userId = userInfo.getUserId();
            if (userId == null && userInfo.getOpenId() != null){
                r.setCode(Result.RESULT_NO_AUTHORITY);
                r.setMsg("请绑定企业微信后重试");
            }else if(userId != null && userInfo.getOpenId() == null){
                WxEmployeeUser user = wxEmployeeUserCQService.getWxEmployeeUserByCQ(userId);
                if (user == null) {
                    return new Result(Result.RESULT_FAILURE, "获取员工信息失败");
                }
                //获取员工工号
                Employee emp = employeeService.getEmployee(user.getEmployeeCode());

                emp.setUserId(userId);
                r.setData(emp);
                r.setCode(Result.RESULT_SUCCESS);
                r.setMsg(Result.RESULT_SUCCESS_MSG);
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return r;
    }

    /**
     * 昆山通过登录code,access_token获取用户的信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/authorizeByKS")
    public Result authorizeByKS(HttpServletRequest request, HttpSession session) throws  Exception{
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);
        try {
            //获取登录code
            String code = request.getParameter("code");
            logger.info("authorizeByKS<===================================>authorizeByKS");
            Wxoauth2token wat = WeixinHelper.getOauth2AccessToken(WeixinConfig.APPID_KS,WeixinConfig.CROPSECRET_KS);

            String access_token=wat.getAccessToken();

            //缓存accessToken
            EhcacheUtil.put("referralForm", "accessTokenKS", wat.getAccessToken());

            //获取用户信息
            MemberInfo userInfo=WeixinHelper.getMemberInfo(access_token,code);
            logger.info("userInfo========>"+userInfo.toString());
            String userId = userInfo.getUserId();
            if (userId == null && userInfo.getOpenId() != null){
                r.setCode(Result.RESULT_NO_AUTHORITY);
                r.setMsg("请绑定企业微信后重试");
            }else if(userId != null && userInfo.getOpenId() == null){
                WxEmployeeUser user = wxEmployeeUserKSService.getWxEmployeeUserByKS(userId);
                if (user == null) {
                    return new Result(Result.RESULT_FAILURE, "获取员工信息失败");
                }
                //获取员工工号
                Employee emp = employeeService.getEmployee(user.getEmployeeCode());

                emp.setUserId(userId);
                r.setData(emp);
                r.setCode(Result.RESULT_SUCCESS);
                r.setMsg(Result.RESULT_SUCCESS_MSG);
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return r;
    }
}
