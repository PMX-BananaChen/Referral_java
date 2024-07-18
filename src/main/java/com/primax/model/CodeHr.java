package com.primax.model;

import lombok.Data;

import java.util.Date;

@Data
public class CodeHr {

  private Integer hrid;
  private Integer buid;
  private String empType;
  private String hrEmpNo;
  private String hrEmpName;
  private String hrEmpEName;
  private String hrEmpEmail;
  private String isEnable;
  private Integer userId;
  private String updateUser;
  private Date updateDate;
  private String remark;



}
