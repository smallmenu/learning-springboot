package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import com.niuchaoqun.springboot.entity.Mybatis;
import com.niuchaoqun.springboot.mapper.MybatisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mybatis")
public class MybatisController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MybatisController.class);

    @Autowired
    private MybatisMapper mybatisMapper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        if (id > 0) {
            Mybatis mybatis = mybatisMapper.findById(id);
            if (mybatis != null) {
                return Response.data(mybatis);
            } else {
                return Response.error("ID不存在");
            }
        }

        return Response.error("参数错误");
    }
}
