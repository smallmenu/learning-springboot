package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/template")
public class TemplateController {

    @RequestMapping("/setmodel")
    public String setModel(Model model) {
        Date date = new Date();

        List<User> users = new ArrayList<User>();
        users.add(new User(1, "zhangsan"));
        users.add(new User(2, "lisi"));
        users.add(new User(3, "wangwu"));

        model.addAttribute("title", "Template");
        model.addAttribute("show", true);
        model.addAttribute("date", date);
        model.addAttribute("users", users);

        return "template";
    }
}
