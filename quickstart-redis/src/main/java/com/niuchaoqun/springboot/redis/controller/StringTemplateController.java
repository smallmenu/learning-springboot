package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RequestMapping("/stringtemplate")
@RestController
@Slf4j
public class StringTemplateController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/set")
    public RestResult set(@RequestParam(value = "stringKey", defaultValue = "stringredis_key") String stringKey) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(stringKey, "test_stringredis_value", 360, TimeUnit.SECONDS);

        return RestResponse.success();
    }

    @GetMapping("/get")
    public RestResult get(@RequestParam(value = "stringKey", defaultValue = "stringredis_key") String stringKey) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String result = operations.get(stringKey);

        return RestResponse.data(result);
    }
}
