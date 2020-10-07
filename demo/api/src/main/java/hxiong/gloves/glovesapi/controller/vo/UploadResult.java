package hxiong.gloves.glovesapi.controller.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel

public class UploadResult {
  private String state;
  private String url;
  private String title;
  private String original; 
  public UploadResult() {

  }
}