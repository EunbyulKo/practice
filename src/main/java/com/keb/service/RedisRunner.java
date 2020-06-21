package com.keb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisRunner implements ApplicationRunner {

    @Autowired
    StringRedisTemplate redisTemplate;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
    
    public void setEmailTempKey(String email, String key) {
    	ValueOperations<String, String> values = redisTemplate.opsForValue();
    	values.set(email, key);
    }
    
    public String getEmailTempKey(String email) {
    	ValueOperations<String, String> values = redisTemplate.opsForValue();
    	return values.get(email);
    }
}
