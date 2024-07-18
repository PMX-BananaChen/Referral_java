package com.primax.mapper.db1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.primax.model.WxEmployeeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WxEmployeeUserMapper extends BaseMapper<WxEmployeeUser> {

    @Select("select top 1 * from [10.40.1.112].[ElectronicRelease].[dbo].[WX_Employee_User] where work_user_id =#{userId} order by create_time DESC")
    WxEmployeeUser selectPmxOne(String userId);

    @Select("select top 1 * from [10.40.1.112].[ElectronicRelease].[dbo].[WX_Employee_User] where employee_code =#{empNo} order by create_time DESC")
    WxEmployeeUser selectOne(String empNo);
}
