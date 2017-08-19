package org.seckill.core.service.impl;

import java.util.List;
import org.seckill.core.service.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

@Service
public class JedisServiceImpl implements JedisService {

	@Autowired
	private JedisPool jedisPool;

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String key, String fields) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(key, fields);
		jedis.close();
		return result;

	}

	@Override
	public Long rpush(String key, String writeValueAsString) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result= jedis.rpush(key, writeValueAsString);
		} catch (Exception e) {
			e.printStackTrace();
			jedis.close();
		} finally {
			jedis.close();
		}
		
		return result;
	}

	@Override
	public long incrBy(String key, int value) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result= jedis.incrBy(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			jedis.close();
		} finally {
			jedis.close();
		}
		return result;
	}

	@Override
	public String lpop(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = jedisPool.getResource();
			result= jedis.lpop(key);
		} catch (Exception e) {
			e.printStackTrace();
			jedis.close();
		} finally {
			jedis.close();
		}
		return result;
	}

	@Override
	public Long decrBy(String key, int value) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result= jedis.decrBy(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			jedis.close();
		} finally {
			jedis.close();
		}
		return result;
	}

	@Override
	public List<Object> watch(String key,Object value) {
		Jedis jedis = null;
		List<Object> result = null;
		try {
			jedis = jedisPool.getResource();
			jedis.watch(key);
            Transaction transaction = jedis.multi();
            transaction.incr(key);
            result = transaction.exec();
            if(result != null || !result.isEmpty()) {
            	jedis.sadd("setsucc", new ObjectMapper().writeValueAsString(value));
            }
		} catch (Exception e) {
			e.printStackTrace();
			jedis.close();
		} finally {
			jedis.close();
		}
		return result;
	}

	@Override
	public Long sadd(String key, Object value) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.sadd(key, new ObjectMapper().writeValueAsString(value));
		} catch (Exception e) {
			e.printStackTrace();
			jedis.close();
		} finally {
			jedis.close();
		}
		return result;
	}
}
