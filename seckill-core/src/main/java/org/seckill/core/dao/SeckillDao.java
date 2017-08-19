package org.seckill.core.dao;

import org.seckill.client.domain.SeckillDO;
import org.springframework.stereotype.Repository;

@Repository
public class SeckillDao {

	public void insert(SeckillDO seckillDO) {
		System.out.println("=========保存用户信息=======" + seckillDO.getUid());
	}

}
