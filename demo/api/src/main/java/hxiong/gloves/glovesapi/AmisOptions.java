package hxiong.gloves.glovesapi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class AmisOptions {

  @ApiModelProperty(required = true, value = "选项列表")
  private List<AmisOptionItem> options;

  public void setOptions(List<AmisOptionItem> options) {
    this.options = options;
  }

  public List<AmisOptionItem> getOptions() {
    return this.options;
  }

  public AmisOptions(List<AmisOptionItem> options) {
    this.options = options;
  }
}