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
@TableName(value = "t_doc_options")
@ApiModel
public class DocOptions {

  @TableId(type=IdType.AUTO)
  private Integer id;

  @ApiModelProperty(value = "文档编号")
  private String docId;
  private Byte docVersion;
  private String nodeId;
  private String label;
  private String value;

  public DocOptions() {

  }
}