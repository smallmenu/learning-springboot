package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RequestMapping("/multiple")
@RestController
@Slf4j
public class MultipleController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("manualRedisTemplate")
    private RedisTemplate manualRedisTemplate;

    @Autowired
    @Qualifier("otherRedisTemplate")
    private RedisTemplate otherRedisTemplate;

    @GetMapping("/set")
    public RestResult set(@RequestParam(value = "stringKey", defaultValue = "manual_string_key") String stringKey) {
        ValueOperations<String, String> operations1 = redisTemplate.opsForValue();
        operations1.set(stringKey, "manual_test_redis_value1", 360, TimeUnit.SECONDS);

        ValueOperations<String, String> operations2 = manualRedisTemplate.opsForValue();
        operations2.set(stringKey, "manual_test_redis_value2", 360, TimeUnit.SECONDS);

        ValueOperations<String, String> operations3 = otherRedisTemplate.opsForValue();
        operations3.set(stringKey, "manual_test_redis_value3", 360, TimeUnit.SECONDS);

        return RestResponse.success();
    }

    @GetMapping("/get")
    public RestResult get(@RequestParam(value = "stringKey", defaultValue = "manual_string_key") String stringKey) {
        ValueOperations<String, String> operations1 = redisTemplate.opsForValue();
        String string1 = null;
        String string2 = null;
        String string3 = null;
        if (BooleanUtils.isTrue(redisTemplate.hasKey(stringKey))) {
            string1 = operations1.get(stringKey);
        }

        ValueOperations<String, String> operations2 = manualRedisTemplate.opsForValue();
        if (BooleanUtils.isTrue(manualRedisTemplate.hasKey(stringKey))) {
            string2 = operations2.get(stringKey);
        }

        ValueOperations<String, String> operations3 = otherRedisTemplate.opsForValue();
        if (BooleanUtils.isTrue(otherRedisTemplate.hasKey(stringKey))) {
            string3 = operations3.get(stringKey);
        }

        List<String> strings = new ArrayList<>();
        strings.add(string1);
        strings.add(string2);
        strings.add(string3);

        return RestResponse.data(strings);
    }
}
