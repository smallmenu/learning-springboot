package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.redis.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list")
@Slf4j
public class ListController extends BaseController {

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("")
    public void index() {

    }

    @RequestMapping("/string")
    public Object string(@RequestParam(value = "listKey", defaultValue = "list_key_string") String listKey) {
        String value = "list_value";
        ListOperations<String, String> operations = stringRedisTemplate.opsForList();

        operations.rightPush(listKey, value);
        operations.rightPush(listKey, value);
        operations.rightPush(listKey, value);

        String pop = operations.leftPop(listKey);

        return RestResponse.success(pop);
    }

    @RequestMapping("/object")
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
