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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/string")
public class StringController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(StringController.class);

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/set")
    public Object set(@RequestParam(value = "stringKey", defaultValue = "string_key") String stringKey) {
        ValueOperations operations = stringRedisTemplate.opsForValue();
        operations.set(stringKey, "test_redis_value", 360, TimeUnit.SECONDS);

        return Response.success();
    }

    @RequestMapping("/get")
    public Object get(@RequestParam(value = "stringKey", defaultValue = "string_key") String stringKey) {
        ValueOperations operations = stringRedisTemplate.opsForValue();
        if (stringRedisTemplate.hasKey(stringKey)) {
            String result = (String)operations.get(stringKey);
            return Response.success(result);
        } else {
            return Response.error("not exist");
        }
    }

    @RequestMapping("/seti")
    public Object seti(@RequestParam(value = "integerKey", defaultValue = "integer_key") String integerKey) {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.set(integerKey, 200, 360, TimeUnit.SECONDS);

        return Response.success();
    }

    @RequestMapping("/geti")
    public Object geti(@RequestParam(value = "integerKey", defaultValue = "integer_key") String integerKey) {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(integerKey)) {
            Integer integer = operations.get(integerKey);
            return Response.success(String.valueOf(integer));
        } else {
            return Response.error("not exist");
        }
    }

    @RequestMapping("/seta")
    public Object seta(@RequestParam(value = "arrayKey", defaultValue = "array_key") String arrayKey) {
        ArrayList<String> arrays = new ArrayList<>();
        arrays.add("AA");
        arrays.add("BB");
        arrays.add("CC");

        ValueOperations<String, ArrayList<String>> operations = redisTemplate.opsForValue();
        operations.set(arrayKey, arrays, 360, TimeUnit.SECONDS);

        return Response.success();
    }

    @RequestMapping("/geta")
    public Object geta(@RequestParam(value = "arrayKey", defaultValue = "array_key") String arrayKey) {
        ValueOperations<String, ArrayList<String>> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(arrayKey)) {
            ArrayList<String> arrays = operations.get(arrayKey);
            return Response.data(arrays);
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
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(objectKey)) {
            User user = operations.get(objectKey);
            return Response.data(user);
        } else {
            return Response.error("not exist");
        }
    }
}
