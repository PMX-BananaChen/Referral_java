package com.primax.service.referral;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.primax.mapper.db1.ReferralMapper;
import com.primax.model.RecommendationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReferralService {

    private static final Logger logger = LoggerFactory.getLogger(ReferralService.class);

    @Resource
    private ReferralMapper referralMapper;

    public void addVisitorRecord (RecommendationForm form){
        referralMapper.insert(form);
    }

    /**
     * 流水单号
     * @return
     */
    public String selectSerialNumber() {
        RecommendationForm form =referralMapper.selectOne();
        if(form == null){return null;}
        return form.getFormNo();
    }

    /**
     *员工查询内推列表
     * @param empNo
     * @param a
     * @return
     */
    public List<RecommendationForm> getFormList(String empNo, int[] a) {

//        List<RecommendationForm> forms = referralMapper.getFormList(empNo,arr);

        List<RecommendationForm> forms = referralMapper.selectList(
                new QueryWrapper<RecommendationForm>()
                        .eq("IntroducerEmpNo", empNo).in("FormState",a[0],a[1])
                        .orderByDesc("RecommendationID")
        );
        return forms;
    }

    /**
     *签核者经理查询推荐列表
     * @param empNo
     * @param a
     * @return
     */
    public List<RecommendationForm> getFormListBySign(String empNo, int [] a) {

//        List<RecommendationForm> forms=referralMapper.getRecommendationFormList(departmentCode,status);
        List<RecommendationForm> forms = referralMapper.selectList(
                new QueryWrapper<RecommendationForm>()
                        .in("sendEmpNo", empNo)
                        .in("FormState",a[0],a[1],a[2])
                        .orderByDesc("RecommendationID")
        );
        return forms ;
    }

    /**
     * 签核者HR查询推荐列表
     * @param
     * @param
     * @return
     */
    public List<RecommendationForm> getFormListByHR(Integer[] buIds , String [] empTypes1 ,int [] a) {

//        List<RecommendationForm> forms=referralMapper.getFormListByHR(buid,status);
        List<RecommendationForm> forms = referralMapper.selectList(
                new QueryWrapper<RecommendationForm>()
                        .in("BUID", buIds)
                        .in("IntroducedType", empTypes1)
                        .in("FormState",a[0],a[1])
                        .orderByDesc("RecommendationID")
        );
        return forms ;
    }

    /**
     * 查询推荐单
     * @param formNo
     * @return
     */
    public RecommendationForm getForm(String formNo) {

        RecommendationForm form = referralMapper.getForm(formNo);

        return form;
    }

    /**
     * 查询推荐单的该被推荐人记录
     * @param introducedIDNo
     * @return
     */
    public Integer getFormRecord(String introducedIDNo) {

        Integer form = referralMapper.getFormRecord(introducedIDNo);

        return form;
    }

    /**
     * 更新内推单数据
     * @param form
     * @return
     */
    public Integer updateForm(RecommendationForm form) {
        return referralMapper.updateById(form);
    }


}
