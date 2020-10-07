package hxiong.gloves.glovesapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hxiong.gloves.glovesapi.Const;
import hxiong.gloves.glovesapi.controller.vo.DefaultConfig;
import hxiong.gloves.glovesapi.controller.vo.UploadResult;
import hxiong.gloves.glovesapi.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UEditorController {

  @RequestMapping("/ueditor")
  public String ueditor(@RequestParam(value = "action", required = false) String action,
      @RequestParam(value = "upfile", required = false) MultipartFile file) {
      
    if (StringUtils.isEmpty(action) || "config".equals(action)) {
      DefaultConfig config = new DefaultConfig();
      List<String> allowFiles = new ArrayList<String>();
      allowFiles.add(".png");
      allowFiles.add(".jpg");
      allowFiles.add(".jpeg");
      allowFiles.add(".bmp");

      config.setImageAllowFiles(allowFiles);
      return JSON.toJSONString(config);
    } else if ("uploadimage".equals(action)) {
      UploadResult result = new UploadResult();
      result.setState("ERROR");
      if (file != null && !file.isEmpty()) {

        try {
          String imagePath = Const.UEDITOR_STORAGE_PATH + "images";
          File dir = new File(imagePath);
          if (dir.exists() && dir.isDirectory()) {
            String old = file.getOriginalFilename();

            String ext = old.substring(old.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + ext;
            String destFileName = dir + File.separator + filename;
            System.out.println(destFileName);
            File dest = new File(destFileName);
            file.transferTo(dest);

            result.setState("SUCCESS");
            result.setUrl("uploads/images/" + filename);
            result.setTitle(old);
            result.setOriginal(old);

          } else {
            dir.mkdir();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return JSON.toJSONString(result);
    }

    return "";
  }
}