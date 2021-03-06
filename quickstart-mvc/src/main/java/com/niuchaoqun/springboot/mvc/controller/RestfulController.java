package com.niuchaoqun.springboot.mvc.controller;

import com.niuchaoqun.springboot.mvc.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 控制器 RequestMaping 注解URL前缀: /rest
 */
@RestController
@RequestMapping("/rest")
@Slf4j
public class RestfulController {
    private final AtomicLong counter = new AtomicLong();

    /**
     * 这种情况下，如果为空，则支持 /rest 与 /rest/ 访问
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "Hello Rest";
    }

    @RequestMapping("/greeing")
    public User index(@RequestParam(value = "name", defaultValue = "World") String name) {
        User user = new User();
        user.setId(counter.incrementAndGet());
        user.setName(name);

        return user;
    }
}
