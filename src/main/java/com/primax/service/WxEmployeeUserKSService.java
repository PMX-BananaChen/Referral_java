package com.primax.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.primax.mapper.db2.WxEmployeeUserCQMapper;
import com.primax.mapper.db3.WxEmployeeUserKSMapper;
import com.primax.model.WxEmployeeUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WxEmployeeUserKSService {
    private static final Logger logger = LoggerFactory.getLogger(WxEmployeeUserKSService.class);

    @Resource
    private WxEmployeeUserKSMapper ksMapper;

    /**
     * 昆山获取授权数据
     *
     * @param userId
     * @return
     */
    public WxEmployeeUser getWxEmployeeUserByKS(String userId) {

        WxEmployeeUser wxEmployeeUser =ksMapper.selectOne(
                new QueryWrapper<WxEmployeeUser>()
                        .eq("work_user_id", userId)
                        .eq("status",1)
                        .orderByDesc("create_time")
        );

        return wxEmployeeUser;
    }

    /**
     * 昆山获取授权数据
     *
     * @param empNo
     * @return
     */
    public WxEmployeeUser getWxEmpByKS(String empNo) {

        WxEmployeeUser wxEmployeeUser =ksMapper.selectOne(
                new QueryWrapper<WxEmployeeUser>()
                        .eq("employee_code", empNo)
                        .eq("status",1)
                        .orderByDesc("create_time")
        );

        return wxEmployeeUser;
    }
}
