package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/set")
@RestController
public class SetController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SetController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/string")
    public Object string(@RequestParam(value = "setKey", defaultValue = "set_key_string") String setKey) {
        SetOperations<String, String> operations = redisTemplate.opsForSet();
        operations.add(setKey, "AA");
        operations.add(setKey, "BB");
        operations.add(setKey, "CC");
        operations.add(setKey, "BB");

        String random = operations.randomMember(setKey);

        return Response.success(random);
    }
}
