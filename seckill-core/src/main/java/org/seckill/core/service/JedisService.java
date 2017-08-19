package org.seckill.core.service;

import java.util.List;

public interface JedisService {
	String get(String key);

	String set(String key, String value);

	String hget(String hkey, String key);

	long hset(String hkey, String key, String value);

	long incr(String key);

	long expire(String key, int second);

	long ttl(String key);

	long hdel(String key);

	long hdel(String key, String field);

	Long rpush(String userSeckillKey, String writeValueAsString);

	long incrBy(String takeInKey, int i);

	String lpop(String userSeckillKey);

	Long decrBy(String key, int i);

	List<Object> watch(String key,Object value);

	Long sadd(String orderSequence, Object value);
}
