package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.entity.Admin;
import com.niuchaoqun.springboot.mapper.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping("/")
    @ResponseBody
    public String index() {

        Admin admin = adminMapper.getRelationById((long) 1);
        logger.info(admin.toString());

        return "security index";
    }
}
