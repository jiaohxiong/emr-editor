package hxiong.gloves.glovesapi.entity;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "t_doc_official")
@ApiModel("")
public class TypeDoc {
  
  @ApiModelProperty(value = "文本编号")
  private String docId;
  private String docName;
  private String type;
  private String typeName;
  private Byte version;

  @TableField(exist = false)
  private Map<String,String> params = new HashMap<String,String>();

  public TypeDoc() {
  }
}