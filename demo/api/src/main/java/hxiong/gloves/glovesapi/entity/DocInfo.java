package hxiong.gloves.glovesapi.entity;

import java.util.Date;
import java.util.List;

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
@TableName(value = "t_doc_manage")
@ApiModel
public class DocInfo {

  // 主键
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "id", hidden = true)
  private Integer id;

  @ApiModelProperty(value = "文档标识符", hidden = false)
  private String docId;

  @ApiModelProperty(value = "文档名称", hidden = false)
  private String docName;

  @ApiModelProperty(value = "部门ID", hidden = false)
  private String deptId;

  @ApiModelProperty(value = "文档类型", hidden = false)
  private String type;

  @ApiModelProperty(value = "文档类型", hidden = false)
  private String typeName;

  @ApiModelProperty(value = "部门名称", hidden = false)
  private String deptName;

  @ApiModelProperty(value = "最大版本号", hidden = false)
  private Byte lastVersion;

  @ApiModelProperty(value = "创建者ID", hidden = false)
  private String createUserId;

  @ApiModelProperty(value = "创建者姓名", hidden = false)
  private String createUserName;

  @ApiModelProperty(value = "最后更新者ID", hidden = false)
  private String lastUpdateUserId;

  @ApiModelProperty(value = "最后更新者姓名", hidden = false)
  private String lastUpdateUserName;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "创建时间", hidden = false)
  private Date createTime;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "最后更新时间", hidden = false)
  private Date lastUpdateTime;

  @TableField(exist = false)
  @ApiModelProperty(value = "模版集", hidden = false)
  private List<Doc> docs;

  public DocInfo() {
  }
}