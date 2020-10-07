package hxiong.gloves.glovesapi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class AmisPageItem<T> {

  @ApiModelProperty(required = true, notes = "数据集")
  private List<T> items;

  @ApiModelProperty(required = true, notes = "总行数", example = "10")
  private long count = 0;

  public AmisPageItem() {

  }

  public AmisPageItem(List<T> items, long count) {
    this.items = items;
    this.count = count;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  public List<T> getItems() {
    return this.items;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public long getCount() {
    return this.count;
  }
}