package hxiong.gloves.glovesapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@TableName("t_doc_control_option")
@Data
@ApiModel
public class DataControlOption {
  @TableId(type = IdType.AUTO)
  private Integer id;

  private String label;
  private String value;
  private String controlId;
}