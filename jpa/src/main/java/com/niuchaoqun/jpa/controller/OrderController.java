package com.niuchaoqun.jpa.controller;

import com.niuchaoqun.jpa.core.BaseController;
import com.niuchaoqun.jpa.entity.Order;
import com.niuchaoqun.jpa.entity.Product;
import com.niuchaoqun.jpa.entity.Role;
import com.niuchaoqun.jpa.entity.User;
import com.niuchaoqun.jpa.repository.OrderRepository;
import com.niuchaoqun.jpa.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRepository orderRepository;

//    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
//    public Object get(@PathVariable Long orderId) {
//        if (orderId > 0) {
//            Order order = orderRepository.findOne(orderId);
//            if (order != null) {
//                return this.responseData(order);
//            }
//        }
//
//        return this.responseError("参数错误");
//    }
//
//    @RequestMapping(value = "/product/{orderId}", method = RequestMethod.GET)
//    public Object product(@PathVariable Long orderId) {
//        if (orderId > 0) {
//            Order order = orderRepository.findOne(orderId);
//            if (order != null) {
//                List<Product> products = order.getProducts();
//                return this.responseData(products);
//            }
//        }
//
//        return this.responseError("参数错误");
//    }
//
//    @RequestMapping(value = "/user/{orderId}", method = RequestMethod.GET)
//    public Object user(@PathVariable Long orderId) {
//        if (orderId > 0) {
//            Order order = orderRepository.findOne(orderId);
//            if (order != null) {
//                User user = order.getUser();
//                return this.responseData(user);
//            }
//        }
//
//        return this.responseError("参数错误");
//    }

}
