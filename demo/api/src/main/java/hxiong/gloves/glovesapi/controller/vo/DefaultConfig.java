package hxiong.gloves.glovesapi.controller.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class DefaultConfig {
  private String imageActionName = "uploadimage";
  private String imageFieldName = "upfile";
  private Integer imageMaxSize = 2048000;
  private List<String> imageAllowFiles;
  private Boolean imageCompressEnable = true;
  private Integer imageCompressBorder = 1600;
  private String imageInsertAlign ="none";
  private String imageUrlPrefix = "";

  public DefaultConfig() {

  }
}