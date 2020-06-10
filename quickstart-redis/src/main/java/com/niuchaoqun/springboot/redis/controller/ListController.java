package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.redis.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/list")
@Slf4j
public class ListController extends BaseController {

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/set_string")
    public Object setStringList(@RequestParam(value = "listKey", defaultValue = "list_key_string") String listKey) {
        String value = "list_value";
        ListOperations<String, String> operations = redisTemplate.opsForList();

        operations.rightPush(listKey, value);
        operations.rightPush(listKey, value);
        operations.rightPush(listKey, value);

        String pop = operations.leftPop(listKey);

        return RestResponse.success(pop);
    }

    @GetMapping("/get_string")
    public RestResult getStringList(@RequestParam(value = "listKey", defaultValue = "list_key_string") String listKey) {
        List<String> lists = new ArrayList<>();
        ListOperations<String, String> operations = redisTemplate.opsForList();
        long size = Optional.ofNullable(operations.size(listKey)).orElse(0L);
        for (long i = 0; i < size; i++) {
            lists.add(operations.rightPop(listKey));
        }

        return RestResponse.data(lists);
    }

    @GetMapping("/object")
    public Object setObject(@RequestParam(value = "objectKey", defaultValue = "list_key_object") String objectKey) {
        ListOperations<String, User> operations = redisTemplate.opsForList();

        User user1 = new User();
        user1.setId(1);
        user1.setName("helloworld汉字1");
        user1.setAge(15);

        User user2 = new User();
        user2.setId(2);
        user2.setName("helloworld汉字2");
        user2.setAge(16);

        operations.rightPush(objectKey, user1);
        operations.rightPush(objectKey, user2);

        User user = operations.leftPop(objectKey);

        return RestResponse.data(user);
    }
}
