package com.example.redischat.redis;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class RedisChatMemory {


	private final StringRedisTemplate redis;


	public RedisChatMemory(StringRedisTemplate redis) {
	this.redis = redis;
	}


	public void add(String sessionId, String message) {
	redis.opsForList().rightPush(sessionId, message);
	}


	public List<String> getHistory(String sessionId, int limit) {
	return redis.opsForList().range(sessionId, -limit, -1);
	}
}