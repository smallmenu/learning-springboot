package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import com.niuchaoqun.springboot.entity.Product;
import com.niuchaoqun.springboot.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping("/product")
    public Object products(@RequestParam(value = "id", required = false) Integer id){
        if (id != null && id > 0) {
            List<Product> productWhere = productService.getProductWhere(id);
            return Response.data(productWhere);
        } else {
            List<Product> allProduct = productService.getAllProduct();
            return Response.data(allProduct);
        }
    }

    @RequestMapping("/product/{productId}")
    public Object get(@PathVariable Long productId) {
        if (productId > 0) {
            Product product = productService.getProductById(productId);
            if (product != null) {
                return Response.data(product);
            }
        }
        return Response.error();
    }
}
