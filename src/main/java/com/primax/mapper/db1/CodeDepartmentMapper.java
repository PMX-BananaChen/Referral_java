package com.primax.mapper.db1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.primax.model.CodeDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CodeDepartmentMapper extends BaseMapper<CodeDepartment> {

    @Select("SELECT TOP 1 * FROM Code_Department WHERE MasterCode = #{empNo} or ProxyCode = #{empNo}")
    CodeDepartment getCodedepartment(String empNo);

    @Select("SELECT TOP 1 * FROM Code_Department WHERE DepartmentCode = #{deptNo}")
    CodeDepartment getCodedepartmentByDeptNo(String deptNo);

    @Select("SELECT  DepartmentCode FROM Code_Department WHERE ParentDeptNo = #{deptNo}")
    List<String> getCodedepartmentByParentDeptNo(String deptNo);

}
