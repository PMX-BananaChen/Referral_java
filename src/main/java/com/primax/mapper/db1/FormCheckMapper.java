package com.primax.mapper.db1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.primax.model.FormCheck;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FormCheckMapper extends BaseMapper<FormCheck> {

    @Select("SELECT TOP 1 * FROM FormCheck WHERE FormNo = #{formNo} ORDER BY CheckID DESC ")
    FormCheck getFormCheck(String formNo);
}
