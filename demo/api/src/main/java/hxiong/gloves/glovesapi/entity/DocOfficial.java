package hxiong.gloves.glovesapi.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "t_doc_official")
@ApiModel
public class DocOfficial {

  @TableId(type = IdType.AUTO)
  private Integer id;
  @ApiModelProperty(value = "文档编号")
  private String docId;
  private Byte version;
  private String type;
  private String typeName;
  private String docName;
  private String deptId;
  private String deptName;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date beginTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date endTime;
  private String checkUserId;
  private String checkUserName;
  private Byte status;

  public DocOfficial() {
  }
}