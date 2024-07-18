package com.primax.service.referral;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.primax.mapper.db1.EmployeeMapper;
import com.primax.model.Employee;
import com.primax.model.WxEmployeeUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;


    //查询员工信息
    public Employee getEmployee(String empNo) {

        Employee employee = employeeMapper.selectOne(empNo);

        return employee;
    }

    //查询员工入职记录
    public Integer getEmpRecord(String introducedIDNo) {

        Integer record = employeeMapper.getEmpRecord(introducedIDNo);

        return record;
    }

    //查询所有在职员工
    public List<Employee> getEmpList() {

        List<Employee> list = employeeMapper.getEmpList();

        return list;
    }

    //查询员工信息
    public Employee getEmployee1(String empNo) {

        Employee employee = employeeMapper.selectTwo(empNo);

        return employee;
    }
}
