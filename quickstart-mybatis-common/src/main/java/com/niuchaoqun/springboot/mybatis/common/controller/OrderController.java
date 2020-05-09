package com.niuchaoqun.springboot.mybatis.common.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.mybatis.common.entity.Order;
import com.niuchaoqun.springboot.mybatis.common.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController extends BaseController {
    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long orderId) {
        if (orderId > 0) {
            Order order = orderMapper.selectByPrimaryKey(orderId);
            return RestResponse.data(order);
        }

        return RestResponse.fail("参数错误");
    }

    @RequestMapping(value = "/product/{orderId}", method = RequestMethod.GET)
    public Object product(@PathVariable Long orderId) {
        return RestResponse.fail("参数错误");
    }
}
