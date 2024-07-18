package com.primax.service.referral;

import com.primax.mapper.db1.CodeDepartmentMapper;
import com.primax.model.CodeDepartment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CodeDepartmentService {

    @Resource
    private CodeDepartmentMapper codeDepartmentMapper;

    public CodeDepartment getCodedepartment(String empNo){

        CodeDepartment codedepartment = codeDepartmentMapper.getCodedepartment(empNo);

        return codedepartment;
    }

    public CodeDepartment getCodedepartmentByDeptNo(String deptNo) {

        CodeDepartment codedepartment = codeDepartmentMapper.getCodedepartmentByDeptNo(deptNo);

        return codedepartment;
    }

    public List<String> getCodedepartmentByParentDeptNo(String deptNo) {

        List<String> codedepartments = codeDepartmentMapper.getCodedepartmentByParentDeptNo(deptNo);

        return codedepartments;
    }

}
