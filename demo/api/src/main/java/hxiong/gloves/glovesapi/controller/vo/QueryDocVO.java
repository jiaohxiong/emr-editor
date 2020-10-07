package hxiong.gloves.glovesapi.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class QueryDocVO {
  @ApiModelProperty(value = "文档标识符")
  private String docId;

  @ApiModelProperty(value = "版本号")
  private Byte version;

  @ApiModelProperty(value = "患者ID")
  private String patientId;

  @ApiModelProperty(value = "患者文档ID")
  private String patientDocId;

  @ApiModelProperty(value = "版本号")
  private Byte patientDocVersion;

  @ApiModelProperty(value = "模版类型")
  private String type;

  @ApiModelProperty(value = "类型名称")
  private String typeName;

  @ApiModelProperty(value = "文档名称")
  private String name;

  public QueryDocVO() {

  }
}