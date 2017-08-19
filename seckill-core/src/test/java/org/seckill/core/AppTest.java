package org.seckill.core;

import java.util.HashMap;
import java.util.Map;

import org.seckill.core.service.SeckillService;
import org.seckill.core.service.impl.JedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseTest {
	@Autowired
   JedisPool jedisPool;
	@Autowired
	private SeckillService seckillService;
	
	@org.junit.Test
	public void test() {
		Jedis jedis = jedisPool.getResource();
		Map<String, String> map = new HashMap<>();
		map.put("1", "test");
		//jedis.hmset("test", map);
		jedis.hset("test", "name", "上海");
		jedis.pexpire("test", 3000l);
		
		System.out.println(jedis.ttl("test"));
		System.out.println(jedis.hget("test", "name"));
		
		Map<String, String> map2 =  jedis.hgetAll("test");
		System.out.println(jedis.ttl("test"));
	}
	
	@org.junit.Test
	public void proxyTest() {
		System.out.println(seckillService.getClass().getName());
	}
}
