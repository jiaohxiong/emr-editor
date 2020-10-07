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
@TableName(value = "t_dept")
@ApiModel
public class Dept {

  // 主键
  @TableId(type = IdType.AUTO)
  private Integer id;
  
  @ApiModelProperty(value = "科室ID", hidden = false)
  private String deptId;

  @ApiModelProperty(value = "科室名称", hidden = false)
  private String deptName;
}