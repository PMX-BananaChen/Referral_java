package com.primax.model;

import lombok.Data;

import java.util.Date;

@Data
public class CodeBu {

  private long buid;
  private String buCode;
  private String buName;
  private String shortName;
  private long parentBu;
  private String prefixCode;
  private long showOrder;
  private long grade;
  private String isEnable;
  private Date stopDate;
  private long userId;
  private String updateUser;
  private Date updateDate;
  private String remark;
  private long topBu;
  private String buString;
  private String farther1;
  private String farther2;
  private String farther3;
  private String subBuString;

}
