package com.primax.mapper.db1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.primax.model.Employee;
import com.primax.model.WxEmployeeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    @Select("select top 1 * from [HRDataSync].[dbo].[HR_Employee] where emp_no =#{empNo}")
    Employee selectOne(String empNo);

    /**
     * 查询员工是否离职满三月 未满返回1
     * @param introducedIDNo
     * @return integer
     */
    //@Select("select COUNT(Emp_Serial_ID) from [HRDataSync].[dbo].[HR_Employee] where Emp_Serial_ID =#{introducedIDNo} and Emp_OutDate = '9999-12-31'")
    @Select("SELECT COUNT\n" +
            "\t( Emp_Serial_ID ) \n" +
            "FROM\n" +
            "\t[HRDataSync].[dbo].[HR_Employee] \n" +
            "WHERE\n" +
            "\tEmp_Serial_ID = #{introducedIDNo} \n" +
            "\tand Emp_OutDate  > dateadd(DAY,-90,getdate())")
    Integer getEmpRecord(String introducedIDNo);

    @Select("select * from [HRDataSync].[dbo].[HR_Employee] where  Emp_OutDate = '9999-12-31' and Dept_Name != 'TW' order by Area")
    List<Employee> getEmpList();

    @Select("select top 1 * from [HRDataSync].[dbo].[HR_Employee] where emp_no =#{empNo} and Emp_Title not in ('L11','L11-1','L11-2','L12','L12-1','L12-1','L12-2','L13','L13-1','L13-2')")
    Employee selectTwo(String empNo);

}
