package com.primax.model;

import lombok.Data;

import java.util.Date;

@Data
public class CodeDepartment {

  private Integer departmentId;
  private Integer buId;
  private String departmentCode;
  private String departmentName;
  private String costCenter;
  private String isEnable;
  private Date stopDate;
  private Integer parentId;
  private String parentDeptNo;
  private Integer grade;
  private Integer showOrder;
  private String masterCode;
  private String masterName;
  private String proxyCode;
  private String proxyName;
  private Date ValidTime;
  private Date ValidUntil;
  private Integer userId;
  private String updateUser;
  private Date updateDate;
  private String remark;

}
