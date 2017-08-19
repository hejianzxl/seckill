package org.seckill.core.lock;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

public class RedisLock {
	
	@Autowired
	private Jedis jedis;
	
	public RedisLock(Jedis jedis) {
		this.jedis = jedis;
	}
	
	public  boolean lock(final String key) {
		boolean locked  = jedis.setnx(key, "true") > 0;
		if(locked) {
			jedis.expire(key, 1);
		}
		return locked;
	}

}
