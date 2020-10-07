package hxiong.gloves.glovesapi.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 黄卫平
 * @date 2020-07-27 08:57:41
 * @email
 */
@Data
@AllArgsConstructor
@TableName(value = "t_patient_doc_list")
@ApiModel("")
public class PatientDocList {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "")
  private Integer id;

  @TableField(exist = false)
  @ApiModelProperty(value = "")
  private String docId;

  @TableField(exist = false)
  @ApiModelProperty(value = "")
  private Byte docVersion;

  @ApiModelProperty(value = "")
  private String patientDocId;

  @ApiModelProperty(value = "")
  private Byte patientDocVersion;

  @ApiModelProperty(value = "")
  private String html;

  @ApiModelProperty(value = "")
  private String structure;

  @ApiModelProperty(value = "")
  private String createUserId;

  @ApiModelProperty(value = "")
  private String createUserName;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "")
  private Date createTime;

  public PatientDocList() {

  }
}