package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/template")
public class TemplateController {

    @RequestMapping("/setmodel")
    public String setModel(Model model) {
        Date date = new Date();

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setName("zhangsan");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("lisi");

        User user3 = new User();
        user3.setId(3L);
        user3.setName("wangwu");

        users.add(user1);
        users.add(user2);
        users.add(user3);

        model.addAttribute("title", "Template");
        model.addAttribute("show", true);
        model.addAttribute("date", date);
        model.addAttribute("users", users);

        return "template";
    }
}
