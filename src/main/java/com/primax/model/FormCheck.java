package com.primax.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Data
@TableName(value = "FormCheck")
public class FormCheck {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @TableId(value = "CheckID",type= IdType.AUTO)
  private Integer CheckID;

  @TableField(value = "FormID")
  private Integer formID;

  @TableField(value = "Grade")
  private Integer grade;

  @TableField(value = "CheckName")
  private String checkName;

  @TableField(value = "CheckResult")
  private String checkResult;

  @TableField(value = "CheckRemark")
  private String checkRemark;

  @TableField(value = "FormNo")
  private String formNo;

  @TableField(value = "Category")
  private String category;

  @TableField(value = "CheckManID")
  private Integer checkManID;

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  @TableField(value = "CheckTime")
  private Date checkTime;

}
