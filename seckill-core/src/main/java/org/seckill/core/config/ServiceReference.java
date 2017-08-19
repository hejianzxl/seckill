package org.seckill.core.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.seckill.core.DynamicDataSource.DynamicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import okhttp3.OkHttpClient;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class ServiceReference extends MybatisAutoConfiguration{

	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(100);
		jedisPoolConfig.setMaxTotal(20);
		jedisPoolConfig.setMaxWaitMillis(3000);
		jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7000));
		JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, jedisPoolConfig);
		return jedisCluster;
	}

	@Bean
	@Scope("prototype")
	public Jedis jedis() {
		return jedisPool().getResource();
	}

	@Bean
	public JedisPool jedisPool() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(1000);
		jedisPoolConfig.setMaxWaitMillis(1);
		jedisPoolConfig.setMaxTotal(1000);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 3000, null);
		return jedisPool;
	}
	
	@Bean(name = "dataSource1")
	public DataSource getDataSource1() throws Exception {
		return DataSourceBuilder.create(Thread.currentThread().getContextClassLoader())
				.driverClassName("com.mysql.jdbc.Driver").url("jdbc:mysql://10.255.132.19:3306/versus")
				.username("versus").password("versus").build();
	}

	@Bean(name = "dataSource2")
	public DataSource getDataSource2() throws Exception {
		return DataSourceBuilder.create(Thread.currentThread().getContextClassLoader())
				.driverClassName("com.mysql.jdbc.Driver").url("jdbc:mysql://10.255.132.19:3306/trident")
				.username("trident").password("trident").build();
	}

	@Bean
	@Primary
	public AbstractRoutingDataSource dataSource() throws Exception {
		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setDefaultTargetDataSource(getDataSource1());
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("dataSoruce1", getDataSource1());
		targetDataSources.put("dataSoruce2", getDataSource2());
		dataSource.setTargetDataSources(targetDataSources);
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
      return super.sqlSessionFactory(dataSource());
    }
}
