package hxiong.gloves.glovesapi.service;

import com.lmax.disruptor.WorkHandler;

import org.springframework.beans.factory.annotation.Autowired;

import hxiong.gloves.glovesapi.entity.PatientDocInfo;
import hxiong.gloves.glovesapi.util.RedisUtil;

public class DisruptorDataEventHandler implements WorkHandler<DisruptorDataEvent> {

	@Autowired
	private RedisUtil redis;

	@Autowired
  	private GlovesService gloves;

	@Override
	public void onEvent(DisruptorDataEvent event) throws Exception {
		System.out.println("receive data" + event.getValue().getType());
		
		String key = event.getValue().getType().getKey();
		String dataId = event.getValue().getDataId();
		Object data = event.getValue().getData();
		// 以set方式缓存在redis中
		redis.hset(key, dataId, data, 60*30);

		StorgeFixedThreadPool._new().getPool().execute(()->{
			try {
				int count = gloves.addPatientDoc4M((PatientDocInfo)data);
				System.out.println("stored "+(count>0?"success":"faild")+"!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		System.out.println("cached to redis,KEY="+key+",dataId="+dataId);
	}
    
}
