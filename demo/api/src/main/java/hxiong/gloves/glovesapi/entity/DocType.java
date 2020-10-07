package hxiong.gloves.glovesapi.entity;

import java.util.List;
import java.util.ArrayList;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "t_doc_type")
@ApiModel
public class DocType {

  @TableId(type = IdType.AUTO)
  private Integer id;

  private String typeId;

  @ApiModelProperty(value = "父级类型编号")
  private String parentTypeId;
  private String label;

  @TableField(exist = false)
  private List<DocType> children = new ArrayList<>();

  @TableField(exist = false)
  private TypeDoc relationDoc;

  @TableField(exist = false)
  private List<TypePatientDoc> patientDocs;
  
  public DocType() {
  }
}