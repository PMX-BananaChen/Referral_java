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
@TableName(value = "RecommendationForm")
public class RecommendationForm {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @TableId(value = "RecommendationID" ,type= IdType.AUTO)
  private Integer recommendationID;

  @TableField(value = "BUID")
  private Integer buId;

  @TableField(value = "FormNo")
  private String formNo;

  @TableField(value = "FormState")
  private Integer formState;

  @TableField(value = "ApplyDate")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date applyDate;

  @TableField(value = "Area")
  private String area;

  @TableField(value = "IntroducedName")
  private String introducedName;

  @TableField(value = "IntroducedIDNo")
  private String introducedIdNo;

  @TableField(value = "IntroducedSex")
  private String introducedSex;

  //性别男女
  @TableField(exist = false)
  private String introducedSex1;

  @TableField(value = "IntroducedBirthDate")
  @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
  private Date introducedBirthDate;

  @TableField(value = "IntroducedTel")
  private String introducedTel;

  @TableField(value = "Relationship")
  private Integer relationship;

  @TableField(exist = false)
  private String relationshipName;

  @TableField(value = "IntroducedType")
  private String introducedType;

  @TableField(value = "IntroducerEmpNo")
  private String introducerEmpNo;

  @TableField(value = "IntroducerEmpName")
  private String introducerEmpName;

  @TableField(value = "IntroducerDeptNo")
  private String introducerDeptNo;

  @TableField(exist = false)
  private String introducerDeptName;

  @TableField(value = "IntroducerCostCenter")
  private String introducerCostCenter;

  @TableField(value = "IntroducerOutDate")
  private String introducerOutDate;

  @TableField(value = "IntroducerType")
  private String introducerType;

  @TableField(value = "IntroducerTel")
  private String introducerTel;

  @TableField(value = "CheckState")
  private String checkState;

  @TableField(value = "CheckName")
  private String checkName;

  //下一步签核人
  @TableField(exist = false)
  private String checkName1;

  @TableField(value = "CheckDate")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date checkDate;

  @TableField(value = "UpdateUser")
  private String upDateUser;

  @TableField(value = "UpdateDate")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date upDateDate;

  @TableField(value = "Remark")
  private String remark;

  @TableField(value = "SendEmpNo")
  private String sendEmpNo;
}
