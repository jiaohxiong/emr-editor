
package hxiong.gloves.glovesapi.service;

import java.util.List;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hxiong.gloves.glovesapi.entity.DisruptorData;

@Component
public class DisruptorDataEventQueueHelper extends BaseQueueHelper<DisruptorData, DisruptorDataEvent, DisruptorDataEventHandler> implements InitializingBean {
  private static final int QUEUE_SIZE = 1024;
  @Autowired
  private List<DisruptorDataEventHandler> disruptorDataEventHandler;
 
  @Override
  protected int getQueueSize() {
    return QUEUE_SIZE;
  }
 
  @Override
  protected EventFactory eventFactory() {
    return new DisruptorDataEventFactory();
  }
 
  @Override
  protected WorkHandler[] getHandler() {
    int size = disruptorDataEventHandler.size();
    DisruptorDataEventHandler[] paramEventHandlers = (DisruptorDataEventHandler[]) disruptorDataEventHandler.toArray(new DisruptorDataEventHandler[size]);
    return paramEventHandlers;
  }
 
  @Override
  protected WaitStrategy getStrategy() {
    return new BlockingWaitStrategy();
    //return new YieldingWaitStrategy();
  }
 
  @Override
  public void afterPropertiesSet() throws Exception {
    this.init();
  }
}