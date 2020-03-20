package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.redis.entity.Product;
import com.niuchaoqun.springboot.redis.mapper.ProductMapper;
import com.niuchaoqun.springboot.redis.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
@Slf4j
public class CacheController extends BaseController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/product")
    public Object products(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null && id > 0) {
            List<Product> productWhere = productService.getProductWhere(id);
            return RestResponse.data(productWhere);
        } else {
            List<Product> allProduct = productService.getAllProduct();
            return RestResponse.data(allProduct);
        }
    }

    @GetMapping("/product/{productId}")
    public Object get(@PathVariable Long productId) {
        if (productId > 0) {
            Product product = productService.getProductById(productId);
            if (product != null) {
                return RestResponse.data(product);
            }
        }
        return RestResponse.error();
    }

    @PutMapping("/product/{productId}")
    public Object save(@PathVariable Long productId) {
        if (productId > 0) {
            Product product = productMapper.selectByPrimaryKey(productId);
            product.setPrice(0.00);
            Product save = productService.save(product);
            return RestResponse.data(save);
        }
        return RestResponse.error();
    }
}
