package com.primax.mapper.db1;

import com.primax.model.CodeHr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CodeHrMapper {
    @Select("SELECT TOP 1 * FROM Code_HR WHERE HrEmpNo =  #{empNo}")
    CodeHr getCodeHr(String empNo);

    @Select("SELECT  * FROM Code_HR WHERE HrEmpNo =  #{empNo}")
    List<CodeHr> getCodeHrs(String empNo);

    @Select("SELECT * FROM Code_HR WHERE BUID =#{buId} AND EmpType =#{empType}")
    List<CodeHr> getCodeHrByBuId(Integer buId, String empType);
}
