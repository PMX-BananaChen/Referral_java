package com.primax.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "HR_Employee")
public class Employee {

    @TableField(value = "Emp_No")
    private String empNo;

    @TableField(value = "Emp_Name")
    private String empName;

    @TableField(value = "Cost_Center")
    private String costCenter;

    @TableField(value = "Dept_No")
    private String deptNo;

    @TableField(value = "Dept_Name")
    private String deptName;

    @TableField(value = "MobilePhone")
    private String MobilePhone;

    @TableField(value = "Emp_Type")
    private String empType;

    @TableField(value = "Emp_OutDate")
    private String empOutDate;

    @TableField(value = "Area")
    private String Area;

    @TableField(value = "Emp_Title")
    private String empTitle;

    @TableField(value = "Emp_Title_Name")
    private String empTitleName;

    @TableField(exist = false)
    private String userId;

}
