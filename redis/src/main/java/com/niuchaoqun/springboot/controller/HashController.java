package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hash")
@RestController
public class HashController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(HashController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/string")
    public Object string(@RequestParam(value = "hashKey", defaultValue = "hash_key_string") String hashKey) {
        HashOperations<String, String, String> operations = redisTemplate.opsForHash();

        operations.put(hashKey, "key1", "value1");
        operations.put(hashKey, "key2", "value2");
        operations.put(hashKey, "key3", "value3");

        String value = operations.get(hashKey, "key2");

        return Response.success(value);
    }
}
