package hxiong.gloves.glovesapi.service;

import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.ArrayList;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public abstract class BaseQueueHelper<D, E extends ValueWrapper<D>, H extends WorkHandler<E>> {
  private static List<BaseQueueHelper> queueHelperList = new ArrayList<BaseQueueHelper>();
  private Disruptor<E> disruptor;
  private RingBuffer<E> ringBuffer;
  private List<D> initQueue = new ArrayList<D>();
  protected abstract int getQueueSize();
  protected abstract EventFactory<E> eventFactory();
  protected abstract WorkHandler[] getHandler();
  public void init() {
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("DisruptorThreadPool").build();
    disruptor = new Disruptor<E>(eventFactory(), getQueueSize(), namedThreadFactory, ProducerType.SINGLE,
        getStrategy());
    disruptor.setDefaultExceptionHandler(new DisruptorHandlerException());
    disruptor.handleEventsWithWorkerPool(getHandler());
    ringBuffer = disruptor.start();
    // 初始化数据发布
    for (D data : initQueue) {
      ringBuffer.publishEvent(new EventTranslatorOneArg<E, D>() {
        @Override
        public void translateTo(E event, long sequence, D data) {
          event.setValue(data);
        }
      }, data);
    }

    // 加入资源清理钩子
    synchronized (queueHelperList) {
      if (queueHelperList.isEmpty()) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
          @Override
          public void run() {
            for (BaseQueueHelper baseQueueHelper : queueHelperList) {
              baseQueueHelper.shutdown();
            }
          }
        });
      }
      queueHelperList.add(this);
    }
  }
  protected abstract WaitStrategy getStrategy();
  public synchronized void publishEvent(D data) {
    if (ringBuffer == null) {
      initQueue.add(data);
      return;
    }
    ringBuffer.publishEvent(new EventTranslatorOneArg<E, D>() {
      @Override
      public void translateTo(E event, long sequence, D data) {
        event.setValue(data);
      }
    }, data);
  }
  public void shutdown() {
    disruptor.shutdown();
  }
}