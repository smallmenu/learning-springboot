package com.niuchaoqun.springboot.jpa.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.jpa.entity.Order;
import com.niuchaoqun.springboot.jpa.entity.Product;
import com.niuchaoqun.springboot.jpa.entity.User;
import com.niuchaoqun.springboot.jpa.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController extends BaseController {
    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public RestResult get(@PathVariable Long orderId) {
        if (orderId > 0) {
            Optional<Order> order = orderRepository.findById(orderId);
            return RestResponse.data(order.orElse(null));
        }

        return RestResponse.fail("参数错误");
    }

    @RequestMapping(value = "/product/{orderId}", method = RequestMethod.GET)
    public RestResult product(@PathVariable Long orderId) {
        if (orderId > 0) {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                List<Product> products = order.get().getProducts();
                return RestResponse.data(products);
            }
        }

        return RestResponse.fail("参数错误");
    }

    @RequestMapping(value = "/user/{orderId}", method = RequestMethod.GET)
    public RestResult user(@PathVariable Long orderId) {
        if (orderId > 0) {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                User user = order.get().getUser();
                return RestResponse.data(user);
            }
        }

        return RestResponse.fail("参数错误");
    }

}
