package hxiong.gloves.glovesapi.entity;

import lombok.Data;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@TableName(value = "t_doc_source")
@ApiModel
public class DataSource {
  // 主键
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "id", hidden = true)
  private Integer id;

  @ApiModelProperty(value = "数据源ID", hidden = true)
  private String sourceId;

  @ApiModelProperty(value = "数据源名称", hidden = true)
  private String name;

  @ApiModelProperty(value = "数据源类型", hidden = true)
  private String type;

  @ApiModelProperty(value = "附加内容", hidden = true)
  private String extra;

  @ApiModelProperty(value = "数据源内容", hidden = true)
  private String content;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "创建时间", hidden = true)
  private Date createTime;

  public DataSource() {
  }

  
}