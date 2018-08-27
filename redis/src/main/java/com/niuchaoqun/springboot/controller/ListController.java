package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list")
public class ListController {
    private static final Logger logger = LoggerFactory.getLogger(ListController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("")
    public void index() {

    }

    @RequestMapping("/add")
    public Object add() {
        String key = "test_list";
        String value = "value";
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        ListOperations<String, String> list = redisTemplate.opsForList();

        list.rightPush(key, value + "1");
        list.rightPush(key, value + "2");

        Object o = list.leftPop(key);

        return Response.data(o);
    }


}
