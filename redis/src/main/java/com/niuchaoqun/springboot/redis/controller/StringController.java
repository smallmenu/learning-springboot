package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.redis.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author niuchaoqun
 */
@RestController
@RequestMapping("/string")
@Slf4j
public class StringController extends BaseController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/set")
    public RestResult set(@RequestParam(value = "stringKey", defaultValue = "string_key") String stringKey) {
        ValueOperations operations = redisTemplate.opsForValue();
        operations.set(stringKey, "test_redis_value", 360, TimeUnit.SECONDS);

        return RestResponse.success();
    }

    @GetMapping("/get")
    public RestResult get(@RequestParam(value = "stringKey", defaultValue = "string_key") String stringKey) {
        ValueOperations operations = redisTemplate.opsForValue();
        if (BooleanUtils.isTrue(redisTemplate.hasKey(stringKey))) {
            // 没有指定泛型，获取时则需要进行类型转换
            String result = (String) operations.get(stringKey);
            return RestResponse.success(result);
        } else {
            return RestResponse.fail("not exist");
        }
    }

    @GetMapping("/seti")
    public RestResult seti(@RequestParam(value = "integerKey", defaultValue = "integer_key") String integerKey) {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.set(integerKey, 200, 360, TimeUnit.SECONDS);

        return RestResponse.success();
    }

    @GetMapping("/geti")
    public RestResult geti(@RequestParam(value = "integerKey", defaultValue = "integer_key") String integerKey) {
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        if (BooleanUtils.isTrue(redisTemplate.hasKey(integerKey))) {
            Integer integer = operations.get(integerKey);
            return RestResponse.data(integer);
        } else {
            return RestResponse.fail("not exist");
        }
    }

    @GetMapping("/seta")
    public Object seta(@RequestParam(value = "arrayKey", defaultValue = "array_key") String arrayKey) {
        ArrayList<String> arrays = new ArrayList<>();
        arrays.add("AA");
        arrays.add("BB");
        arrays.add("CC");

        ValueOperations<String, ArrayList<String>> operations = redisTemplate.opsForValue();
        operations.set(arrayKey, arrays, 360, TimeUnit.SECONDS);

        return RestResponse.success();
    }

    @GetMapping("/geta")
    public Object geta(@RequestParam(value = "arrayKey", defaultValue = "array_key") String arrayKey) {
        ValueOperations<String, ArrayList<String>> operations = redisTemplate.opsForValue();
        if (BooleanUtils.isTrue(redisTemplate.hasKey(arrayKey))) {
            ArrayList<String> arrays = operations.get(arrayKey);
            return RestResponse.data(arrays);
        } else {
            return RestResponse.fail("not exist");
        }
    }

    @GetMapping("/setobject")
    public Object setObject(@RequestParam(value = "objectKey", defaultValue = "object_key") String objectKey) {
        User user = new User();
        user.setId(1);
        user.setName("helloworld汉字");
        user.setAge(15);

        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set(objectKey, user);
        return RestResponse.success();
    }

    @GetMapping("/getobject")
    public Object getObject(@RequestParam(value = "objectKey", defaultValue = "object_key") String objectKey) {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        if (BooleanUtils.isTrue(redisTemplate.hasKey(objectKey))) {
            User user = operations.get(objectKey);
            return RestResponse.data(user);
        } else {
            return RestResponse.fail("not exist");
        }
    }
}
