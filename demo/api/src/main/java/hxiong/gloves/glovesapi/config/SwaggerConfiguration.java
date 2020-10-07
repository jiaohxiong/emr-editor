package hxiong.gloves.glovesapi.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfiguration {

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("V1.0").pathMapping("/")
        .select().apis(RequestHandlerSelectors.basePackage("hxiong.gloves.glovesapi.controller"))
        // .paths(or(regex("/api/.*")))
        .paths(PathSelectors.any()).build().apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("GLOVES-文档").description("JGenX前后端对接API文档").termsOfServiceUrl("http://xdawo.com")
        .version("1.0").contact(new Contact("hxiong", "http://xdawo.com", "sanyiws@qq.com"))
        .license("The Apache License, Version 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .build();
  }

}