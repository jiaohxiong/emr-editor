package hxiong.gloves.glovesapi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AmisOptionItem {

  @ApiModelProperty(required = true, value = "选项显示文本")
  private String label;

  @ApiModelProperty(required = true, value = "选项值")
  private String value;

  public void setLabel(String label) {
    this.label = label;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLabel() {
    return this.label;
  }

  public String getValue() {
    return this.value;
  }
}