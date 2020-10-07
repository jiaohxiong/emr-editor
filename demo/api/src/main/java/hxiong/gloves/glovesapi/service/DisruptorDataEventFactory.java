package hxiong.gloves.glovesapi.service;

import com.lmax.disruptor.EventFactory;

public class DisruptorDataEventFactory implements EventFactory<DisruptorDataEvent> {
 
    @Override
    public DisruptorDataEvent newInstance() {
      return new DisruptorDataEvent();
    }
  }
