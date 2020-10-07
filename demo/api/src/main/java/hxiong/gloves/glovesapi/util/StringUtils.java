package hxiong.gloves.glovesapi.util;

import java.util.UUID;

public class StringUtils {

  /**
   * 获取UUID
   */
  public static String getUid() {
    return UUID.randomUUID().toString();
  }

  public static boolean isEmpty(String value) {
    return org.springframework.util.StringUtils.isEmpty(value);
  }
}