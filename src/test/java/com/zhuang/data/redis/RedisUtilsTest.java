package com.zhuang.data.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisUtilsTest {

	@Test
	public void getJedis() {
		
		Jedis jedis = RedisUtils.getJedis();
		
		jedis.set("zwb","hello zwb!");
		
	}
	
}
