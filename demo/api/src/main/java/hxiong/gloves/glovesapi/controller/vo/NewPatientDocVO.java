package hxiong.gloves.glovesapi.controller.vo;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class NewPatientDocVO {
    @ApiModelProperty(value = "文本编号")
  private String docId;
  private String docName;
  private String type;
  private String typeName;
  private Byte version;

  private Map<String,String> params = new HashMap<String,String>();
  public NewPatientDocVO() {
  }
}
