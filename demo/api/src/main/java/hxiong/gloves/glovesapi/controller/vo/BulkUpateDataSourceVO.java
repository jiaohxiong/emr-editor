package hxiong.gloves.glovesapi.controller.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class BulkUpateDataSourceVO {
  private String type;

  public BulkUpateDataSourceVO() {

  }
}