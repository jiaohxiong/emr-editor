package hxiong.gloves.glovesapi.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "t_doc")
@ApiModel
public class Doc {

  // 主键
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "id", hidden = true)
  private Integer id;

  @ApiModelProperty(value = "文档标识符", hidden = false)
  private String docId;

  // 描述
  @ApiModelProperty(value = "文档名称", hidden = false)
  private String name;

  @ApiModelProperty(value = "文档类型", hidden = false)
  private String type;
  
  @ApiModelProperty(value = "文档类型", hidden = false)
  private String typeName;
  
  // 内容
  @ApiModelProperty(value = "文档内容", hidden = false)
  private String html;

  @ApiModelProperty(value = "数据结构", hidden = false)
  private String structure;

  @ApiModelProperty(value = "版本号", hidden = false)
  private Byte version;

  @ApiModelProperty(value = "创建人ID", hidden = false)
  private String createUserId;

  @ApiModelProperty(value = "创建人姓名", hidden = false)
  private String createUserName;

  // 标记
  @TableField(value = "create_time", update = "NOW()")
  @ApiModelProperty(value = "创建时间", hidden = false)
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  private String layout;

  @TableField(exist = false)
  @ApiModelProperty(value = "数据项属性列表", hidden = false)
  private List<DocAttr> attrs;

  @TableField(exist = false)
  @ApiModelProperty(value = "数据项列表", hidden = false)
  private List<DocData> data;

  @TableField(exist = false)
  private Map<String, List<DocData>> plans;

  public Doc() {
  }
}