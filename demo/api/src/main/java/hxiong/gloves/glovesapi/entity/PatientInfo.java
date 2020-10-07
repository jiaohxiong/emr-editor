package hxiong.gloves.glovesapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 黄卫平
 * @date 2020-07-27 08:56:58
 * @email
 */
@Data
@AllArgsConstructor
@TableName(value = "t_patient_info")
@ApiModel("")
public class PatientInfo {

  @TableId(type= IdType.AUTO)
  @ApiModelProperty(value = "")
  private Integer id;

  @ApiModelProperty(value = "")
  private String patientId;

  @ApiModelProperty(value = "")
  private String realName;

  @ApiModelProperty(value = "")
  private String gender;

  @ApiModelProperty(value = "")
  private String idNo;

  @ApiModelProperty(value = "")
  private String doctorId;

  @ApiModelProperty(value = "")
  private String doctorName;

  @ApiModelProperty(value = "")
  private String deptId;

  @ApiModelProperty(value = "")
  private String deptName;

  @ApiModelProperty(value = "")
  private String organizationId;

  @ApiModelProperty(value = "")
  private String organizationName;

  @ApiModelProperty(value = "")
  private String diagnosis;

  private String age;

  private String bedNo;

  private String wardName;
  private String wardId;

  private String days;
  
  public PatientInfo() {

  }
}