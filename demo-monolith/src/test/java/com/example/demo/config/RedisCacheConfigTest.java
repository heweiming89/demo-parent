package com.example.demo.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.example.demo.MonolithApplicationTests;

public class RedisCacheConfigTest extends MonolithApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void test() {
		stringRedisTemplate.opsForValue().set("demo.key", "helloworld");
	}

}
