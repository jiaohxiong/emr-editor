package hxiong.gloves.glovesapi.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "t_patient_doc")
@ApiModel("")
public class TypePatientDoc {

  @ApiModelProperty(value = "")
  private String docId;

  private String docName;

  private String docType;

  @ApiModelProperty(value = "")
  private Byte docVersion;

  @ApiModelProperty(value = "")
  private String patientId;

  @ApiModelProperty(value = "文本编号")
  private String patientDocId;

  @ApiModelProperty(value = "")
  private Byte lastPatientDocVersion;

  @ApiModelProperty(value = "")
  private String createUserId;

  @ApiModelProperty(value = "")
  private String createUserName;

  @ApiModelProperty(value = "")
  private String lastUpateUserName;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "")
  private Date lastUpdateTime;

}