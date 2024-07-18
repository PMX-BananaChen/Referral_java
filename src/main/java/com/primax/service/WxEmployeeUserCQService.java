package com.primax.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.primax.mapper.db2.WxEmployeeUserCQMapper;
import com.primax.model.WxEmployeeUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WxEmployeeUserCQService {

    private static final Logger logger = LoggerFactory.getLogger(WxEmployeeUserCQService.class);

    @Resource
    private WxEmployeeUserCQMapper cqMapper;

    /**
     * 东聚获取授权数据
     *
     * @param userId
     * @return
     */
    public WxEmployeeUser getWxEmployeeUserByCQ(String userId) {

        WxEmployeeUser wxEmployeeUser =cqMapper.selectOne(
                new QueryWrapper<WxEmployeeUser>()
                        .eq("work_user_id", userId)
                        .eq("status",1)
                        .orderByDesc("create_time")
        );

        return wxEmployeeUser;
    }

    /**
     * 重庆获取授权数据
     *
     * @param empNo
     * @return
     */
    public WxEmployeeUser getWxEmpByCQ(String empNo) {

        WxEmployeeUser wxEmployeeUser =cqMapper.selectOne(
                new QueryWrapper<WxEmployeeUser>()
                        .eq("employee_code", empNo)
                        .eq("status",1)
                        .orderByDesc("create_time")
        );

        return wxEmployeeUser;
    }
}
