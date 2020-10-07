package hxiong.gloves.glovesapi;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApiModel
public class AmisResults<T> {

  private static Logger logger = LoggerFactory.getLogger(AmisResults.class);

  @ApiModelProperty(required = true, notes = "结果状态码", example = "0")
  private java.lang.Integer status;
  @ApiModelProperty(required = true, notes = "返回信息", example = "操作成功")
  private java.lang.String msg;
  private T data;

  public AmisResults(Integer status, String msg, T data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }

  public void SetStatus(Integer status) {
    this.status = status;
  }

  public Integer getStatus() {
    return this.status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public static AmisResults success() {
    return new AmisResults(0, "操作成功!", null);
  }

  public static <T> AmisResults success(T data) {
    return new AmisResults(0, "操作成功!", data);
  }

  public static <T> AmisResults page(List<T> data, long count) {
    return success(new AmisPageItem(data, count));
  }

  public static <T> AmisResults options(List<AmisOptionItem> data) {
    return success(new AmisOptions(data));
  }

  public static AmisResults updated(int count) {
    if (count > 0) {
      return success();
    }
    return failed();
  }

  public static AmisResults failed(String msg) {
    return new AmisResults(300, msg, null);
  }

  public static AmisResults failed() {
    return AmisResults.failed("操作失败");
  }

  public static AmisResults error(Exception e) {
    return fromException(e);
  }

  public static AmisResults error(String msg) {
    return new AmisResults(500, msg, null);
  }

  public static AmisResults unauthorized(String msg) {
    return new AmisResults(401, msg, null);
  }

  public String toJson() {
    return JSON.toJSONString(this);
  }

  public static AmisResults fromException(Exception e) {
    logger.error("[JGenX-内部异常]:", e);
    return new AmisResults(500, "操作失败", null);
  }
}