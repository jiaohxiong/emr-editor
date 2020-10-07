package hxiong.gloves.glovesapi.config;

import java.util.List;

import hxiong.gloves.glovesapi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import hxiong.gloves.glovesapi.entity.DataSource;
import hxiong.gloves.glovesapi.service.GlovesService;
import hxiong.gloves.glovesapi.util.RedisUtil;

@Component
@Order(value = 1)
public class OnApplicationStarted implements ApplicationRunner {

  @Autowired
  private GlovesService glovesService;

  @Autowired
  private RedisUtil redis;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("GLOVES loading ... ！");
    
    try{
      // 缓存数据源
      List<DataSource> data = glovesService.getAllDataSource();
      for (DataSource d : data) {

        if(StringUtils.isEmpty(d.getSourceId())) continue;

        redis.set(d.getSourceId(), d);
      }
      System.out.println("GLVOES 数据源行数 [" +data.size()+ "] !");
    }catch(Exception e) {
      System.out.println("Load caching faild," + e.getMessage());
    }
    System.out.println("GLVOES started !");
  }
}
