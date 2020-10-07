package hxiong.gloves.glovesapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
@TableName("t_doc_control")
public class DataControl {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "id", hidden = true)
  private Integer id;

  @ApiModelProperty(value = "控件名称", hidden = true)
  private String name;

  @ApiModelProperty(value = "")
  private String color;

  @ApiModelProperty(value = "")
  private String fontSize;
  
  @ApiModelProperty(value = "")
  private String regex;

  @ApiModelProperty(value = "")
  private String width;

  @ApiModelProperty(value = "")
  private String textType;

  @ApiModelProperty(value = "")
  private String textAlign;

  @ApiModelProperty(value = "")
  private String defaultValue;

  @ApiModelProperty(value = "")
  private String sourceId;

  @ApiModelProperty(value = "")
  private String sourceName;

  @ApiModelProperty(value = "")
  private String deptId;

  @ApiModelProperty(value = "")
  private String deptName;

  @ApiModelProperty(value = "")
  private String mode;

  @ApiModelProperty(value = "")
  private String createUserId;

  @ApiModelProperty(value = "")
  private String createUserName;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "")
  private Date createTime;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "")
  private Date lastUpdateTime;

  @ApiModelProperty(value = "")
  private String lastUpdateUserId;

  @ApiModelProperty(value = "")
  private String lastUpdateUserName;

  private String typeId;
  private String typeName;
  private String typeCommand;
  
  @TableField(exist = false)
  private List<DataControlOption> options;

  private String controlId;

  public DataControl() {

  }
}