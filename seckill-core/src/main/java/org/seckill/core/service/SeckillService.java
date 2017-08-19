package org.seckill.core.service;

import java.util.List;

import org.seckill.client.domain.ItemDO;

public interface SeckillService {
	public List<ItemDO> getSeckillItem();
	
	public boolean seckill(String uid,String itemId,long systemTime,String activityCode);
	
	public List<ItemDO> getSeckillResult(long uid);

	public void init();

	public boolean seckill2(String uid, String itemId, long currentTimeMillis, String activityCode);

	public Object test();
 }
