package com.primax.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "WX_Employee_User")
public class WxEmployeeUser {

    private Integer id;

    //员工工号
    private String employeeCode;

    //员工绑定企业微信id
    private String workUserId;

    //绑定时间戳
    private Integer createTime;

    //绑定状态 1:绑定成功
    private Integer status;

}
