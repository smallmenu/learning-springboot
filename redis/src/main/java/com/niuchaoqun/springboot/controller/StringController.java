package com.niuchaoqun.springboot.controller;


import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import com.niuchaoqun.springboot.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/string")
public class StringController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(StringController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/set")
    public Object set(@RequestParam(value = "stringKey", defaultValue = "string_key") String stringKey) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(stringKey, "test_redis_value", 360, TimeUnit.SECONDS);

        return Response.success();
    }

    @RequestMapping("/get")
    public Object get(@RequestParam(value = "stringKey", defaultValue = "string_key") String stringKey) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(stringKey)) {
            String result = (String) operations.get(operations);
            return Response.success(result);
        } else {
            return Response.error("not exist");
        }
    }

    @RequestMapping("/setobject")
    public Object setObject(@RequestParam(value = "objectKey", defaultValue = "object_key") String objectKey) {
        User user = new User();
        user.setId(1);
        user.setName("helloworld汉字");
        user.setAge(15);

        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set(objectKey, user);
        return Response.success();
    }

    @RequestMapping("/getobject")
    public Object getObject(@RequestParam(value = "objectKey", defaultValue = "object_key") String objectKey) {
        ValueOperations operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(objectKey)) {
            User user = (User) operations.get(operations);
            return Response.data(user);
        } else {
            return Response.error("not exist");
        }
    }
}
