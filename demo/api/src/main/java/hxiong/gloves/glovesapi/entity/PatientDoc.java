package hxiong.gloves.glovesapi.entity;

import java.util.Date;
import java.util.List;

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
@TableName(value = "t_patient_doc")
@ApiModel("")
public class PatientDoc {

  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "")
  private Integer id;

  @ApiModelProperty(value = "")
  private String patientDocId;

  @ApiModelProperty(value = "")
  private Byte lastPatientDocVersion;

  @ApiModelProperty(value = "")
  private String docId;

  @ApiModelProperty(value = "")
  private Byte docVersion;

  private String docType;
  private String docName;

  @ApiModelProperty(value = "")
  private String patientId;

  @ApiModelProperty(value = "")
  private Date createTime;

  @ApiModelProperty(value = "")
  private String createUserId;

  @ApiModelProperty(value = "")
  private String createUserName;

  @ApiModelProperty(value = "")
  private String lastUpdateUserId;

  @ApiModelProperty(value = "")
  private String lastUpateUserName;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "")
  private Date lastUpdateTime;

  @TableField(exist = false)
  private List<PatientData> data;

  @TableField(exist = false)
  private List<PatientData> plans;

  public PatientDoc() {

  }
}