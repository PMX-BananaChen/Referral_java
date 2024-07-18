package com.primax.service.referral;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.primax.mapper.db1.FormCheckMapper;
import com.primax.model.FormCheck;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FormCheckService {

    @Resource
    private FormCheckMapper formCheckMapper;

    /**
     * 查询推荐审核单详情
     * @param formNo
     */
    public FormCheck getFormCheck(String formNo) {

        return formCheckMapper.getFormCheck(formNo);
    }

    /**
     * 插入推荐审核单数据
     * @param check
     * @return
     */
    public Integer insert(FormCheck check) {

        return formCheckMapper.insert(check);
    }

    /**
     * 更新推荐审核单数据
     * @param check
     * @return
     */
    public int update(FormCheck check,Integer checkId) {
        int i = formCheckMapper.update(check,
                new QueryWrapper<FormCheck>()
                        .eq("CheckID", checkId)
        );
        return i;
    }
}
