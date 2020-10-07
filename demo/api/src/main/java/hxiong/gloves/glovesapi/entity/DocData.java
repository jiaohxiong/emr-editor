package hxiong.gloves.glovesapi.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@TableName(value = "t_doc_data")
@ApiModel
public class DocData {
  // 主键
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "id", hidden = true)
  private Integer id;

  @ApiModelProperty(value = "文档标识符", hidden = false)
  private String docId;

  @ApiModelProperty(value = "版本号", hidden = false)
  private Byte version;

  @ApiModelProperty(value = "组件标识符", hidden = false)
  private String nodeId;

  @ApiModelProperty(value = "数据名称", hidden = false)
  private String name;

  @ApiModelProperty(value = "数据值", hidden = false)
  private String value;

  @ApiModelProperty(value = "组件类型", hidden = false)
  private String type;

  @ApiModelProperty(value ="正则表达式", hidden = false)
  private String regex;

  private String groupName;
  private String totalGroupName;

  @ApiModelProperty(value = "数据源", hidden = false)
  private String sourceId;

  private String plan;

  @TableField(exist = false)
  @ApiModelProperty(value = "组件样式", hidden = false)
  private DocAttr attr;

  public DocData() {

  }
}