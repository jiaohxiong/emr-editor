package hxiong.gloves.glovesapi.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("")
public class PatientDocInfo {
  
  @ApiModelProperty(value = "")
  private Integer id;

  @ApiModelProperty(value = "")
  private String docId;

  @ApiModelProperty(value = "")
  private Byte docVersion;

  @ApiModelProperty(value = "文档名称")
  private String docName;

  private String docType;

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

  @ApiModelProperty(value = "")
  private String patientId;

  @ApiModelProperty(value = "")
  private String lastUpdateUserId;

  @ApiModelProperty(value = "")
  private String lastUpateUserName;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "")
  private Date lastUpdateTime;

  @TableField(exist = false)
  @ApiModelProperty(value = "依赖数据")
  private List<PatientData> data;

  @TableField(exist = false)
  private Map<String, List<PatientData>> plans;
  
  @TableField(exist = false)
  private Map<String,String> params;

  public PatientDocInfo() {

  }
}