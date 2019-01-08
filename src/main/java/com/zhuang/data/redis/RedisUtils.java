package com.zhuang.data.redis;

import com.zhuang.data.config.MyDataProperties;

import redis.clients.jedis.Jedis;

public class RedisUtils {

	private static volatile Jedis jedis;

	public static Jedis getJedis() {
		if (jedis == null) {
			synchronized (Jedis.class) {
				if (jedis == null) {
					MyDataProperties zhuangDataProperties = new MyDataProperties();
					jedis = new Jedis(zhuangDataProperties.getRedisHost(), zhuangDataProperties.getRedisPort());
				}
			}
		}
		return jedis;
	}
}
