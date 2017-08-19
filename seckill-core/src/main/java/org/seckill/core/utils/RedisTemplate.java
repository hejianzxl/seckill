/*package org.seckill.core.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTemplate {

	private static JedisPool jedisPool;
	
	public RedisTemplate(JedisPool jedisPool) {
		RedisTemplate.jedisPool = jedisPool;
	}

	public static String getKey(String key) {
		try {
			String result = jedisPool.getResource().get(key);
			
		}finally {
			jedisPool.returnResource(resource);
		}
	}
}
*/