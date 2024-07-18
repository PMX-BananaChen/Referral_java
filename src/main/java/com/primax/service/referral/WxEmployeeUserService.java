package com.primax.service.referral;

import com.primax.mapper.db1.WxEmployeeUserMapper;
import com.primax.model.WxEmployeeUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 员工service
 */
@Service
public class WxEmployeeUserService {

    private static final Logger logger = LoggerFactory.getLogger(WxEmployeeUserService.class);

    @Resource
    private WxEmployeeUserMapper weUserMapper;


    /**
     * 东聚获取授权数据
     *
     * @param userId
     * @return
     */
    public WxEmployeeUser getWxEmployeeUserByPmx(String userId) {
        WxEmployeeUser wxEmployeeUser=new WxEmployeeUser();

        wxEmployeeUser =weUserMapper.selectPmxOne(userId);

        return wxEmployeeUser;
    }

    /**
     * 东聚获取授权数据
     *
     * @param empNo
     * @return
     */
    public WxEmployeeUser getWxEmpByPmx(String empNo) {
        WxEmployeeUser wxEmployeeUser=new WxEmployeeUser();

        wxEmployeeUser =weUserMapper.selectOne(empNo);

        return wxEmployeeUser;
    }

}
