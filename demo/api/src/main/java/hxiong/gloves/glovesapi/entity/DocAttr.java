package hxiong.gloves.glovesapi.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@TableName(value = "t_doc_attr")
@ApiModel
public class DocAttr {
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

  @ApiModelProperty(value = "组件类型", hidden = false)
  private String type;

  @ApiModelProperty(value = "字体颜色", hidden = false)
  private String color;

  @ApiModelProperty(value = "默认值", hidden = false)
  private String defaultValue;

  @ApiModelProperty(value = "字体大小", hidden = false)
  private String fontSize;

  @ApiModelProperty(value = "水平对齐方式", hidden = false)
  private String textAlign;

  @ApiModelProperty(value = "文本类型", hidden = false)
  private String textType;

  @ApiModelProperty(value = "最小宽度", hidden = false)
  private String width;

  @ApiModelProperty(value = "控件名称", hidden = false)
  private String nodeName;

  @ApiModelProperty(value = "校验正则", hidden = false)
  private String regex;

  @ApiModelProperty(value = "控件样式", hidden = false)
  private String style;

  @ApiModelProperty(value = "得分控件分值", hidden = false)
  private Float score;

  @ApiModelProperty(value = "计划编号", hidden = false)
  private String plan;

  @ApiModelProperty(value = "选项模式", hidden = false)
  private String mode;

  @ApiModelProperty(value = "是否必填", hidden = false)
  private Byte required;

  @ApiModelProperty(value = "关联选项", hidden = false)
  @TableField(exist = false)
  private List<DocOptions> options = new ArrayList<>();

  public DocAttr() {

  }
}