package org.seckill.core.help;

import java.util.Date;

import org.seckill.client.SeckillItem;
import org.seckill.client.domain.SeckillDO;

public class SeckillItemHelp {

	public static SeckillItem getItem(String uid, String itemId) {
		SeckillItem seckillItem = new SeckillItem();
		seckillItem.setItemId(itemId);
		seckillItem.setUserId(uid);
		return seckillItem;
	}

	public static SeckillDO convertSeckillItem(String uid, String itemId, String activityCode) {
		SeckillDO seckillDO = new SeckillDO();
		seckillDO.setActivityCode(activityCode);
		seckillDO.setCreatedTime(new Date());
		seckillDO.setId(1);
		seckillDO.setItemId(itemId);
		seckillDO.setUid(uid);
		seckillDO.setUpdatedTime(new Date());
		return seckillDO;
	}
}
