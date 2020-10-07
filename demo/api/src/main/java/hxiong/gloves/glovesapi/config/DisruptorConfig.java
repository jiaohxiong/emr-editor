package hxiong.gloves.glovesapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import hxiong.gloves.glovesapi.service.DisruptorDataEventHandler;

@Configuration
@ComponentScan(value = {"com.portal.disruptor"})
//多实例几个消费者
public class DisruptorConfig {
 
  /**
   * disruptorDataEventHandler1
   *
   * @return DisruptorDataEventHandler
   */
  @Bean
  public DisruptorDataEventHandler disruptorDataEventHandler1() {
    return new DisruptorDataEventHandler();
  }
 
  /**
   * disruptorDataEventHandler2
   *
   * @return DisruptorDataEventHandler
   */
  @Bean
  public DisruptorDataEventHandler disruptorDataEventHandler2() {
    return new DisruptorDataEventHandler();
  }
 
  /**
   * disruptorDataEventHandler3
   *
   * @return DisruptorDataEventHandler
   */
  @Bean
  public DisruptorDataEventHandler disruptorDataEventHandler3() {
    return new DisruptorDataEventHandler();
  }
 
 
  /**
   * disruptorDataEventHandler4
   *
   * @return DisruptorDataEventHandler
   */
  @Bean
  public DisruptorDataEventHandler disruptorDataEventHandler4() {
    return new DisruptorDataEventHandler();
  }
 
  /**
   * disruptorDataEventHandler5
   *
   * @return DisruptorDataEventHandler
   */
  @Bean
  public DisruptorDataEventHandler disruptorDataEventHandler5() {
    return new DisruptorDataEventHandler();
  }
}
