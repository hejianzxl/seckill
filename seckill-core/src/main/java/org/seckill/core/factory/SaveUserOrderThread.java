package org.seckill.core.factory;

import org.seckill.core.dao.SeckillDao;
import org.seckill.core.help.SeckillItemHelp;

/**
 * 记录用户参与记录
 * @author hejian
 *
 */
public class SaveUserOrderThread implements Runnable {
	
	private SeckillDao seckillDao;
	
	private String uid;
	
	private String itemId;
	
	private String activityCode;
	
	public SaveUserOrderThread(String uid, String itemId, String activityCode,SeckillDao seckillDao) {
		this.uid = uid;
		this.activityCode = activityCode;
		this.itemId = itemId;
		this.seckillDao = seckillDao;
	}

	@Override
	public void run() {
		seckillDao.insert(SeckillItemHelp.convertSeckillItem(uid,itemId,activityCode));
	}

}
