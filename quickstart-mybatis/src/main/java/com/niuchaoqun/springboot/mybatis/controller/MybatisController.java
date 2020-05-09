package com.niuchaoqun.springboot.mybatis.controller;

import com.niuchaoqun.springboot.mybatis.core.BaseController;
import com.niuchaoqun.springboot.mybatis.core.Response;
import com.niuchaoqun.springboot.mybatis.entity.Mybatis;
import com.niuchaoqun.springboot.mybatis.mapper.MybatisMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/mybatis")
public class MybatisController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MybatisController.class);

    @Autowired
    private MybatisMapper mybatisMapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add() {
        Mybatis mybatis = new Mybatis();
        mybatis.setName("test");
        mybatis.setBirthday(LocalDate.parse("1980-08-08", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        mybatis.setSex("male");
        mybatis.setAccess(LocalDateTime.parse("1981-08-08 01:02:03", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mybatis.setAccessTime(LocalTime.parse("01:02:03", DateTimeFormatter.ofPattern("HH:mm:ss")));
        mybatis.setState(new Short("1"));
        boolean success = mybatisMapper.insert(mybatis);
        return Response.data(mybatis);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Object edit(@PathVariable Long id) {
        if (id > 0) {
            Mybatis mybatis = mybatisMapper.findById(id);
            if (mybatis != null) {
                Mybatis edit = new Mybatis();
                edit.setId(id);
                edit.setSex("male");
                edit.setBirthday(LocalDate.parse("1983-01-08", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                int success = mybatisMapper.update(edit);

                return Response.data(success);
            } else {
                return Response.error("ID不存在");
            }
        }

        return Response.error("参数错误");
    }

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

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public Object getUsername(@RequestParam String name) {
        if (!StringUtils.isEmpty(name)) {
            Mybatis mybatis = mybatisMapper.findByName(name);
            return Response.data(mybatis);
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object getAll() {
        List<Mybatis> mybatisAll = mybatisMapper.findAll();
        return Response.data(mybatisAll);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Long id) {
        if (id > 0) {
            Mybatis exists = mybatisMapper.findById(id);
            if (exists != null) {
                mybatisMapper.deleteById(id);
                return Response.success("操作成功");
            } else {
                return Response.error("ID不存在");
            }
        }
        return Response.error("参数错误");
    }
}
