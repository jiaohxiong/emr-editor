package hxiong.gloves.glovesapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import hxiong.gloves.glovesapi.Const;

/**
 * @autor hxiong
 * @date 2020-06-02
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

  /**
   * 访问静态资源
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    
    // registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/moblie2/static/");
    registry.addResourceHandler("/uploads/**").addResourceLocations("file:"+Const.UEDITOR_STORAGE_PATH);
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    super.addResourceHandlers(registry);
  }

  @Override
  protected void addViewControllers(ViewControllerRegistry registry) {
    // registry.addViewController("/").setViewName("forward:index.html");
    super.addViewControllers(registry);
  }
}