package org.seckill.core.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.seckill.client.domain.ItemDO;
import org.seckill.core.constant.ConstantKey;
import org.seckill.core.dao.SeckillDao;
import org.seckill.core.factory.SaveUserOrderThread;
import org.seckill.core.help.SeckillItemHelp;
import org.seckill.core.service.JedisService;
import org.seckill.core.service.SeckillService;
import org.seckill.core.utils.SpringContextHolder;
import org.seckill.core.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.SpringProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author hejian
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService{

	private static final Logger	logger	= LoggerFactory.getLogger(SeckillServiceImpl.class);

	@Autowired
	private JedisService		jedisService;
	@Autowired
	private SeckillDao			seckillDao;

	@Override
	public List<ItemDO> getSeckillItem() {
		List<ItemDO> lists = new ArrayList<>();
		for (int i = 1; i < 3; i++) {
			ItemDO itemDO = new ItemDO();
			itemDO.setCreateTime(new Date());
			itemDO.setId(i);
			itemDO.setInventoryQty(1000);
			itemDO.setItemCode("itemcode" + i);
			itemDO.setPrice(10000);
			itemDO.setUpdateTime(new Date());
			itemDO.setUsableQty(0);
			lists.add(itemDO);
		}
		return lists;
	}

	@Override
	public boolean seckill(String uid, String itemId, long systemTime, String activityCode) {
		logger.info("uid " + uid + " itemId " + itemId + " activityCode" + activityCode);
		if (null != jedisService.get(ConstantKey.getTakeInKey(uid, itemId, activityCode))
				&& Integer.parseInt(jedisService.get(ConstantKey.getTakeInKey(uid, itemId, activityCode))) >= 1) {
			System.out.println(">>>uid " + uid + " itemId " + itemId + " activityCode" + activityCode);
			return false;
		}

		Long inventoryQty = Long.parseLong(jedisService.get(ConstantKey.getKey(itemId)));
		if (StringUtils.isEmpty(inventoryQty) || inventoryQty <= 1) {
			System.out.println("==========活动结束==========");
			return false;
		}

		try {
			Long result = jedisService.rpush(ConstantKey.getUserSeckillKey(),
					new ObjectMapper().writeValueAsString(SeckillItemHelp.getItem(uid, itemId)));
			// 记录用户已经参与抢购，避免重复刷新
			jedisService.incrBy(ConstantKey.getTakeInKey(uid, itemId, activityCode), 1);
			// 异步记录抢购记录
			ThreadUtils.getInstance().defaultExecutor()
					.execute(new SaveUserOrderThread(uid, itemId, activityCode, seckillDao));
		} catch (JsonProcessingException e) {
			logger.error("seckill is error ", e);
			e.printStackTrace();
		}
		return Boolean.TRUE;
	}

	@Override
	public List<ItemDO> getSeckillResult(long uid) {
		return null;
	}

	@Override
	public void init() {
		// 获取list
		List<ItemDO> itemDOs = getSeckillItem();
		for (ItemDO itemDO : itemDOs) {
			System.out.println("初始化特卖产品库存：" + itemDO.getItemCode() + " " + itemDO.getInventoryQty());
			jedisService.set(ConstantKey.getKey(itemDO.getItemCode()), String.valueOf(itemDO.getInventoryQty()));
		}
	}

	@Override
	public boolean seckill2(String uid, String itemId, long currentTimeMillis, String activityCode) {
		Long inventoryQty = Long.parseLong(jedisService.get(ConstantKey.getKey(itemId)));
		if (StringUtils.isEmpty(inventoryQty) || inventoryQty <= 1) {
			System.out.println("抢购活动结束");
			return Boolean.FALSE;
		}
		System.out.println("uid" + uid + " 剩余商品库存：" + inventoryQty);
		List<Object> result = jedisService.watch(ConstantKey.getKey(itemId), null);
		if (!CollectionUtils.isEmpty(result)) {
			// 记录用户已经参与抢购，避免重复刷新
			jedisService.incrBy(ConstantKey.getTakeInKey(uid, itemId, activityCode), 1);
			// 异步记录抢购记录
			ThreadUtils.getInstance().defaultExecutor()
					.execute(new SaveUserOrderThread(uid, itemId, activityCode, seckillDao));
		}
		return Boolean.TRUE;
	}

	@Override
	public Object test() {
		return null;
	}

}
