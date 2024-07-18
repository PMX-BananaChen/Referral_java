package com.primax.mapper.db1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.primax.model.RecommendationForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReferralMapper extends BaseMapper<RecommendationForm> {

    @Select("SELECT TOP 1 * FROM RecommendationForm ORDER BY RecommendationID DESC")
    RecommendationForm selectOne();

    @Select("SELECT * FROM RecommendationForm WHERE IntroducerDeptNo = #{departmentCode} AND FormState IN #{status} ORDER BY RecommendationID DESC")
    List<RecommendationForm> getRecommendationFormList(String departmentCode, String status);

    @Select("SELECT TOP 1* FROM RecommendationForm WHERE FormNo = #{formNo} ORDER BY RecommendationID DESC")
    RecommendationForm getForm(String formNo);

    @Select("SELECT * FROM RecommendationForm WHERE BUID = #{buid} AND FormState IN #{status} ORDER BY RecommendationID DESC")
    //& status != "0" & status != "-1"
    List<RecommendationForm> getFormListByHR(Integer buid, String status);

    @Select("SELECT * FROM RecommendationForm WHERE IntroducerEmpNo = #{empNo} AND FormState IN (#{arr}) ORDER BY RecommendationID DESC")
    List<RecommendationForm> getFormList(String empNo, int[] arr );

    @Select("SELECT COUNT(IntroducedIDNo) FROM RecommendationForm WHERE IntroducedIDNo = #{introducedIDNo} AND FormState NOT IN ('-1','-2','2') ")
    Integer getFormRecord(String formNo);
}
