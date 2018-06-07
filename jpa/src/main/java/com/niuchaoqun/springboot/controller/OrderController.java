package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import com.niuchaoqun.springboot.entity.Order;
import com.niuchaoqun.springboot.entity.Product;
import com.niuchaoqun.springboot.entity.User;
import com.niuchaoqun.springboot.repository.OrderRepository;
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
public class OrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long orderId) {
        if (orderId > 0) {
            Optional<Order> order = orderRepository.findById(orderId);
            return Response.data(order.orElse(null));
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/product/{orderId}", method = RequestMethod.GET)
    public Object product(@PathVariable Long orderId) {
        if (orderId > 0) {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                List<Product> products = order.get().getProducts();
                return Response.data(products);
            }
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/user/{orderId}", method = RequestMethod.GET)
    public Object user(@PathVariable Long orderId) {
        if (orderId > 0) {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                User user = order.get().getUser();
                return Response.data(user);
            }
        }

        return Response.error("参数错误");
    }

}
