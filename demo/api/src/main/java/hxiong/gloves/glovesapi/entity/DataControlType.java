package hxiong.gloves.glovesapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "t_doc_control_type")
@ApiModel
public class DataControlType {

  // 主键
  @TableId(type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty(value = "控件KEY", hidden = false)
  private String name;

  @ApiModelProperty(value = "控件名称", hidden = false)
  private String label;

  @ApiModelProperty(value = "指令", hidden = false)
  private String command;
}