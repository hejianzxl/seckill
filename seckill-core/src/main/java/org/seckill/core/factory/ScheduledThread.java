package org.seckill.core.factory;

import java.io.IOException;
import org.seckill.client.SeckillItem;
import org.seckill.core.constant.ConstantKey;
import org.seckill.core.service.JedisService;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ScheduledThread implements Runnable {

	private static JedisService jedisService;

	public ScheduledThread(JedisService jedisService) {
		ScheduledThread.jedisService = jedisService;
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			String userSeckill = jedisService.lpop(ConstantKey.getUserSeckillKey());
			if (StringUtils.isEmpty(userSeckill)) {
				continue;
			}
			try {
				SeckillItem seckillItem = new ObjectMapper().readValue(userSeckill, SeckillItem.class);
				if (null != seckillItem) {
					Long qty = Long.parseLong(jedisService.get(ConstantKey.getKey(String.valueOf(seckillItem.getItemId()))));
					if(null != qty && qty > 0 ) {
						//成功队列  userid - List
						jedisService.sadd(ConstantKey.getOrderSequence(seckillItem.getUserId()),seckillItem);
						//扣除商品库存
						jedisService.decrBy(ConstantKey.getKey(String.valueOf(seckillItem.getItemId())), 1);
					}else {
						System.out.println("库存不足！");
					}
					//异步更新记录抢购记录，共用线程池
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
