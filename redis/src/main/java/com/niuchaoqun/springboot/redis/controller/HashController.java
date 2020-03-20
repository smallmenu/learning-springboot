package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuchaoqun
 */
@RequestMapping("/hash")
@RestController
@Slf4j
public class HashController extends BaseController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/string")
    public Object string(@RequestParam(value = "hashKey", defaultValue = "hash_key_string") String hashKey) {
        HashOperations<String, String, String> operations = redisTemplate.opsForHash();

        operations.put(hashKey, "key1", "value1");
        operations.put(hashKey, "key2", "value2");
        operations.put(hashKey, "key3", "value3");

        String value = operations.get(hashKey, "key2");

        return RestResponse.success(value);
    }
}
